package pl.edu.pw.fizyka.pojava.zespol1;

public class Point {

	int x;
	int y;
	
	public Point(int arg1, int arg2) {
	x = arg1;	
	y = arg2;	
	}
	
	public Point() {
		x = 0;
		y = 0;
	}

	void setX(int arg) { x = arg; }
	void setY(int arg) { y = arg; }
	
	int getX() { return x; }
	int getY() { return y; }
}
