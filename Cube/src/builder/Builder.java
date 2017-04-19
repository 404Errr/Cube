package builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import data.BuilderData;
import main.Layout;
import main.Pointer;

public class Builder implements BuilderData {
	public static void build() {
		int[][][][] rawPieceLayouts = TO_BUILD;
		List<BuildOrder> pieces = new ArrayList<>();
		for (int p = 0;p<rawPieceLayouts.length;p++) {
			pieces.add(new BuildOrder(new Layout(rawPieceLayouts[p])));
		}		
		for (int p = 0;p<pieces.size();p++) {
			boolean done = false;
			BuildOrder piece = pieces.get(p);
			Pointer pointer = piece.getMinNeighbors(), last = piece.getMinNeighbors();
			while (!done) {
				last = pointer.getMoved(0, 0, 0);//clone
				Stack<int[]> moves = new Stack<>();
				moves.addAll(Arrays.asList(new int[] {1, 0, 0}, new int[] {0, 1, 0}, new int[] {0, 0, 1}, new int[] {-1, 0, 0}, new int[] {0, -1, 0}, new int[] {0, 0, -1}));
				Collections.shuffle(moves);
				while (!moves.isEmpty()) {
					int[] move = moves.pop();
					Pointer tempPointer = pointer.getMoved(move[0], move[1], move[2]);
					if (piece.inBounds(tempPointer)&&piece.get(tempPointer).getColor()!=0) {
						pointer = tempPointer;
						break;
					}
				}
//				if (piece.get(pointer).getNext()!=piece.get(prior)) {
				if (piece.get(last).getNext()==null) piece.get(last).setNext(piece.get(pointer));
//				}
				
				System.out.println("\n"+last+"\t"+(char)piece.get(last).getDirection());
				System.out.println(pointer+"\t"+(char)piece.get(pointer).getDirection());
				System.out.println(piece);
				
				if (piece.isComplete()) {
					done = true;
				}
				if (new Random().nextInt(100)==0) piece = new BuildOrder(new Layout(rawPieceLayouts[p]));
			}
		}
		for (int p = 0;p<pieces.size();p++) {
			System.out.println(pieces.get(p));
		}
	}
}
