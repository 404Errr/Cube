package generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import data.GeneratorData;
import main.Layout;
import main.Pointer;

public class Generator implements GeneratorData {
	private static NavigateableLayout cube;
	private static Pointer currentPointer;
	private static boolean print;
	private static StringBuilder log;

	static {
		log = new StringBuilder();
	}

	public static void generate() {
		long startTime = System.currentTimeMillis();
		gen();
		if (PRINT_LOG_STUFF) {
			print = true;
			isValid();
		}
		log.append("\n"+cube);
		if (MAKE_PIECES==new Boolean(true)) {
			if (HIDE_SOLUTION==new Boolean(true)) for (int i = 0;i<15;i++) log.append("\n");
			makePieces();
		}
		System.out.println(log.toString());
		System.out.print("\n"+(System.currentTimeMillis()-startTime)/1000f+" s\n\n");
	}

	private static final int BACKTRACK_CHANCE = 3;
	private static final int PREVENT_FORWARD_CHANCE = 2;
	private static void gen() {
		Random rand = new Random();
		int color = 1, cubieCount = 0;
		Stack<int[]> moves = new Stack<>();
		moves.addAll(Arrays.asList(new int[] {1, 0, 0}, new int[] {0, 1, 0}, new int[] {0, 0, 1}, new int[] {-1, 0, 0}, new int[] {0, -1, 0}, new int[] {0, 0, -1}));
		Collections.shuffle(moves);
		boolean reset = true, full = false;
		do {
			if (reset) {
				reset = false;
				cube = new NavigateableLayout(SIZE);
				currentPointer = new Pointer(1, 1, 1);
				color = 1;
				cubieCount = 0;
			}
			boolean couldMove = false;
//			while (!moves.isEmpty()) {
			for (int i = 0;i<moves.size();i++) {//TODO
//				int[] move = moves.pop();
				int[] move = moves.get(i);
//				System.out.println(Arrays.toString(moves.get(i)));
				Pointer tempPointer = currentPointer.getMoved(move[0], move[1], move[2]);
				if (cube.inBounds(tempPointer)&&((cube.get(tempPointer)==color&&rand.nextInt(BACKTRACK_CHANCE)==0)||!cube.isOccupied(tempPointer))) {
					currentPointer = tempPointer;
					if (rand.nextInt(PREVENT_FORWARD_CHANCE)==0) moves.push(moves.remove(i));
					couldMove = true;
					break;
				}
			}
			if (!couldMove||color>PIECE_COUNT) {
				reset = true;
				continue;
			}
			if (cube.get(currentPointer)!=color) {
				cubieCount++;
				cube.set(currentPointer, color);
			}
			if (shouldAdvanceColor(cubieCount)) {
				color++;
				cubieCount = 0;
			}
			full = cube.isFull();
			if (full&&!isValid()) reset = true;
		} while (reset||!full);
	}

	private static boolean shouldAdvanceColor(int cubieCount) {
		return cubieCount>=new Random().nextInt(PIECE_SIZE_MAX-PIECE_SIZE_MIN+1)+PIECE_SIZE_MIN;
	}

	private static boolean isValid() {		
		
//		if (hasOverhang()) {//FIXME
////			System.out.println("hasOverhang\n"+cube);
//			return false;
//		}

		if (hasFlat()) {
//			System.out.println("flat");
			return false;
		}
		if (tooManyOnPlane()) {
//			System.out.println("boring");
			return false;
		}
		if (!hasCollision()) {
//			System.out.println("has collision");
			return false;
		}
		if (has3DClusters()) {
//			System.out.println("3d");
			return false;
		}
		if (has2DClusters()) {
//			System.out.println("2d");
			return false;
		}
		if (hasIdentical()) {
			System.out.println("has identical\n\n"+cube);
			return false;
		}
		return true;
	}
	
	private static boolean hasIdentical() {//TODO test
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
					if (pieces.get(i).equals(pieces.get(j))) return true;
				}
			}
		}
		return false;
	}

	private static boolean hasOverhang() {//FIXME
		int[][][] pieceLayout = null;
		for (int i = 0;i<PIECE_COUNT;i++) {
			pieceLayout = new int[SIZE][SIZE][SIZE];
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
			if (piece.d()==1||piece.h()==1||piece.w()==1) continue;
			boolean hasOverhang = true;
			for (int o = 0;o<6;o++) {//every side
				piece.rotate((o==4)?1:(o==5)?2:0, (o>=1&&o<=4)?1:0, 0);
				int overhangs = 0;
				for (int z = 0;z<piece.d();z++) {
					for (int y = 0;y<piece.h();y++) {
						for (int x = 0;x<piece.w();x++) {
							if (piece.inBounds(x, y-1, z)) {
								if (piece.get(x, y, z)==0&&piece.get(x, y-1, z)!=0) {
									overhangs++;
								}
							}
						}
					}
				}
//				System.out.println("\n"+piece+"o "+o+"\toverhangs "+overhangs+"\tcount "+count+"\tprintable "+printable);								
				if (overhangs==0) {
					hasOverhang = false;
					break;
				}
			}
			if (!hasOverhang) continue;
			System.out.println(piece);
			return true;
		}
		return false;
	}

	private static boolean hasCollision() {
		for (int z = 0;z<SIZE;z++) {
			for (int y = 0;y<SIZE;y++) {
				for (int x = 0;x<SIZE;x++) {
					for (int a = 0;a<3;a++) {//2x2x1
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
						if (colors.size()!=4) continue;
						int tl = colors.get(0), tr = colors.get(1), bl = colors.get(2), br = colors.get(3);
						if (tl==bl||tr==br||tl==tr||tr==br||tl!=br||tr!=bl) continue;
						return true;
					}
					for (int a = 0;a<3;a++) {//3x1x1
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
						if (colors.get(0)==colors.get(2)&&colors.get(0)!=colors.get(1)) {
							System.out.println("3x1x1\n"+cube);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean has2DClusters() {
		if (print) log.append("\nclusterCounts2D:\n");
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
						if (print) log.append(counts+"\n");
					}
				}
			}
		}
		return false;
	}

	private static boolean has3DClusters() {
		if (print) log.append("\nclusterCounts3D:\n");
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
					if (print) log.append(counts+"\n");
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
		if (print) {
			for (int i = 0;i<planeCounts.size();i++) {
				log.append((i+1)+"\n");
				for (int j = 0;j<planeCounts.get(i).length;j++) {
					log.append(((j==X)?"x":(j==Y)?"y":"z")+" "+Arrays.toString(planeCounts.get(i)[j])+"\n");
				}
				log.append("\n");
			}
			log.append("flatCount: "+flatCount+"\n");
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
		List<Integer> colorCount = new ArrayList<>();
		for (int z = 0;z<SIZE;z++) {
			for (int y = 0;y<SIZE;y++) {
				for (int x = 0;x<SIZE;x++) {
					if (!colorCount.contains(cube.get(x, y, z))) {
						colorCount.add(cube.get(x, y, z));
					}
				}
			}
		}
		List<int[][][]> pieceLayouts = new ArrayList<>();
		for (int i = 0;i<colorCount.size();i++) {
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
			piece.trim();
			piece.rotate(rand.nextInt(4), 3, 1);
			pieces.add(piece);
		}
		log.append("\n");
		for (int i = 0;i<pieces.size();i++) {
			log.append(pieces.get(i)+"\n");
		}
		System.out.println(log.toString());
	}

	private static void makePieces() {
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
			piece.trim();
			if (HIDE_SOLUTION) piece.rotate(rand.nextInt(4), 3, 1);
			pieces.add(piece);
		}
		log.append("\n");
		for (int i = 0;i<pieces.size();i++) {
			log.append(pieces.get(i)+"\n");
		}
	}
}

