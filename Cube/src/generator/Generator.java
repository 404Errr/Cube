package generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import data.GeneratorData;
import main.Layout;
import main.Pointer;

public class Generator implements GeneratorData {
	private static NavigateableLayout cube;
	private static Pointer pointer;
	private static StringBuilder log = new StringBuilder();

	public static void generate() {
		long startTime = System.currentTimeMillis();
		gen();
		log.append("\n"+cube);
		if (MAKE_PIECES==new Boolean(true)) {
			if (HIDE_SOLUTION==new Boolean(true)) for (int i = 0;i<15;i++) log.append("\n");
			makePieces(cube);
		}
		System.out.println(log.toString());
		System.out.println((System.currentTimeMillis()-startTime)/1000f+" s");
	}

	private static void gen() {
		Random rand = new Random();
		List<Integer> colors = null;
		int colorI = 0;
		boolean reset = true, full = false;
		List<int[]> moves = new ArrayList<>(Arrays.asList(new int[] {1, 0, 0}, new int[] {0, 1, 0}, new int[] {0, 0, 1}, new int[] {-1, 0, 0}, new int[] {0, -1, 0}, new int[] {0, 0, -1}));
		do {
			if (reset) {
				reset = false;
				cube = new NavigateableLayout(SIZE);
				pointer = new Pointer(1, 1, 1);
				colors = getColorList();
				colorI = 0;
			}
			boolean couldMove = false;
			Collections.shuffle(moves);
			for (int i = 0;i<moves.size();i++) {
				int[] move = moves.get(i);
				Pointer tempPointer = pointer.getMoved(move[0], move[1], move[2]);
				if (cube.inBounds(tempPointer)&&((cube.get(tempPointer)==colors.get(colorI)&&rand.nextBoolean())||!cube.isOccupied(tempPointer))) {
					pointer = tempPointer;
					couldMove = true;
					break;
				}
			}
			if (cube.get(pointer)!=colors.get(colorI)) {
				cube.set(pointer, colors.get(colorI));
				colorI++;
			}
			if (!couldMove||colorI>SIZE*SIZE*SIZE) {
				reset = true;
			}
			full = cube.isFull();
			if (full&&!isValid()) reset = true;
		} while (reset||!full);
	}

	private static List<Integer> getColorList() {
		int[] counts = new int[PIECE_COUNT];
		for (int i = 0;i<counts.length;i++) counts[i] = PIECE_SIZE_MIN;
		int total = PIECE_SIZE_MIN*PIECE_COUNT;
		Random rand = new Random();
		while (total<SIZE*SIZE*SIZE) {
			int color = rand.nextInt(counts.length);
			if (counts[color]<PIECE_SIZE_MAX) {
				counts[color]++;
				total++;
			}
		}
		List<Integer> colorList = new ArrayList<>();
		for (int i = 0;i<counts.length;i++) {
			for (int j = 0;j<counts[i];j++) {
				colorList.add(i+1);
			}
		}
		return colorList;
	}

	private static boolean isValid() {
		if (hasFlat()) {
//			System.out.println("flat");
			return false;
		}
		if (tooManyOnPlane()) {
//			System.out.println("too many on plane");
			return false;
		}
		if (hasCollision()) {
//			System.out.println("has collision");
			return false;
		}
		if (has2DClusters()) {
//			System.out.println("2d");
			return false;
		}
		if (has3DClusters()) {
//			System.out.println("3d");
			return false;
		}
//		if (hasOverhang()) {//TODO FIXME
//			System.out.println("has overhang\n"+cube);
//			return false;
//		}
		if (hasIdentical()) {
//			System.out.println("has identical);
			return false;
		}
		return true;
	}

	private static boolean hasIdentical() {
		List<Layout> pieces = new ArrayList<>();
		for (int i = 0;i<PIECE_COUNT;i++) {
			int[][][] pieceLayout = new int[SIZE][SIZE][SIZE];
			for (int z = 0;z<SIZE;z++) {
				for (int y = 0;y<SIZE;y++) {
					for (int x = 0;x<SIZE;x++) {
						int color = i+1;
						if (cube.get(x, y, z)==color) pieceLayout[z][y][x] = cube.get(x, y, z);
					}
				}
			}
			Layout piece = new Layout(pieceLayout);
			piece.trim();
			pieces.add(piece);
		}
		for (int i = 0;i<pieces.size();i++) {
			for (int o = 0;o<6;o++) {//every side
				pieces.get(i).rotate((o==4)?1:(o==5)?2:0, (o>=1&&o<=4)?1:0, 0);
				for (int j = 0;j<pieces.size();j++) {
					if (i!=j&&pieces.get(i).equals(pieces.get(j))) {
						return true;
					}
				}
			}
		}
		return false;
	}

//	private static boolean hasOverhang() {//FIXME TODO
//		int[][][] pieceLayout = null;
//		for (int i = 0;i<PIECE_COUNT;i++) {
//			pieceLayout = new int[SIZE][SIZE][SIZE];
//			for (int z = 0;z<SIZE;z++) {
//				for (int y = 0;y<SIZE;y++) {
//					for (int x = 0;x<SIZE;x++) {
//						int color = i+1;
//						if (cube.get(x, y, z)==color) pieceLayout[z][y][x] = cube.get(x, y, z);
//					}
//				}
//			}
//			Layout piece = new Layout(pieceLayout);
//			piece.trim();
//			if (piece.d()==1||piece.h()==1||piece.w()==1) continue;
//			boolean hasOverhang = true;
//			for (int o = 0;o<6;o++) {//every side
//				piece.rotate((o==4)?1:(o==5)?2:0, (o>=1&&o<=4)?1:0, 0);
//				int overhangs = 0;
//				for (int z = 0;z<piece.d();z++) {
//					for (int y = 0;y<piece.h();y++) {
//						for (int x = 0;x<piece.w();x++) {
//							if (piece.inBounds(x, y-1, z)) {
//								if (piece.get(x, y, z)==0&&piece.get(x, y-1, z)!=0) {
//									overhangs++;
//								}
//							}
//						}
//					}
//				}
////			System.out.println("\n"+piece+"o "+o+"\toverhangs "+overhangs+"\tcount "+count+"\tprintable "+printable);
//				if (overhangs==0) {
//					hasOverhang = false;
//					break;
//				}
//			}
//			if (!hasOverhang) continue;
//			System.out.println(piece);
//			return true;
//		}
//		return false;
//	}

	private static boolean hasCollision() {
		for (int z = 0;z<SIZE;z++) {
			for (int y = 0;y<SIZE;y++) {
				for (int x = 0;x<SIZE;x++) {
					for (int a = 0;a<3;a++) {
						int zOLimit = (a==0)?1:2, yOLimit = (a==1)?1:2, xOLimit = (a==2)?1:2;
						List<Integer> colors = new ArrayList<>();
						for (int zO = 0;zO<zOLimit;zO++) {
							for (int yO = 0;yO<yOLimit;yO++) {
								for (int xO = 0;xO<xOLimit;xO++) {
									if (!cube.inBounds(x+xO, y+yO, z+zO)) continue;
									colors.add(cube.get(x+xO, y+yO, z+zO));
								}
							}
						}
						if (colors.size()<4) continue;
						int tl = colors.get(0), tr = colors.get(1), bl = colors.get(2), br = colors.get(3);
						if (!(tl==bl||tr==br||tl==tr||tr==br||!(tl==br||tr==bl))) return true;
					}
					for (int a = 0;a<3;a++) {
						int zOLimit = (a==0)?3:1, yOLimit = (a==1)?3:1, xOLimit = (a==2)?3:1;
						List<Integer> colors = new ArrayList<>();
						for (int zO = 0;zO<zOLimit;zO++) {
							for (int yO = 0;yO<yOLimit;yO++) {
								for (int xO = 0;xO<xOLimit;xO++) {
									if (!cube.inBounds(x+xO, y+yO, z+zO)) continue;
									colors.add(cube.get(x+xO, y+yO, z+zO));
								}
							}
						}
						if (colors.size()<3) continue;
						int f = colors.get(0), m = colors.get(1), l = colors.get(2);
						if (f==l&&f!=m) return true;
					}
				}
			}
		}
		return false;
	}

	private static boolean has2DClusters() {
		for (int z = 0;z<SIZE;z++) {
			for (int y = 0;y<SIZE;y++) {
				for (int x = 0;x<SIZE;x++) {
					for (int a = 0;a<3;a++) {
						int zOLimit = (a==0)?1:2, yOLimit = (a==1)?1:2, xOLimit = (a==2)?1:2;
						List<Integer> counts = new ArrayList<>();
						for (int i = 0;i<PIECE_COUNT;i++) {
							counts.add(0);
						}
						for (int zO = 0;zO<zOLimit;zO++) {
							for (int yO = 0;yO<yOLimit;yO++) {
								for (int xO = 0;xO<xOLimit;xO++) {
									if (!cube.inBounds(x+xO, y+yO, z+zO)) continue;
									int cubieColor = cube.get(x+xO, y+yO, z+zO)-1;
									counts.set(cubieColor, counts.get(cubieColor)+1);
								}
							}
						}
						for (int i = 0;i<counts.size();i++) {
							if (counts.get(i)>=BORING_2D_CLUSTER_COUNT) return true;
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean has3DClusters() {
		for (int z = 0;z<SIZE-1;z++) {
			for (int y = 0;y<SIZE-1;y++) {
				for (int x = 0;x<SIZE-1;x++) {
					List<Integer> counts = new ArrayList<>();
					for (int i = 0;i<PIECE_COUNT;i++) {
						counts.add(0);
					}
					for (int zO = 0;zO<2;zO++) {
						for (int yO = 0;yO<2;yO++) {
							for (int xO = 0;xO<2;xO++) {
								int cubieColor = cube.get(x+xO, y+yO, z+zO)-1;
								counts.set(cubieColor, counts.get(cubieColor)+1);
							}
						}
					}
					for (int i = 0;i<counts.size();i++) {
						if (counts.get(i)>=BORING_3D_CLUSTER_COUNT) return true;
					}
				}
			}
		}
		return false;
	}

	private static boolean tooManyOnPlane() {
		int[][][] nCount = new int[SIZE][SIZE][SIZE];
		for (int z = 0;z<SIZE;z++) {
			for (int y = 0;y<SIZE;y++) {
				for (int x = 0;x<SIZE;x++) {
					int count = 0, cubieColor = cube.get(x, y, z);
					for (int zO = -1;zO<=1;zO++) {
						for (int yO = -1;yO<=1;yO++) {
							for (int xO = -1;xO<=1;xO++) {
								if (cube.inBounds(x+xO, y+yO, z+zO)&&cube.get(x+xO, y+yO, z+zO)==cubieColor) count++;
							}
						}
					}
					nCount[z][y][x] = count;
				}
			}
		}
		boolean tooManyOnPlane = false;
		List<int[][]> planeCounts = getPlaneCounts();
		for (int i = 0;i<planeCounts.size();i++) {
			for (int j = 0;j<planeCounts.get(i).length;j++) {
				for (int k = 0;k<planeCounts.get(i)[j].length;k++) {
					if (planeCounts.get(i)[j][k]>=BORING_PLANE_COUNT) tooManyOnPlane = true;
				}
			}
		}
		return tooManyOnPlane;
	}

	private static boolean hasFlat() {
		List<int[][]> planeCounts = getPlaneCounts();
		int flatCount = 0;
		for (int i = 0;i<planeCounts.size();i++) {
			for (int j = 0;j<planeCounts.get(i).length;j++) {
				int zeroCount = 0;
				for (int k = 0;k<planeCounts.get(i)[j].length;k++) {
					if (planeCounts.get(i)[j][k]==0) {
						zeroCount++;
					}
				}
				if (zeroCount==SIZE-1) {
					flatCount++;
				}
			}
		}
		return flatCount!=0;
	}

	private static List<int[][]> getPlaneCounts() {
		List<int[][]> planeCounts = new ArrayList<>();
		for (int i = 0;i<PIECE_COUNT;i++) {
			planeCounts.add(new int[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}});
		}
		for (int z = 0;z<SIZE;z++) {
			for (int y = 0;y<SIZE;y++) {
				for (int x = 0;x<SIZE;x++) {
					int cubieColor = cube.get(x, y, z);
					planeCounts.get(cubieColor-1)[X][x]++;
					planeCounts.get(cubieColor-1)[Y][y]++;
					planeCounts.get(cubieColor-1)[Z][z]++;
				}
			}
		}
		return planeCounts;
	}

	public static void makePieces(Layout cube) {
		Generator.cube = new NavigateableLayout(cube.getLayout());
		List<int[][][]> pieceLayouts = new ArrayList<>();
		for (int i = 0;i<PIECE_COUNT;i++) {
			pieceLayouts.add(new int[SIZE][SIZE][SIZE]);
			for (int z = 0;z<SIZE;z++) {
				for (int y = 0;y<SIZE;y++) {
					for (int x = 0;x<SIZE;x++) {
						int color = i+1;
						if (cube.get(x, y, z)==color) pieceLayouts.get(i)[z][y][x] = cube.get(x, y, z);
					}
				}
			}
		}
		List<Layout> pieces = new ArrayList<>();
		Random rand = new Random();
		for (int i = 0;i<pieceLayouts.size();i++) {
			Layout piece = new Layout(pieceLayouts.get(i));
//			if (DIRECTIONS) {
//				boolean found = false;
//				for (int z = 0;!found&&z<SIZE;z++) {
//					for (int y = 0;!found&&y<SIZE;y++) {
//						for (int x = 0;!found&&x<SIZE;x++) {
//							if (x==1&&y==1&&z==1) continue;
//							if (piece.get(x, y, z)!=0) {
//								piece.set(x, y, z, getDir(x, y, z));
//								found = true;
//							}
//						}
//					}
//				}
//			}
			piece.trim();
			if (HIDE_SOLUTION) piece.rotate(rand.nextInt(4), 3, 1);
			pieces.add(piece);
		}
		log.append("\n");
		for (int i = 0;i<pieces.size();i++) {
			log.append(pieces.get(i)+"\n");
		}
	}
//
//	private static int getDir(int x, int y, int z) {
//		if (y==0) return U;
//		if (y==2) return D;
//		if (x==0) return L;
//		if (x==2) return R;
//		if (z==0) return F;
//		if (z==2) return B;
//		return -100;
//	}
}

