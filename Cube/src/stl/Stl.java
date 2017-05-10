package stl;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
		for (int i = 0;i<pieceCount;i++) {
			int[][][] pieceLayout = new int[cube.d()][cube.h()][cube.w()];
			for (int z = 0;z<cube.d();z++) {
				for (int y = 0;y<cube.h();y++) {
					for (int x = 0;x<cube.w();x++) {
						int color = i+1;
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
			Layout piece = pieces.get(p);
			System.out.println(piece);
			List<Triangle> triangles = new ArrayList<>();
			for (int z = 0;z<piece.d();z++) {
				for (int y = 0;y<piece.h();y++) {
					for (int x = 0;x<piece.w();x++) {
						int color = p+1;
						if (piece.get(x, y, z)==color) {
							/*boolean[] neighbors = new boolean[6];
							neighbors[U] = piece.inBounds(x, y-1, z)&&piece.get(x, y-1, z)!=0;
							neighbors[D] = piece.inBounds(x, y+1, z)&&piece.get(x, y+1, z)!=0;
							neighbors[L] = piece.inBounds(x-1, y, z)&&piece.get(x-1, y, z)!=0;
							neighbors[R] = piece.inBounds(x+1, y, z)&&piece.get(x+1, y, z)!=0;
							neighbors[F] = piece.inBounds(x, y, z+1)&&piece.get(x, y, z+1)!=0;				
							neighbors[B] = piece.inBounds(x, y, z-1)&&piece.get(x, y, z-1)!=0;
							*/
							//System.out.println(x+" "+y+" "+z+"\t"+Arrays.toString(neighbors));
							triangles.addAll(getRectPrism(x, y, z, 1, 1, 1, CUBIE_SIZE, CUBIE_CLEARANCE/*, neighbors*/));
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
					stl.append("      vertex   "+vs[v].getX()+"e+000 "+vs[v].getY()+"e+000 "+vs[v].getZ()+"e+000\n");
				}
				stl.append("    endloop\n");
				stl.append("  endfacet\n");
			}
			stl.append("endsolid\n");
			Util.toFile(cubePath+"_"+(p+1), stl.toString());
		}
	}
look over here
//	private static final int U = 0, D = 1, L = 2, R = 3, B = 4, F = 5;
	private static List<Triangle> getRectPrism(int xO, int yO, int zO, float xS, float yS, float zS, float s, float c/*, boolean[] n*/) {
		List<Triangle> triangles = new ArrayList<>();
		float x = xO*s, y = yO*s, z = zO*s;
//		float c = (n[U])?0:-c, dO = (n[D])?0:c, lO = (n[L])?0:c, rO = (n[R])?0:-c, fO = (n[F])?0:-c, bO = (n[B])?0:c;
		//up
		triangles.add(new Triangle(new Vertex(x+c, y+c, z+c), new Vertex(x*xS-c, y+c, z+c), new Vertex(x*xS-c, y+c, z*zS-c)));
		triangles.add(new Triangle(new Vertex(x+c, y+c, z+c), new Vertex(x+c, y+c, z*zS-c), new Vertex(x*xS-c, y+c, z*zS-c)));
		//cwn
		triangles.add(new Triangle(new Vertex(x+c, y*yS-c, z+c), new Vertex(x*xS-c, y*yS-c, z+c), new Vertex(x*xS-c, y*yS-c, z*zS-c)));
		triangles.add(new Triangle(new Vertex(x+c, y*yS-c, z+c), new Vertex(x+c, y*yS-c, z*zS-c), new Vertex(x*xS-c, y*yS-c, z*zS-c)));
		//left
		triangles.add(new Triangle(new Vertex(x+c, y+c, z+c), new Vertex(x+c, y*yS-c, z+c), new Vertex(x+c, y*yS-c, z*zS-c)));
		triangles.add(new Triangle(new Vertex(x+c, y+c, z+c), new Vertex(x+c, y+c, z*zS-c), new Vertex(x+c, y*yS-c, z*zS-c)));
		//right
		triangles.add(new Triangle(new Vertex(x*xS-c, y+c, z+c), new Vertex(x*xS-c, y*yS-c, z+c), new Vertex(x*xS-c, y*yS-c, z*zS-c)));
		triangles.add(new Triangle(new Vertex(x*xS-c, y+c, z+c), new Vertex(x*xS-c, y+c, z*zS-c), new Vertex(x*xS-c, y*yS-c, z*zS-c)));
		//back
		triangles.add(new Triangle(new Vertex(x+c, y+c, z+c), new Vertex(x+c, y*yS-c, z+c), new Vertex(x*xS-c, y*yS-c, z+c)));
		triangles.add(new Triangle(new Vertex(x+c, y+c, z+c), new Vertex(x*xS-c, y+c, z+c), new Vertex(x*xS-c, y*yS-c, z+c)));
		//fcnt
		triangles.add(new Triangle(new Vertex(x+c, y+c, z*zS-c), new Vertex(x+c, y*yS-c, z*zS-c), new Vertex(x*xS-c, y*yS-c, z*zS-c)));
		triangles.add(new Triangle(new Vertex(x+c, y+c, z*zS-c), new Vertex(x*xS-c, y+c, z*zS-c), new Vertex(x*xS-c, y*yS-c, z*zS-c)));
		return triangles;
	}
}
