package main;

import data.MainData;
import generator.Generator;
import solver.Solver;

public class CubeMain implements MainData {
	private static final int FUNCTION = SOLVE;
//	private static final int FUNCTION = MAKE;

	public static void main(String[] args) {
		switch (FUNCTION) {
		case SOLVE:
			System.out.println("Solving...");
			Solver.solve();
			break;
		case MAKE:
			System.out.println("Generating...");
			Generator.generate();
			break;
		}
	}
}
