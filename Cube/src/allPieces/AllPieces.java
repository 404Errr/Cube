package allPieces;

import java.util.ArrayList;
import java.util.List;

import data.AllPiecesData;
import main.Layout;
import main.NavigateableLayout;
import main.Pointer;

public class AllPieces implements AllPiecesData {	
		
	public static void generate() {
		List<Layout> pieces = new ArrayList<>();
		Layout piece;
		piece = new NavigateableLayout(CONSTRAINT, CONSTRAINT, CONSTRAINT);
		Pointer pointer = new Pointer(1,1,1);
		System.out.println(piece);
		//TODO
	}

}




//public static int[][][] TEST = {
//{
//	{0,5,5},
//	{0,5,5},
//	{0,0,0},	
//},
//{
//	{0,5,0},
//	{0,0,0},
//	{0,0,0},	
//},
//{
//	{0,5,0},
//	{0,5,5},
//	{0,5,0},	
//},
//};
