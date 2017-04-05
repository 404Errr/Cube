package main;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder str = new StringBuilder();
		while (true) {
			String input = scan.nextLine().toUpperCase();
			if (input.length()>0&&!Character.isAlphabetic(input.charAt(0))) break;
			str.append("\'"+input.charAt(0)+"\', ");
		}
		scan.close();
		System.out.println("{"+str.toString().substring(0, str.length()-2)+"}");
	}
}
