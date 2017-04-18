package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import data.SolverData;
import data.ToSolve;
import main.Layout;

public class Solver implements SolverData {
	private static List<Layout> solutions;
	private static List<List<Layout>> layouts;

	public static void solve() {
		long startTime = System.currentTimeMillis();
		int[][][][] rawPieceLayouts = ToSolve.getUnsolved();
		List<Piece> pieces = new ArrayList<>();
		for (int p = 0;p<rawPieceLayouts.length;p++) {
			pieces.add(new Piece(rawPieceLayouts[p]));
		}
		Collections.sort(pieces);//larger first
		layouts = new ArrayList<>();
		for (int p = 0;p<pieces.size();p++) {
			layouts.add(new ArrayList<>());
			Layout layout = pieces.get(p).getLayout().clone();
			for (int o = 0;o<6;o++) {//every side
				layout.rotate((o==4)?1:(o==5)?2:0, (o>=1&&o<=4)?1:0, 0);
				for (int r = 0;r<4;r++) {//every rotation of every side
					layout.rotate(0, 0, 1);
					for (int zO = 0;zO<=SIZE-layout.d();zO++) {
						for (int yO = 0;yO<=SIZE-layout.h();yO++) {
							for (int xO = 0;xO<=SIZE-layout.w();xO++) {
								Layout tempCube = new Layout(3);
								if (tempCube.append(layout, xO, yO, zO)) {
									layouts.get(p).add(tempCube);
//									System.out.println(zO+"\t"+yO+"\t"+xO+"\n"+tempCube);
								}
							}
						}
					}
					if (p==0) break;
				}
				if (p==0) break;
			}
			for (int i = layouts.get(p).size()-1;i>=0;i--) {
				for (int j = 0;j<i;) {
					if (layouts.get(p).get(i).equals(layouts.get(p).get(j))) {
						layouts.get(p).remove(j);
						i--;
					}
					else j++;
				}
			}
			Collections.sort(layouts.get(p));
		}
//		for (List<Layout> piece:layouts) {
//			for (Layout layout:piece) {
//				System.out.println(layout);
//			}
//		}
		solutions = new ArrayList<>();
		int[] is = new int[layouts.size()], limits = new int[layouts.size()];
		for (int i = 0;i<limits.length;i++) limits[i] = layouts.get(i).size();
		System.out.println(Arrays.toString(limits)+"\n");
		do {
			Layout potentialSolution = new Layout(3);
			for (int i = 0;i<layouts.size();i++) {
				if (!potentialSolution.append(layouts.get(i).get(is[i]))) break;
				else if (i==layouts.size()-1) {
					solutions.add(potentialSolution);
				}
			}
//			if (is[1]==0&&is[0]==0&&is[2]==0&&is[3]==0) System.out.println(Arrays.toString(is));
		} while (!incrementArray(is, limits)&&(solutions.isEmpty()||FIND_ALL));
		System.out.print((System.currentTimeMillis()-startTime)/1000f+" s\n\n");
		if (solutions.isEmpty()) System.out.println("No solutions :(");
		else for (int i = 0;i<solutions.size();i++) System.out.println(solutions.get(i));
	}

	public static boolean incrementArray(int[] array, int[] limits) {//true if at max, inclusive-exlusive
		array[0]++;
		for (int i = 1;i<=array.length;i++) {
			if (array[i-1]==limits[i-1]) {
				if (i<array.length) {
					array[i-1] = 0;
					array[i]++;
				}
				else return true;
			}
		}
		return false;
	}

//	public static Layout lay = new Layout(3);
//
//	private static void sol(int x) {
//		System.out.println(x);
//		if (x==0) {
////			lay.append(toAppend)
//		}
//		else {
//			for (int i = 0;i<layouts.size();i++) {
//				sol(x-1);
//			}
//		}
//	}
//
//	private static void sol(int[] order, Space[] spaces, List<List<Space>> spacesData, int depth) {
//	if (depth==0) {
//		Cube cube = new Cube();
//		boolean fail = false;
//		for (int i = 0;i<spaces.length;i++) {
//			if (!cube.overLay(spaces[i])) {
//				fail = true;
//				break;
//			}
//		}
//		if (!fail) Solver.cube = cube;
//		return;
//	}
//	for (int i = 0;i<spacesData.get(depth-1).size();i++) {
//		spaces[depth-1] = spacesData.get(depth-1).get(i);
//		sol(order, spaces, spacesData, depth-1);
//	}
//	return;
//}
//
//	public static Layout merge(Layout layout0, Layout layout1) {
//		Layout merged = layout0.clone();
//		if (!merged.append(layout1)) return null;
//		return merged;
//	}
//
//	public static List<Layout> combine(Layout l0, Layout l1) {
//			List<Layout> pairs = new ArrayList<>();
//			Layout working0;
//			working0 = l0.clone();
//			for (int o = 0;o<6;o++) {//every side
//				working0.rotate((o==4)?1:(o==5)?2:0, (o>=1&&o<=4)?1:0, 0);
//				for (int r = 0;r<4;r++) {//every rotation of every side
//					working0.rotate(0, 0, 1);
//					for (int zO = 0;zO<=SIZE-working0.d();zO++) {
//						for (int yO = 0;yO<=SIZE-working0.h();yO++) {
//							for (int xO = 0;xO<=SIZE-working0.w();xO++) {
//								System.out.println(zO+"\t"+yO+"\t"+xO+"\n"+working0);
//
//								Layout working1;
//								working0 = l.clone();
//							}
//						}
//					}
//
//				}
//			}
//			return pairs;
//		}
//	}
//
//		Layout working0 = l0.clone();
//		for (int o0 = 0;o0<6;o0++) {//every side
//			working0.rotate((o0==4)?1:(o0==5)?2:0, (o0>=1&&o0<=4)?1:0, 0);
//			for (int r0 = 0;r0<4;r0++) {//every rotation of every side
//				working0.rotate(0, 0, 1);
//				for (int zO0 = 0;zO0<=SIZE-working0.d();zO0++) {
//					for (int yO0 = 0;yO0<=SIZE-working0.h();yO0++) {
//						for (int xO0 = 0;xO0<=SIZE-working0.w();xO0++) {
//							System.out.println(zO0+"\t"+yO0+"\t"+xO0+"\n"+working0);
//							Layout working1 = l1.clone();
//							for (int o1 = 0;o1<6;o1++) {//every side
//								working1.rotate((o1==4)?1:(o1==5)?2:0, (o1>=1&&o1<=4)?1:0, 0);
//								for (int r1 = 0;r1<4;r1++) {//every rotation of every side
//									working1.rotate(0, 0, 1);
//									for (int zO1 = 0;zO1<=SIZE-working1.d();zO1++) {
//										for (int yO1 = 0;yO1<=SIZE-working1.h();yO1++) {
//											for (int xO1 = 0;xO1<=SIZE-working1.w();xO1++) {
//												Layout c = new Layout(3);
//												boolean success = true;
//												for (int z = 0;z<working0.d();z++) {
//													for (int y = 0;y<working0.h();y++) {
//														for (int x = 0;x<working0.w();x++) {
//															System.out.println((xO1)+"\t"+(yO1)+"\t"+(zO1)+"\t\t"+(x)+"\t"+(y)+"\t"+(z)+"\t\t"+(xO1+x)+"\t"+(yO1+y)+"\t"+(zO1+z));
//															c.set(xO1+x, yO1+y, zO1+z, working0.get(x, y, z));
////															int v0 = working0.get(x, y, z);
////															int v1 = working1.get(x, y, z);
////															c.set(x, y, z, v0);
////															if (v0==0) {
////																if (v1!=0) c.set(x, y, z, v1);
////																else success = false;
////															}
//														}
//													}
//												}
//												Layout combined = working0.clone();
//												if (combined.append(working1)) pairs.add(combined);
//
//												System.out.println(zO1+"\t"+yO1+"\t"+xO1+"\n"+working1);
//											}
//										}
//									}
//
//								}
//							}
//						}
//					}
//				}
//
//			}
//		}
//		return null;
//
//
//
//	public static void solve() {
//		int[][][][] rawPieceLayouts = ToSolve.getUnsolved();
//		List<Layout> pieces = new ArrayList<>();
//
//		for (int p = 0;p<rawPieceLayouts.length;p++) {
//			pieces.addAll(Piece.getAllPermutations(new Layout(rawPieceLayouts[p])));
//		}
//		System.out.println(pieces);
//
//
//	}
//
//
//
//
//	public static void solve() {
//		System.out.println("no solution found.");
//
//		int[][][][] pieceLayouts = ToSolve.getUnsolved();
////		new Piece(ToSolve.SIDES6);
//		List<Piece> pieces = new ArrayList<>();
//		for (int p = 0;p<pieceLayouts.length;p++) {
//			pieces.add(new Piece(pieceLayouts[p]));
//		}
//		List<List<int[][][]>> piecesSpaces = new ArrayList<>();
//		for (int p = 0;p<pieces.size();p++) {
//			List<int[][][]> pieceSpaces = new ArrayList<>();
//			piecesSpaces.add(pieceSpaces);
//			List<Layout> layouts = pieces.get(p).getLayouts();
//			for (int l = 0;l<layouts.size();l++) {
//				Layout layout = layouts.get(l);
//				for (int zO = 0;zO<SIZE;zO++) {
//					for (int yO = 0;yO<SIZE;yO++) {
//						for (int xO = 0;xO<SIZE;xO++) {
//							if (zO+layout.d()<=SIZE&&yO+layout.h()<=SIZE&&xO+layout.w()<=SIZE) {
//								int[][][] pieceSpace = new int[SIZE][SIZE][SIZE];
//								pieceSpaces.add(pieceSpace);
//								for (int z = 0;z<layout.d();z++) {
//									for (int y = 0;y<layout.h();y++) {
//										for (int x = 0;x<layout.w();x++) {
//											pieceSpace[z+zO][y+yO][x+xO] = layout.get(x, y, z);
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		List<List<Space>> spaces = new ArrayList<>();
//		for (int p = 0;p<piecesSpaces.size();p++) {
//			List<Space> pieceSpaces = new ArrayList<>();
//			spaces.add(pieceSpaces);
//			for (int p1 = 0;p1<piecesSpaces.get(p).size();p1++) {
//				Space space = new Space(piecesSpaces.get(p).get(p1));
//				spaces.get(p).add(space);
//			}
//			System.out.println(spaces.get(p).size());
//		}
//		Stack<int[]> orders = new Stack<>();
//		orders.addAll(Util.getPermutations(pieces.size()));
//		while (!orders.isEmpty()) {
//			int[] order = orders.pop();
//			System.out.println("order: "+Arrays.toString(order));
//			sol(order, new Space[5], spaces, spaces.size());
//			if (cube!=null) break;
//		}
//		System.out.println(cube);
//	}
//
//	private static void sol(int[] order, Space[] spaces, List<List<Space>> spacesData, int depth) {
//		if (depth==0) {
//			Cube cube = new Cube();
//			boolean fail = false;
//			for (int i = 0;i<spaces.length;i++) {
//				if (!cube.overLay(spaces[i])) {
//					fail = true;
//					break;
//				}
//			}
//			if (!fail) Solver.cube = cube;
//			return;
//		}
//		for (int i = 0;i<spacesData.get(depth-1).size();i++) {
//			spaces[depth-1] = spacesData.get(depth-1).get(i);
//			sol(order, spaces, spacesData, depth-1);
//		}
//		return;
//	}
}










