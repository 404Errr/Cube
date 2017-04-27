//package builder;
//
//import data.BuilderData;
//
//public class Cubie implements BuilderData {
//	private Cubie next;
//	private final int x, y, z, color;
//
//	public Cubie(int x, int y, int z, int color) {
//		this.x = x;
//		this.y = y;
//		this.z = z;
//		this.color = color;
//	}
//
//	public Cubie getNext() {
//		return next;
//	}
//
//	public void setNext(Cubie next) {
//		this.next = next;
//	}
//
//	public int getColor() {
//		return color;
//	}
////
////	public boolean canBeNext(Cubie next) {
////		if (this.next==null) {
////			if (this.z>next.z) return F;
////			if (this.y>next.y) return D;
////			if (this.x>next.x) return R;
////			if (this.z<next.z) return B;
////			if (this.y<next.y) return U;
////			if (this.x<next.x) return L;
////		}
////	}
//
//	public boolean makesCollision(Cubie cubie) {
//		if (cubie.getNext()==null) return true;
//		if (cubie.getNext()==this||next==cubie) return false;
//		int thisDir = this.getDirection(), otherDir = cubie.getDirection();
//		if (thisDir==F&&otherDir==f) return false;
//		if (thisDir==B&&otherDir==b) return false;
//		if (thisDir==L&&otherDir==l) return false;
//		if (thisDir==R&&otherDir==r) return false;
//		if (thisDir==U&&otherDir==u) return false;
//		if (thisDir==D&&otherDir==d) return false;
//		return true;
//	}
//
//	public int getDirection() {
//		if (color==0) return EMPTY;
////		if (new Boolean(true)) return '9';
//		if (color!=0&&next!=null) {
//			if (this.z<next.z) return F;
//			if (this.y<next.y) return D;
//			if (this.x<next.x) return R;
//			if (this.z>next.z) return B;
//			if (this.y>next.y) return U;
//			if (this.x>next.x) return L;
//		}
//		return '-';
//	}
//
//	public boolean canBeNext(Cubie cubie) {
//		if (next==null||next.getNext()==null) return true;
//		if (this.z<next.z&&next.z>next.getNext().z) return false;
//		if (this.y<next.y&&next.y>next.getNext().y) return false;
//		if (this.x<next.x&&next.x>next.getNext().x) return false;
//		if (this.z>next.z&&next.z<next.getNext().z) return false;
//		if (this.y>next.y&&next.y<next.getNext().y) return false;
//		if (this.x>next.x&&next.x<next.getNext().x) return false;
//		return true;
//	}
//
//}
