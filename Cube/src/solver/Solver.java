package solver;

import java.util.ArrayList;
import java.util.List;

import data.MainData;
import data.ToSolve;
import main.Layout;

public class Solver implements MainData {

	public static void solve() {
		int[][][][] rawPieceLayouts = ToSolve.getUnsolved();
		List<Layout> pieces = new ArrayList<>();
		for (int p = 0;p<rawPieceLayouts.length;p++) {
			pieces.addAll(Piece.getAllPermutations(new Layout(rawPieceLayouts[p])));
		}
		System.out.println(pieces);
















	}




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
	}

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
//}










