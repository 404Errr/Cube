package allPieces;

import java.util.ArrayList;
import java.util.List;

import data.AllPiecesData;
import main.Layout;
import main.NavigateableLayout;
import main.Pointer;

public class AllPieces implements AllPiecesData {	
	
	public static int[][][] TEST = {
		{
			{0,5,5},
			{0,5,5},
			{0,0,0},	
		},
		{
			{0,5,0},
			{0,0,0},
			{0,0,0},	
		},
		{
			{0,5,0},
			{0,5,5},
			{0,5,0},	
		},
	};
	
	
	public static void generate() {
		List<Layout> pieces = new ArrayList<>();
		Layout piece = new NavigateableLayout(CONSTRAINT, CONSTRAINT, CONSTRAINT);
		piece = new Layout(TEST);
		Pointer pointer = new Pointer(1,1,1);
		System.out.println();
		
	}
//
//	public static boolean isContinuous(Layout piece) {
//		for (int z = 0;z<piece.d();z++) {
//			for (int y = 0;y<piece.h();y++) {
//				for (int x = 0;x<piece.w();x++) {
//					if (piece.get(x, y, z)==0) continue;
//					boolean hasNeighbor = false;
//					if (piece.inBounds(x+1, y, z)&&piece.get(x+1, y, z)!=0) hasNeighbor = true;
//					if (piece.inBounds(x, y+1, z)&&piece.get(x, y+1, z)!=0) hasNeighbor = true;
//					if (piece.inBounds(x, y, z+1)&&piece.get(x, y, z+1)!=0) hasNeighbor = true;
//					if (piece.inBounds(x-1, y, z)&&piece.get(x-1, y, z)!=0) hasNeighbor = true;
//					if (piece.inBounds(x, y-1, z)&&piece.get(x, y-1, z)!=0) hasNeighbor = true;
//					if (piece.inBounds(x, y, z-1)&&piece.get(x, y, z-1)!=0) hasNeighbor = true;
//					if (!hasNeighbor) return false;
//				}
//			}
//		}
//		return true;
//	}
}

