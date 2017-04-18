package builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import data.BuilderData;
import main.NavigateableLayout;
import main.Pointer;

public class Builder implements BuilderData {
	public static void build() {
		int[][][][] rawPieceLayouts = TO_BUILD;
		List<NavigateableLayout> pieces = new ArrayList<>();
		for (int p = 0;p<rawPieceLayouts.length;p++) {
			pieces.add(new NavigateableLayout(rawPieceLayouts[p]));
		}
		for (int p = 0;p<pieces.size();p++) {
			boolean done = false;
			while (!done) {
				NavigateableLayout piece = pieces.get(p);
				Pointer pointer = null;
				for (int z = 0;pointer==null&&z<piece.d();z++) {
					for (int y = 0;pointer==null&&y<piece.d();y++) {
						for (int x = 0;pointer==null&&x<piece.d();x++) {
							if (piece.get(x, y, z)!=0) {
								pointer = new Pointer(x, y, z);
							}
						}
					}
				}
				System.out.println(piece);
				System.out.println(pointer);
				
				Stack<int[]> moves = new Stack<>();
				moves.addAll(Arrays.asList(new int[] {1, 0, 0}, new int[] {0, 1, 0}, new int[] {0, 0, 1}, new int[] {-1, 0, 0}, new int[] {0, -1, 0}, new int[] {0, 0, -1}));
				Collections.shuffle(moves);
				
				while (!moves.isEmpty()) {
					int[] move = moves.pop();
					Pointer tempPointer = pointer.getMoved(move[0], move[1], move[2]);
					if (piece.inBounds(tempPointer)&&piece.get(pointer)!=0) {
						pointer = tempPointer;
						break;
					}
					
				}
				//TODO check if cube has been filled with directions, exit if so



				System.out.println(pointer);

			}
		}
	}
}
