package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.MainData;
import data.ToSolve;
import util.Util;

public class Solver implements MainData {
	public static void solve() {
		int[][][][] pieceLayouts = ToSolve.getUnsolved();
//		new Piece(ToSolve.SIDES6);
		List<Piece> pieces = new ArrayList<>();
		for (int p = 0;p<pieceLayouts.length;p++) {
			pieces.add(new Piece(pieceLayouts[p]));
		}

		List<List<int[][][]>> piecesSpaces = new ArrayList<>();

		for (int p = 0;p<pieces.size();p++) {
			List<int[][][]> pieceSpaces = new ArrayList<>();
			piecesSpaces.add(pieceSpaces);
			List<Layout> layouts = pieces.get(p).getLayouts();
			for (int l = 0;l<layouts.size();l++) {
				Layout layout = layouts.get(l);
				for (int zO = 0;zO<SIZE;zO++) {
					for (int yO = 0;yO<SIZE;yO++) {
						for (int xO = 0;xO<SIZE;xO++) {
							if (zO+layout.d()<=SIZE&&yO+layout.h()<=SIZE&&xO+layout.w()<=SIZE) {
								int[][][] pieceSpace = new int[SIZE][SIZE][SIZE];
								pieceSpaces.add(pieceSpace);

								for (int z = 0;z<layout.d();z++) {
									for (int y = 0;y<layout.h();y++) {
										for (int x = 0;x<layout.w();x++) {
											pieceSpace[z+zO][y+yO][x+xO] = layout.get(x, y, z);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		for (int p = 0;p<piecesSpaces.size();p++) {
			for (int p1 = 0;p1<piecesSpaces.get(p).size();p1++) {
				System.out.println(new Layout(piecesSpaces.get(p).get(p1)));
			}
		}

		List<int[]> orders = Util.getPermutations(pieces.size());
		for (int i = 0;i<orders.size();i++) System.out.println(Arrays.toString(orders.get(i)));

//		int[][][] space;
//		List<List<int[][][]>> currentSpaces;
//		for (int i = 0;i<orders.size();i++) {
//			currentSpaces = Util.order(piecesSpaces, orders.get(i));
//			for (int s = 0;s<currentSpaces.size();s++) {
//				List<int[][][]> pieceSpaces = currentSpaces.get(s);
//
//
//				space = new int[SIZE][SIZE][SIZE];
//
//
//
//
//
//
//
//			}
//		}




	}



}
