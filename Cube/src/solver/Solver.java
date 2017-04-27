package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import data.MainData;
import data.SolverData;
import main.Layout;

public class Solver implements MainData, SolverData {
	private static List<Layout> solutions;
//	private static List<Solution> solutions;
//	private static Stack<Solution> potentialSolutions;
	private static List<List<Piece>> orientations;
	public static int doneCount;

	public static void solve() {
		boolean multiThread = MULTI;
		long startTime = System.currentTimeMillis();
		int[][][][] rawPieceLayouts = TO_SOLVE;
		List<OriginalPiece> pieces = new ArrayList<>();
		for (int p = 0;p<rawPieceLayouts.length;p++) {
			pieces.add(new OriginalPiece(rawPieceLayouts[p]));
		}
		Collections.sort(pieces);//larger first
		orientations = new ArrayList<>();
		for (int p = 0;p<pieces.size();p++) {
			orientations.add(new ArrayList<>());
			Layout layout = pieces.get(p).clone();
			for (int o = 0;o<6;o++) {//every side
				layout.rotate((o==4)?1:(o==5)?2:0, (o>=1&&o<=4)?1:0, 0);
				for (int r = 0;r<4;r++) {//every rotation of every side
					layout.rotate(0, 0, 1);
					for (int zO = 0;zO<=SIZE-layout.d();zO++) {
						for (int yO = 0;yO<=SIZE-layout.h();yO++) {
							for (int xO = 0;xO<=SIZE-layout.w();xO++) {
								Piece tempCube = new Piece(3);
								if (tempCube.append(layout, xO, yO, zO)) {
									orientations.get(p).add(tempCube);
								}
							}
						}
					}
					if (p==0) break;//dont rotate first
				}
				if (p==0) break;//dont rotate first
			}
			for (int i = orientations.get(p).size()-1;i>=0;i--) {
				for (int j = 0;j<i;) {
					if (orientations.get(p).get(i).equals(orientations.get(p).get(j))) {
						orientations.get(p).remove(j);
						i--;
					}
					else j++;
				}
			}
			Collections.sort(orientations.get(p));
		}
		solutions = new ArrayList<>();
		if (multiThread) {
			int threadCount = orientations.get(0).size();
			for (int i = 0;i<threadCount;i++) {
				List<List<Piece>> tempOrientations = new ArrayList<>();
				for (int p = 0;p<orientations.size();p++) {
					tempOrientations.add(new ArrayList<>());
					if (p==0) {
						tempOrientations.get(0).add(orientations.get(0).get(i));
					}
					else {
						for (int o = 0;o<orientations.get(p).size();o++) {
							tempOrientations.get(p).add(orientations.get(p).get(o));
						}
					}
				}
				Thread thread = new Thread(new SolverThread(tempOrientations, solutions), i+"");
				thread.start();
			}
			int[] limits = new int[orientations.size()];
			for (int i = 0;i<limits.length;i++) limits[i] = orientations.get(i).size();
			System.out.println(threadCount+" threads\n");
			while (doneCount<threadCount) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {}
			}
		}
		else {
			int[] is = new int[orientations.size()], limits = new int[orientations.size()];
			for (int i = 0;i<limits.length;i++) limits[i] = orientations.get(i).size();
			System.out.println(Arrays.toString(limits)+"\n");
			do {
				Layout potentialSolution = new Layout(3);
				for (int i = 0;i<orientations.size();i++) {
					if (!potentialSolution.append(orientations.get(i).get(is[i]))) break;
					else if (i==orientations.size()-1) {
						solutions.add(potentialSolution);
					}
				}
			} while (!incArray(is, limits)&&(solutions.isEmpty()||FIND_ALL));
		}
		System.out.print((System.currentTimeMillis()-startTime)/1000f+" s\n\n");
		if (solutions.isEmpty()) System.out.println("No solutions :(");
		else for (int i = 0;i<solutions.size();i++) System.out.println(solutions.get(i));
	}

	public static boolean incArray(int[] array, int[] limits) {//true if at max, inclusive-exlusive
		array[0]++;
		for (int i = 1;i<=array.length;i++) {
			if (array[i-1]==limits[i-1]) {
				if (i<array.length) {
					array[i-1] = 0;
					array[i]++;
				}
				else return true;
			}
		}
		return false;
	}
}

class SolverThread implements Runnable, SolverData {
	private List<Layout> solutions;
	private List<List<Piece>> orientations;

	public SolverThread(List<List<Piece>> orientations, List<Layout> solutions) {
		this.orientations = orientations;
		this.solutions = solutions;
	}

	@Override
	public void run() {
		int[] is = new int[orientations.size()], limits = new int[orientations.size()];
		for (int i = 0;i<limits.length;i++) limits[i] = orientations.get(i).size();
		do {
			Layout potentialSolution = new Layout(3);
			for (int i = 0;i<orientations.size();i++) {
				if (!potentialSolution.append(orientations.get(i).get(is[i]))) break;
				if (i==orientations.size()-1) {
					System.out.println(Arrays.toString(is));
					solutions.add(potentialSolution);
				}
			}
		} while (!Solver.incArray(is, limits)&&(solutions.isEmpty()||FIND_ALL));
		Solver.doneCount++;
	}
}








