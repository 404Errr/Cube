package stl;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import main.Layout;

public class Stl {
	public static void generate() {
		List<String> lines = null;
		int size = 1;
		try {
			lines = Files.readAllLines(Paths.get(ROOT+"/"+PATH));
			for (int i = lines.size()-1;i>=0;i--) {
				if (lines.get(i).startsWith("//")) lines.remove(i);
				else if (lines.get(i).contains("-")) size++;
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("Can't find file at: "+ROOT+"/"+PATH);
			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		int[][][] raw = new int[size][size][size];
		System.out.println("size: "+size);
		int z = 0, y = 0;
		for (int i = 0;i<lines.size();i++) {
			String line = lines.get(i);
			if (line.contains("-")) {
				y = 0;
				z++;
				continue;
			}
			for (int x = 0;x<size;x++) {
				System.out.println(x+","+y+","+z+"\t"+line.charAt(x));
				raw[z][y][x] = Integer.parseInt(line.charAt(x)+"");
			}
			y++;
		}
		System.out.println(new Layout(raw));
	}
}
