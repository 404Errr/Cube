package solver;

import data.MainData;
import main.Layout;

public class Piece extends Layout implements MainData, Comparable<Piece> {
	public Piece(int size) {
		super(size);
	}

	public Piece(int[][][] layout) {
		super(layout);
	}
	
	@Override
	public int compareTo(Piece that) {
		if (d()!=SIZE||h()!=SIZE||w()!=SIZE) throw new UnsupportedOperationException("not a 3x3x3");
		int centerThis = 0, centerThat = 0;
		int edgeThis = 0, edgeThat = 0;
		int cornerThis = 0, cornerThat = 0;
		int middleThis = 0, middleThat = 0;
		for (int z = 0;z<SIZE;z++) {
			for (int y = 0;y<SIZE;y++) {
				for (int x = 0;x<SIZE;x++) {
					if (z!=1&&y!=1&&x!=1) {//corner
//						System.out.println("corner");//TODO FIXME
						if (this.get(x, y, z)!=0) cornerThis++;
						if (that.get(x, y, z)!=0) cornerThat++;
					}
					else if (z==1&&y==1&&x==1) {//middle
						if (this.get(x, y, z)!=0) middleThis++;
						if (that.get(x, y, z)!=0) middleThat++;
					}
					else if (z==1&&y==1||y==1&&x==1||z==1&&x==1) {//center
						if (this.get(x, y, z)!=0) centerThis++;
						if (that.get(x, y, z)!=0) centerThat++;
					}
					else {//edge
						if (this.get(x, y, z)!=0) edgeThis++;
						if (that.get(x, y, z)!=0) edgeThat++;
					}
				}
			}
		}
		float totalThis = centerThis*CENTER+edgeThis*EDGE+cornerThis*CORNER+middleThis*MIDDLE;
		float totalThat = centerThat*CENTER+edgeThat*EDGE+cornerThat*CORNER+middleThis*MIDDLE;
		//middle, center, edge, corner
		System.out.println(middleThis+" "+centerThis+" "+edgeThis+" "+cornerThis+"\t\t"+middleThat+" "+centerThat+" "+edgeThat+" "+cornerThat+"\t\t"+totalThis+" "+totalThat);
		if (totalThis<totalThat) return -1;
		if (totalThis>totalThat) return 1;
		return 0;
	}
}
