package main;

import data.Data;
import data.MainData;
import generator.Generator;
import solver.Solver;

public class CubeMain implements MainData, Data {
//	private static final int FUNCTION = SOLVE;
	private static final int FUNCTION = GENERATE;
	private static final int REPEAT = 0;

	public static void main(String[] args) {
		switch (FUNCTION) {
		case SOLVE:
			System.out.println("Solving...");
			Solver.solve();
			break;
		case GENERATE:
			for (int i = 0;i<=REPEAT;i++) {
				System.out.println("Generating...");
				Generator.generate();
			}
			break;
			hey look over here
//		case CONVERT://TODO
//			Converter.convert();
		default:
			System.out.println("Invalid function.");
			break;
		}
	}
}
