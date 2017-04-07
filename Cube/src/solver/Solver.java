package solver;

import java.util.ArrayList;
import java.util.List;

import cube.Layout;
import cube.Piece;
import data.MainData;

public class Solver implements MainData {
	public static void solve(int[][][][] pieceLayouts) {
//		new Piece(ToSolve.SIDES6);
		List<Piece> pieces = new ArrayList<>();
		for (int p = 0;p<pieceLayouts.length;p++) {
			pieces.add(new Piece(pieceLayouts[p]));
		}
//		List<int[]> orders = Util.getPermutations(pieces.size());
//		for (int i = 0;i<orders.size();i++) System.out.println(Arrays.toString(orders.get(i)));

		List<List<int[][][]>> spaces = new ArrayList<>();

		for (int p = 0;p<pieces.size();p++) {
			List<int[][][]> pieceSpaces = new ArrayList<>();
			spaces.add(pieceSpaces);
			List<Layout> layouts = pieces.get(p).getLayouts();

			for (int l = 0;l<layouts.size();l++) {
				Layout layout = layouts.get(l);
				for (int zO = 0;zO<SIZE;zO++) {
					for (int yO = 0;yO<SIZE;yO++) {
						for (int xO = 0;xO<SIZE;xO++) {
							if (SIZE-layout.d()>zO||SIZE-layout.h()>yO||SIZE-layout.w()>xO) continue;
							int[][][] pieceSpace = new int[SIZE][SIZE][SIZE];
							pieceSpaces.add(pieceSpace);
							for (int z = 0;z<SIZE;z++) {
								for (int y = 0;y<SIZE;y++) {
									for (int x = 0;x<SIZE;x++) {

									}
								}
							}
						}
					}
				}
			}



		}



	}
}
