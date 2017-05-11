package stl;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import data.StlData;
import main.Layout;
import util.Util;

public class Stl implements StlData {
	public static void generate() {
		String cubePath = ROOT+"/"+PATH;
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(cubePath));
		}
		catch (FileNotFoundException e) {
			System.err.println("Can't find file at: "+cubePath);
			System.exit(0);
		}
		catch (NoSuchFileException e) {
			System.err.println("There is no file at: "+cubePath);
			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		int[][][] raw = new int[SIZE][SIZE][SIZE];
		int zR = 0, yR = 0, pieceCount = 0;
		for (int i = 0;i<lines.size();i++) {
			String line = lines.get(i);
			if (line.contains("-")) {
				yR = 0;
				zR++;
				continue;
			}
			for (int xR = 0;xR<SIZE;xR++) {
				System.out.println(xR+","+yR+","+zR+"\t"+line.charAt(xR));
				raw[zR][yR][xR] = Integer.parseInt(line.charAt(xR)+"");
				if (raw[zR][yR][xR]>pieceCount) pieceCount = raw[zR][yR][xR];
			}
			yR++;
		}
		Layout cube = new Layout(raw);
		List<Layout> pieces = new ArrayList<>();
		for (int p = 0;p<pieceCount;p++) {
			int[][][] pieceLayout = new int[cube.d()][cube.h()][cube.w()];
			for (int z = 0;z<cube.d();z++) {
				for (int y = 0;y<cube.h();y++) {
					for (int x = 0;x<cube.w();x++) {
						int color = p+1;
						if (cube.get(x, y, z)==color) pieceLayout[z][y][x] = cube.get(x, y, z);
					}
				}
			}
			Layout piece = new Layout(pieceLayout);
			piece.trim();
			pieces.add(piece);
		}
		System.out.println(cube);
		System.out.println("pieceCount: "+pieceCount);
		//TODO rotate piece
		for (int p = 0;p<pieceCount;p++) {
			Layout piece = pieces.get(p), bestOrientation = piece.clone();
			for (int o = 0;o<6;o++) {
				piece.rotate((o==4)?1:(o==5)?2:0, (o>=1&&o<=4)?1:0, 0);
				boolean overhangs = false;
				for (int z = 0;z<piece.d();z++) {
					for (int x = 0;x<piece.w();x++) {
						for (int y = 0;y<piece.h();y++) {
							//TODO check if two above each other look like 010 100 etc
						}						
					}
				}
				if (!overhangs&&piece.h()<bestOrientation.h()) bestOrientation = piece.clone();
			}
			piece = bestOrientation;
			System.out.println(piece);
			List<Triangle> triangles = new ArrayList<>();
			for (int z = 0;z<piece.d();z++) {
				for (int y = 0;y<piece.h();y++) {
					for (int x = 0;x<piece.w();x++) {
						int color = p+1;
						if (piece.get(x, y, z)==color) {
							if (piece.inBounds(x, y-1, z)&&piece.get(x, y-1, z)!=0) triangles.addAll(getRectPrism(x, y-1, z, 1, 2, 1, CUBIE_SIZE, CUBIE_CLEARANCE));
							if (piece.inBounds(x, y+1, z)&&piece.get(x, y+1, z)!=0) triangles.addAll(getRectPrism(x, y, z, 1, 2, 1, CUBIE_SIZE, CUBIE_CLEARANCE));
							if (piece.inBounds(x-1, y, z)&&piece.get(x-1, y, z)!=0) triangles.addAll(getRectPrism(x-1, y, z, 2, 1, 1, CUBIE_SIZE, CUBIE_CLEARANCE));
							if (piece.inBounds(x+1, y, z)&&piece.get(x+1, y, z)!=0) triangles.addAll(getRectPrism(x, y, z, 2, 1, 1, CUBIE_SIZE, CUBIE_CLEARANCE));
							if (piece.inBounds(x, y, z-1)&&piece.get(x, y, z-1)!=0) triangles.addAll(getRectPrism(x, y, z-1, 1, 1, 2, CUBIE_SIZE, CUBIE_CLEARANCE));
							if (piece.inBounds(x, y, z+1)&&piece.get(x, y, z+1)!=0) triangles.addAll(getRectPrism(x, y, z, 1, 1, 2, CUBIE_SIZE, CUBIE_CLEARANCE));
//							triangles.addAll(getRectPrism(x, y, z, 1, 1, 1, CUBIE_SIZE, CUBIE_CLEARANCE));
						}
					}
				}
			}
			System.out.println(triangles.size()+"\n\n");
			StringBuilder stl = new StringBuilder();
			stl.append("solid ascii\n");
			for (int t = 0;t<triangles.size();t++) {
				Vertex[] vs = triangles.get(t).getVertexs();
				stl.append("  facet normal 0.0e+000 0.0e+000 0.0e+000\n");
				stl.append("    outer loop\n");
				for (int v = 0;v<vs.length;v++) {
					stl.append("      vertex   "+vs[v].getX()+"e+000 "+-vs[v].getZ()+"e+000 "+-vs[v].getY()+"e+000\n");
				}
				stl.append("    endloop\n");
				stl.append("  endfacet\n");
			}
			stl.append("endsolid\n");
			Util.toFile(cubePath+"_"+(p+1), stl.toString());
		}
	}

	private static List<Triangle> getRectPrism(int x, int y, int z, int xS, int yS, int zS, float s, float c) {
		List<Triangle> tris = new ArrayList<>();
		x*=s;y*=s;z*=s;
		xS*=s;yS*=s;zS*=s;
		Vertex[][][] v = {
			{
				{new Vertex(x+c, y+c, z+c), new Vertex(x+xS-c, y+c, z+c)},
				{new Vertex(x+c, y+yS-c, z+c), new Vertex(x+xS-c, y+yS-c, z+c)},
			},
			{
				{new Vertex(x+c, y+c, z+zS-c), new Vertex(x+xS-c, y+c, z+zS-c)},
				{new Vertex(x+c, y+yS-c, z+zS-c), new Vertex(x+xS-c, y+yS-c, z+zS-c)},
			},
		};
		tris.add(new Triangle(v[1][0][1], v[1][0][0], v[1][1][1]));
		tris.add(new Triangle(v[1][1][1], v[1][0][0], v[1][1][0]));
		tris.add(new Triangle(v[0][0][1], v[1][0][1], v[0][1][1]));
		tris.add(new Triangle(v[0][1][1], v[1][0][1], v[1][1][1]));
		tris.add(new Triangle(v[0][0][0], v[0][0][1], v[0][1][0]));
		tris.add(new Triangle(v[0][1][0], v[0][0][1], v[0][1][1]));
		tris.add(new Triangle(v[1][0][0], v[0][0][0], v[1][1][0]));
		tris.add(new Triangle(v[1][1][0], v[0][0][0], v[0][1][0]));
		tris.add(new Triangle(v[1][0][0], v[1][0][1], v[0][0][0]));
		tris.add(new Triangle(v[0][0][0], v[1][0][1], v[0][0][1]));
		tris.add(new Triangle(v[0][1][0], v[0][1][1], v[1][1][0]));
		tris.add(new Triangle(v[1][1][0], v[0][1][1], v[1][1][1]));
		return tris;
	}
}