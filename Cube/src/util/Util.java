package util;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Util {
	public static List<int[]> getPermutations(int length) {
		List<int[]> permutations = new ArrayList<>();
		int[] array = new int[length], c = new int[length];
		for (int i = 0;i<length;i++) {
			array[i] = i;
			c[i] = 0;
		}
		permutations.add(array.clone());
		int i = 0;
		while (i<length) {
			if (c[i]<i) {
				if (i%2==0) array = swap(array, 0, i);
				else array = swap(array, c[i], i);
				permutations.add(array.clone());
				c[i]+=1;
				i = 0;
			}
			else {
				c[i] = 0;
				i+=1;
			}
		}
		return permutations;
	}

	public static int[] swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		return array;
	}

	public static <T> List<T> order(List<T> list, int[] order) {
		List<T> newList = new ArrayList<>();
		for (int i = 0;i<order.length;i++) {
			newList.add(list.get(order[i]));
		}
		return newList;
	}
	
	public static int[][] parseIntArrayFromFile(String path) {
		String in = fileToString(path);
		System.out.println(path+" loaded");
		String[] rawRows = in.split(";");
		String[][] raw = new String[rawRows.length][rawRows[0].length()/2+1];
		for (int r = 0;r<rawRows.length;r++) {
			raw[r] = rawRows[r].split(",");
		}
		int[][] array = new int[raw.length][raw[0].length];
		for (int r = 0;r<raw.length;r++) {
			for (int c = 0;c<raw[0].length;c++) {
				try {
					array[r][c] = raw[r][c].charAt(0);
				}
				catch (Exception e) {
					System.err.println("error at: "+r+","+c);
					array[r][c] = 0;
				}
			}
		}
		return array;
	}

	public static String fileToString(String path) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(path));
			StringBuilder str = new StringBuilder();
			for (String line:lines) {
				if (!line.startsWith("//")) str.append(line);
			}
			return str.toString();
		}
		catch (FileNotFoundException e) {
			System.err.println("Can't find file at: "+path);
			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}







