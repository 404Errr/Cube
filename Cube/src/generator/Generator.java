package generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import data.GeneratorData;
import data.MainData;
import main.Layout;
import main.NavigateableLayout;
import main.Pointer;

public class Generator implements MainData, GeneratorData {
	private static boolean T = true;

	private static Random rand = new Random();
//	private static Move net;
	private static NavigateableLayout cube;
	private static Pointer pointer;
	private static StringBuilder log = new StringBuilder();

	public static void generate() {
		long startTime = System.currentTimeMillis();
		System.out.println(WIDTH+"x"+HEIGHT+"x"+DEPTH+"\tpieces: "+PIECE_COUNT+"\tpiece size: "+PIECE_SIZE_MIN+"-"+PIECE_SIZE_MAX);
		gen();
		log.append("\n"+cube);
		if (MAKE_PIECES==T) showPieces(cube);
		if (SAVE==T) save(cube);
		System.out.println(log.toString());
		System.out.println((System.currentTimeMillis()-startTime)/1000f+" s");
	}

	private static void gen() {
		List<Integer> colors = null;
		int colorI = 0;
		boolean reset = true, full;
		List<Move> moves = new ArrayList<>(Arrays.asList(new Move(1, 0, 0), new Move(0, 1, 0), new Move(0, 0, 1), new Move(-1, 0, 0), new Move(0, -1, 0), new Move(0, 0, -1)));
		StringBuilder path = null;
		do {
			if (reset) {
				path = new StringBuilder();
				reset = false;
				cube = new NavigateableLayout(WIDTH, HEIGHT, DEPTH);
				pointer = new Pointer(cube.w()/2, cube.h()/2, cube.d()/2);
				colors = getColorList();
				colorI = 0;
			}
			boolean couldMove = false;
			scrambleMoves(moves);
			for (int i = 0;i<moves.size();i++) {
				Move move = moves.get(i);
				Pointer tempPointer = pointer.getMoved(move.getX(), move.getY(), move.getZ());
				if (cube.inBounds(tempPointer)&&((cube.get(tempPointer)==colors.get(colorI)&&rand.nextBoolean())||!cube.isOccupied(tempPointer))) {
					pointer = tempPointer;
					couldMove = true;
					break;
				}
			}
			if (!couldMove) reset = true;
			if (cube.get(pointer)!=colors.get(colorI)) {
				cube.set(pointer, colors.get(colorI));
				colorI++;
			}
			if (PRINT_PATH) {
				path.append(pointer.toString());
				if (cube.get(pointer)!=colors.get(colorI)) path.append("    "+colors.get(colorI));
				path.append("\n");
			}
			full = cube.isFull();
			if (full&&!(isValid())) reset = true;
		} while (reset||!full);
		if (PRINT_PATH==T) System.out.println(path);
	}

	private static void scrambleMoves(List<Move> moves) {
		int randInt = rand.nextInt(moves.size());
		Collections.swap(moves, randInt, moves.size()-1-randInt);
	}

	private static List<Integer> getColorList() {
		int[] counts = new int[PIECE_COUNT];
		for (int i = 0;i<counts.length;i++) counts[i] = PIECE_SIZE_MIN;
		int total = PIECE_SIZE_MIN*(PIECE_COUNT);
		Random rand = new Random();
		while (total<DEPTH*HEIGHT*WIDTH) {
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
		if (RUN_CHECKS!=T) return true;
		if (CHECK_FLAT==T&&hasFlat()) {
			if (CHECK_PRINT) System.out.println("flat");
			return false;
		}
		if (CHECK_TOO_MANY_ON_PLANE==T&&tooManyOnPlane()) {
			if (CHECK_PRINT) System.out.println("too many on plane");
			return false;
		}
		if ((CHECK_SURROUNDED_COLLISION==T||CHECK_DIAGONAL_COLLISION==T)&&hasCollision()) {
			if (CHECK_PRINT) System.out.println("collision");
			return false;
		}
		if (CHECK_2D_CLUSTERS==T&&has2DClusters()) {
			if (CHECK_PRINT) System.out.println("2d");
			return false;
		}
		if (CHECK_3D_CLUSTERS==T&&has3DClusters()) {
			if (CHECK_PRINT) System.out.println("3d");
			return false;
		}
		if (CHECK_IDENTICAL==T&&hasIdentical()) {
			if (CHECK_PRINT) System.out.println("has identical");
			return false;
		}
		return true;
	}

	private static boolean hasIdentical() {
		List<Layout> pieces = getPieces();
		for (int i = 0;i<pieces.size();i++) {
			for (int o = 0;o<6;o++) {
				pieces.get(i).rotate((o==4)?1:(o==5)?2:0, (o>=1&&o<=4)?1:0, 0);
				for (int r = 0;r<4;r++) {
					pieces.get(i).rotate(0, 0, 1);
					for (int j = 0;j<pieces.size();j++) {
						if (i!=j&&pieces.get(i).equals(pieces.get(j))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean hasCollision() {
		for (int z = 0;z<cube.d();z++) {
			for (int y = 0;y<cube.h();y++) {
				for (int x = 0;x<cube.w();x++) {
					if (CHECK_DIAGONAL_COLLISION==T) for (int a = 0;a<3;a++) {
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
						if ((!(tl==bl||tr==br||tl==tr||tr==br||!(tl==br||tr==bl)))||(tl==br&&tr==bl&&tl!=tr)) return true;
					}
					if (CHECK_SURROUNDED_COLLISION==T) for (int a = 0;a<3;a++) {
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
		int count = 0;
		for (int z = 0;z<cube.d();z++) {
			for (int y = 0;y<cube.h();y++) {
				for (int x = 0;x<cube.w();x++) {
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
							if (counts.get(i)>=4) count++;
							if (count>MAX_2D_CLUSTERS) return true;
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean has3DClusters() {
		for (int z = 0;z<cube.d()-1;z++) {
			for (int y = 0;y<cube.h()-1;y++) {
				for (int x = 0;x<cube.w()-1;x++) {
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
						if (counts.get(i)>MAX_3D_CLUSTER_SIZE) return true;
					}
				}
			}
		}
		return false;
	}

	private static boolean tooManyOnPlane() {
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
				if (zeroCount==planeCounts.get(i).length-1) {
					flatCount++;
				}
			}
		}
		return flatCount!=0;
	}

	private static List<int[][]> getPlaneCounts() {
		List<int[][]> planeCounts = new ArrayList<>();
		for (int i = 0;i<PIECE_COUNT;i++) {
			planeCounts.add(new int[3][]);
			for (int j = 0;j<3;j++) planeCounts.get(i)[j] = new int[(j==0)?cube.d():(j==1)?cube.h():cube.w()];
		}
		for (int z = 0;z<cube.d();z++) {
			for (int y = 0;y<cube.h();y++) {
				for (int x = 0;x<cube.w();x++) {
					int cubieColor = cube.get(x, y, z);
					planeCounts.get(cubieColor-1)[X][x]++;
					planeCounts.get(cubieColor-1)[Y][y]++;
					planeCounts.get(cubieColor-1)[Z][z]++;
				}
			}
		}
		return planeCounts;
	}

	public static List<Layout> getPieces() {
		List<Layout> pieces = new ArrayList<>();
		for (int i = 0;i<PIECE_COUNT;i++) {
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
		return pieces;
	}

	public static void showPieces(Layout cube) {
		Generator.cube = new NavigateableLayout(cube.getLayout());
		List<int[][][]> pieceLayouts = new ArrayList<>();
		for (int i = 0;i<PIECE_COUNT;i++) {
			pieceLayouts.add(new int[cube.d()][cube.h()][cube.w()]);
			for (int z = 0;z<cube.d();z++) {
				for (int y = 0;y<cube.h();y++) {
					for (int x = 0;x<cube.w();x++) {
						int color = i+1;
						if (cube.get(x, y, z)==color) pieceLayouts.get(i)[z][y][x] = cube.get(x, y, z);
					}
				}
			}
		}
		List<Layout> pieces = new ArrayList<>();
		for (int i = 0;i<pieceLayouts.size();i++) {
			Layout piece = new Layout(pieceLayouts.get(i));
			piece.trim();
			pieces.add(piece);
		}
		log.append("\n");
		for (int i = 0;i<pieces.size();i++) {
			log.append(pieces.get(i)+"\n");
		}
	}

	public static void save(Layout cube) {
		try {
			String name = new SimpleDateFormat("MM-dd-yy HH_mm_ss").format(new Date());
			name = cube.w()+"x"+cube.h()+"x"+cube.d()+" "+PIECE_COUNT+" "+PIECE_SIZE_MIN+"-"+PIECE_SIZE_MAX+" "+name;
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(PATH+"/"+name)));
			bw.write(cube.toString());
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
//
//	public static Move getNet() {
//		return net;
//	}
}
//	top-bottom/left-right/back-front
//	private static final int[][] OVERHANGS = {{1,0}, {1,0,0}, {0,1,0}, {1,1,0}};
//
//	private static boolean hasOverhang() {
//		int[][][] pieceLayout = null;
//		for (int p = 0;p<PIECE_COUNT;p++) {
//			pieceLayout = new int[SIZE][SIZE][SIZE];
//			for (int z = 0;z<SIZE;z++) {
//				for (int y = 0;y<SIZE;y++) {
//					for (int x = 0;x<SIZE;x++) {
//						int color = p+1;
//						if (cube.get(x, y, z)==color) pieceLayout[z][y][x] = cube.get(x, y, z);
//					}
//				}
//			}
//			Layout piece = new Layout(pieceLayout);
//			piece.trim();
//			System.out.println(piece);
//			if (piece.d()==1||piece.h()==1||piece.w()==1) continue;
//			int safeSides = 6;
//			for (int o = 0;o<6;o++) {
//				piece.rotate((o==4)?1:(o==5)?2:0, (o>=1&&o<=4)?1:0, 0);
//				boolean found = false;
//				for (int z = 0;!found&&z<piece.d();z++) {
//					for (int x = 0;!found&&x<piece.w();x++) {
//						int[] colors = new int[piece.h()];
//						for (int y = 0;!found&&y<piece.h();y++) {
//							if (piece.get(x, y, z)!=0) piece.set(x, y, z, 1);
//							colors[y] = piece.get(x, y, z);
//						}
//						for (int i = 0;i<OVERHANGS.length;i++) {
//							System.out.println(Arrays.toString(colors)+"\t"+Arrays.toString(OVERHANGS[i]));
//							if (colors.length!=OVERHANGS[i].length) continue;
//							boolean match = true;
//							for (int j = 0;j<OVERHANGS[i].length;j++) {
//								if (colors[j]!=OVERHANGS[i][j]) match = false;
//							}
//							if (match) {
//								System.out.println("found\t"+safeSides+"\n"+piece);
//								found = true;
//								break;
//							}
//						}
//					}
//				}
//				if (found) safeSides--;
//			}
//			if (safeSides==0) return true;
//		}
//		return false;
//	}