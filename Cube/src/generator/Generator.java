package generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Generator implements GeneratorData {
	private static Cube cube;
	private static Pointer currentPointer;

	public static void main(String[] args) {
		generate();
		System.out.println(cube);
		checkDims();
	}

	private static void generate() {
		cube = new Cube();
		currentPointer = new Pointer(0, 0, 0);
		int color = 1;
		int cubieCount = 0;
		while (!cube.full()) {
			boolean couldMove = false;
			Stack<int[]> moves = new Stack<>();
			moves.addAll(Arrays.asList(new int[] {1, 0, 0}, new int[] {0, 1, 0}, new int[] {0, 0, 1}, new int[] {-1, 0, 0}, new int[] {0, -1, 0}, new int[] {0, 0, -1}));
			Collections.shuffle(moves);
			while (true) {
				if (moves.isEmpty()) break;
				int[] move = moves.pop();
				Pointer tempPointer = currentPointer.getOffset(move[0], move[1], move[2]);
				if (cube.inBounds(tempPointer)&&((cube.get(tempPointer)==color&&new Random().nextInt(3)==0)||!cube.occupied(tempPointer))) {
					currentPointer = tempPointer;
					couldMove = true;
					break;
				}
			}
			if (!couldMove||color>PIECE_COUNT) {
				generate();
				return;
			}
			if (cube.get(currentPointer)!=color) {
				cubieCount++;
				cube.add(currentPointer, color);
			}
			if (cubieCount>=new Random().nextInt(3)+4) {
				color++;
				cubieCount = 0;
			}
//			System.out.println(cube+"\n"+currentPointer);
		}
	}

	hey look over here!!!
	private static void checkDims() {//TODO
		List<List<Integer>> xAxis = new ArrayList<>(), yAxis = new ArrayList<>(), zAxis = new ArrayList<>();
		for (int i = 0;i<SIZE;i++) {
			xAxis.add(new ArrayList<>());
			yAxis.add(new ArrayList<>());
			zAxis.add(new ArrayList<>());
		}
		for (int z = 0;z<SIZE;z++) {
			for (int y = 0;y<SIZE;y++) {
				for (int x = 0;x<SIZE;x++) {
					int cubie = cube.get(x, y, z);
					if (!xAxis.get(x).contains(cubie)) xAxis.get(x).add(cubie);
					if (!yAxis.get(y).contains(cubie)) yAxis.get(y).add(cubie);
					if (!zAxis.get(z).contains(cubie)) zAxis.get(z).add(cubie);
				}
			}
		}
		for (int i = 0;i<xAxis.size();i++) System.out.println(xAxis.get(i));
		for (int i = 0;i<yAxis.size();i++) System.out.println(yAxis.get(i));
		for (int i = 0;i<zAxis.size();i++) System.out.println(zAxis.get(i));
	}
}

