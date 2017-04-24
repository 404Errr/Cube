package main;

import builder.Builder;
import data.Data;
import data.MainData;
import generator.Generator;
import solver.Solver;

public class CubeMain implements MainData, Data {
//	private static final int FUNCTION = SOLVE;
	private static final int FUNCTION = GENERATE;
//	private static final int FUNCTION = BUILD;

	public static void main(String[] args) {
		switch (FUNCTION) {
		case SOLVE:
			System.out.println("Solving...");
			Solver.solve();
			break;
		case GENERATE:
			System.out.println("Generating...");
			Generator.generate();
			break;
		case BUILD:
			System.out.println("Building...");
			Builder.build();
			break;
		}
	}
}
