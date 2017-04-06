package main;

import data.ToSolve;
import solver.Solver;

public class CubeMain {
	public static void main(String[] args) {
		Solver.solve(ToSolve.getUnsolved());
	}

//	public static void main(String[] args) {
//		Scanner scan = new Scanner(System.in);
//		StringBuilder str = new StringBuilder();
//		int i = 1;
//		while (true) {
//			String input = scan.nextLine().toUpperCase();
//			if (input.length()>0&&!Character.isAlphabetic(input.charAt(0))) break;
////			str.append("\'"+input.charAt(0)+"\', ");
//			str.append("int "+input.charAt(0)+"_COLOR = "+i+";\n");
//		}
//		scan.close();
//		System.out.println(str.toString());
////		System.out.println("{"+str.toString().substring(0, str.length()-2)+"}");
//	}
}
