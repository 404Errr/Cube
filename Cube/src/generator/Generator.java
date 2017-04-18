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
	private static Cube cube;
	private static Pointer currentPointer;
	private static boolean print;
	private static StringBuilder log;
	private static final boolean MAKE_PIECES = true, HIDE_SOLUTION = true, PRINT_LOG_STUFF = false;

	static {
		log = new StringBuilder();
	}

	public static void generate() {
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

	}

	private static void gen() {
		List<Integer> addedColors = null;
		int color = 1, cubieCount = 0;
		boolean reset = true, full = false;
		do {
			if (reset) {
				reset = false;
				addedColors = new ArrayList<>();
				cube = new Cube();
				currentPointer = new Pointer(SIZE/2, SIZE/2, SIZE/2);
				color = 1;
				cubieCount = 0;
			}
			Stack<int[]> moves = new Stack<>();
			moves.addAll(Arrays.asList(new int[] {1, 0, 0}, new int[] {0, 1, 0}, new int[] {0, 0, 1}, new int[] {-1, 0, 0}, new int[] {0, -1, 0}, new int[] {0, 0, -1}));
			Collections.shuffle(moves);
			boolean couldMove = false;
			while (!moves.isEmpty()) {
				int[] move = moves.pop();
				Pointer tempPointer = currentPointer.getMoved(move[0], move[1], move[2]);
				if (cube.inBounds(tempPointer)&&((cube.get(tempPointer)==color&&new Random().nextInt(3)==0)||!cube.isOccupied(tempPointer))) {
					currentPointer = tempPointer;
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
				addedColors.add(color);
				cube.add(currentPointer, color);
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
		boolean flat = hasFlat();
		boolean boring = isBoring();
		boolean clusters3D = has3DClusters();
		boolean clusters2D = has2DClusters();
//		log.append("flat: "+flat+"\tboring: "+boring+"\tclusters3D: "+clusters3D+"\tclusters2D: "+clusters2D+"\n");
//		return true;
		return !flat&&!boring&!clusters3D&&!clusters2D;
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

	private static boolean isBoring() {
		int totalCount = 0;
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
					totalCount+=count;
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
		if (print) {
			log.append("tooManyOnPlane: "+tooManyOnPlane+"\ntotalCount: "+totalCount+"\n");
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
		Generator.cube = new Cube(cube);
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

