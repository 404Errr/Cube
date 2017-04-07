package util;

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
}
