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
			List<Triangle> triangles = new ArrayList<>();
			for (int z = 0;z<cube.d();z++) {
				for (int y = 0;y<cube.h();y++) {
					for (int x = 0;x<cube.w();x++) {
						int color = p+1;
						if (cube.get(x, y, z)==color) {
							triangles.addAll(getCubie(x, y, z, CUBIE_SIZE));
						}
					}
				}
			}

			//TODO remove repeats

			System.out.println(pieces.get(p)+"\n"+triangles.size()+"\n\n");
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

	private static List<Triangle> getCubie(int xO, int yO, int zO, float s) {
		List<Triangle> triangles = new ArrayList<>();
		float x = xO*s, y = yO*s, z = zO*s;
		triangles.add(new Triangle(new Vertex(x, y+s, z), new Vertex(x, y, z), new Vertex(x, y+s, z+s)));
		triangles.add(new Triangle(new Vertex(x, y+s, z+s), new Vertex(x, y, z), new Vertex(x, y, z+s)));
		triangles.add(new Triangle(new Vertex(x+s, y+s, z), new Vertex(x, y+s, z), new Vertex(x+s, y+s, z+s)));
		triangles.add(new Triangle(new Vertex(x+s, y+s, z+s), new Vertex(x, y+s, z), new Vertex(x, y+s, z+s)));
		triangles.add(new Triangle(new Vertex(x+s, y, z), new Vertex(x+s, y+s, z), new Vertex(x+s, y, z+s)));
		triangles.add(new Triangle(new Vertex(x+s, y, z+s), new Vertex(x+s, y+s, z), new Vertex(x+s, y+s, z+s)));
		triangles.add(new Triangle(new Vertex(x, y, z), new Vertex(x+s, y, z), new Vertex(x, y, z+s)));
		triangles.add(new Triangle(new Vertex(x, y, z+s), new Vertex(x+s, y, z), new Vertex(x+s, y, z+s)));
		triangles.add(new Triangle(new Vertex(x, y, z+s), new Vertex(x+s, y, z+s), new Vertex(x+s, y+s, z+s)));
		triangles.add(new Triangle(new Vertex(x, y+s, z+s), new Vertex(x+s, y, z+s), new Vertex(x+s, y+s, z+s)));
		triangles.add(new Triangle(new Vertex(x+s, y, z), new Vertex(x, y, z), new Vertex(x+s, y+s, z)));
		triangles.add(new Triangle(new Vertex(x+s, y+s, z), new Vertex(x, y, z), new Vertex(x, y+s, z)));
		return triangles;
	}
}
