package pl.edu.pw.fizyka.pojava.zespol1;

import java.awt.geom.Arc2D;

public class Lens {

	Arc2D.Float arc0;
	Arc2D.Float arc1;
	double n;
	int location;
	int posY;
	int thickness;
	int height;
	float radius1;
	float radius2;
	int minRad1;
	int minRad2;
	int maxHght;
	int minThick;
	int minPos;
	int maxPos;
	boolean visible;
	Point[] circleCenters; //array of centers of circles which define lens' surfaces:0 - left, 1 - right
	Point [] vertices; //array of "vertices" of a base rectangle of lens
	double [] arcCenters; //on OX
	
	 
	public Lens() {
		arc0 = new Arc2D.Float();
		arc1 = new Arc2D.Float();
		vertices = new Point[4];	
		arcCenters = new double[2];
		visible = false;
		posY = 185;
		n = 1.5; //base values
		thickness = 100;
		height = 100;
		radius1 = 300;
		radius2 = 300;
		minRad1 = -100;
		minRad2 = -100;
		maxHght = 150;
		minThick = 0;
		minPos = 0;
		maxPos = 1000;
		location = (int) (minPos + maxPos) / 2;
		
		vertices[0] = new Point(location - thickness/2, posY - height);
		vertices[1] = new Point(location - thickness/2, posY + height);
		vertices[2] = new Point(location + thickness/2, posY + height);
		vertices[3] = new Point(location + thickness/2, posY - height);	
		
		circleCenters = new Point[2];
		circleCenters[0] = new Point();
		circleCenters[1] = new Point();
	}
	
	void calculate() //calculating lens' geometry
	{
		vertices[0].setX(location - thickness/2); 
		vertices[0].setY(posY - height);
		vertices[1].setX(location - thickness/2); 
		vertices[1].setY(posY + height);
		vertices[2].setX(location + thickness/2); 
		vertices[2].setY(posY + height);
		vertices[3].setX(location + thickness/2); 
		vertices[3].setY(posY - height);
		
		circleCenters[0].setY(posY);
		circleCenters[1].setY(posY);
		
		if (radius1 >= 0)
			circleCenters[0].setX(vertices[0].getX() + (int)Math.sqrt(radius1*radius1 - height*height));
		else if (radius1 < 0)
			circleCenters[0].setX(vertices[0].getX() - (int)Math.sqrt(radius1*radius1 - height*height));
		
		if (radius2 >= 0)
			circleCenters[1].setX(vertices[3].getX() - (int)Math.sqrt(radius2*radius2 - height*height));
		else if (radius2 < 0)
			circleCenters[1].setX(vertices[3].getX() + (int)Math.sqrt(radius2*radius2 - height*height));
		
		arcCenters[0] = circleCenters[0].getX() - radius1;
		arcCenters[1] = circleCenters[1].getX() + radius2;	
	}
	
	//setting arcs
	void setArcs()
	{
		double tanArg0 = (double)((double)height / ((double)circleCenters[0].getX() - (double)vertices[0].getX()));
		double tanArg1 = (double)((double)height / ((double)circleCenters[1].getX() - (double)vertices[3].getX()));
		
		if (radius1 >= 0)
			arc0.setArcByCenter(
				circleCenters[0].getX(),
				circleCenters[0].getY(),
				radius1,
				180 - Math.toDegrees(Math.atan(tanArg0)),
				Math.toDegrees(2*Math.atan(tanArg0)),
				Arc2D.OPEN
				);
		else if (radius1 < 0)
			arc0.setArcByCenter(
				circleCenters[0].getX(),
				circleCenters[0].getY(),
				-radius1,
				Math.toDegrees(Math.atan(Math.abs(tanArg0))),
				Math.toDegrees(-2*Math.atan(Math.abs(tanArg0))),
				Arc2D.OPEN
				);
		
		if (radius2 < 0)
			arc1.setArcByCenter(
				circleCenters[1].getX(),
				circleCenters[1].getY(),
				-radius2,
				180 - Math.toDegrees(Math.atan(tanArg1)),
				Math.toDegrees(2*Math.atan(tanArg1)),
				Arc2D.OPEN
				);
		else if (radius2 >= 0)
			arc1.setArcByCenter(
				circleCenters[1].getX(),
				circleCenters[1].getY(),
				radius2,
				Math.toDegrees(Math.atan(Math.abs(tanArg1))),
				Math.toDegrees(-2*Math.atan(Math.abs(tanArg1))),
				Arc2D.OPEN
				);
	}
	
	void setDefault() 
	{
		posY = 185;
		n = 1.5;
		location = 500;
		thickness = 50;
		height = 100;
		radius1 = 300;
		radius2 = 300;
	}
	
	double getRefraction() { return n; }
	float getHeight() { return height; }
	float getRadius1() { return radius1; }
	float getRadius2() { return radius2; }
	float getThickness() { return thickness; }
	float getLocation() { return location; }
	int getMaxHght() { return maxHght; }
	int getMinThick() { return minThick; }
	int getPosY() { return posY; }
	
	void setVisible(boolean arg) { visible = arg; }
	boolean isVisible() { return visible; }
	
	//returns minimum radius
	int getMinRad(int option) {
		if (option == 1)
			return minRad1;
		else
			return minRad2;
	}
	
	int getPosLimit(int option) {
		if (option == 1)
			return minPos;
		else
			return maxPos;
	}
	
	Point getCircleCenter(int x){
		if (x == 1)
		{
			return circleCenters[0];
		}
		else if (x == 2)
		{
			return circleCenters[1];
		}
		else
		{
			return null;
		}
	}
	
	void setMinThick()
	{
		int tmp = thickness;
		for (int i = 0; i <= 200; i++)
		{
			thickness = i;
			calculate();
			if(compare())
			{
				minThick = i;
				thickness = tmp;
				calculate();
				break;
			}
		}
	}
	
	//1 - left, else(2) - right
	void setPosLimit(int option, Lens lens) //lens - lens which defines maximum position of current lens
	{
		int tmp = location;
		if (lens == null) //null = no lens
		{
			if (option == 1)
			{
				for (int i = 0; i <= 1000; i++)
				{
					location = i;
					calculate();
					if (arcCenters[0] > 0 && vertices[0].getX() > 0)
					{
						minPos = i;
						if (tmp > minPos)
							location = tmp;
						else
							location = minPos;
						calculate();
						break;
					}
				}
			}
			else
			{
				maxPos = 1000;
				if (tmp < maxPos)
					location = tmp;
				else
					location = maxPos;
			}
				
		}
		else if (lens.isVisible()) //if lens is visible on screen 
		{
			if (option == 1)
			{
				for (int i = (int) lens.getLocation(); i <= 1000; i++)
				{
					location = i;
					calculate();
					if (arcCenters[0] > lens.arcCenters[1] && vertices[0].getX() > lens.arcCenters[1] && 
							arcCenters[0] > lens.vertices[3].getX() && vertices[0].getX() > lens.vertices[3].getX())
					{
						minPos = i;
						if (tmp > minPos)
							location = tmp;
						else
							location = minPos;
						calculate();
						break;
					}
				}
			}
			else 
			{
				for (int i = (int) lens.getLocation(); i >= 0; i--)
				{
					location = i;
					calculate();
					if (arcCenters[1] < lens.arcCenters[0] && vertices[3].getX() < lens.arcCenters[0] && 
							arcCenters[1] < lens.vertices[0].getX() && vertices[3].getX() < lens.vertices[0].getX())
					{
						maxPos = i;
						if (tmp < maxPos)
							location = tmp;
						else
							location = maxPos;
						calculate();
						break;
					}
				}
			}
		}
		else 
		{
			if (option == 1)
			{
				for (int i = 0; i <= 1000; i++)
				{
					location = i;
					calculate();
					if (arcCenters[0] > 0 && vertices[0].getX() > 0)
					{
						minPos = i;
						location = tmp;
						calculate();
						break;
					}
				}
			}
			else
				maxPos = 1000;
		}
	}
	
	
	void setMaxHght()
	{
		float tmpR1 = radius1;
		float tmpR2 = radius2;
		int tmp = height;
		for (int i = 150; i >= 50; i--)
		{
			radius1 = tmpR1*i/tmp;
			radius2 = tmpR2*i/tmp;
			height = i;
			calculate();
			if(compare())
			{
				maxHght = i;
				radius1 = tmpR1;
				radius2 = tmpR2;
				height = tmp;
				calculate();
				break;
			}
			
		}
	}
	
	void setMinRad(int option)
	{
		if (option == 1)
		{
			float tmp = radius1;
			for (int i = -100; i <= 100; i++)
			{
				radius1 = this.height/((float)i/100);
				calculate();
				if(compare())
				{
					minRad1 = i;
					radius1 = tmp;
					calculate();
					break;
				}
			}
		}
		else if (option == 2)
		{
			float tmp = radius2;
			for (int i = -100; i <= 100; i++)
			{
				radius2 = this.height/((float)i/100);
				calculate();
				if(compare())
				{
					minRad2 = i;
					radius2 = tmp;
					calculate();
					break;
				}
			}
		}
	}
	
	boolean compare()
	{
			if(arcCenters[0] >= (arcCenters[1] - 10.0) ) //10 - gap 
				return false;
			else
				return true;
	}
	
	void setRefraction(double arg) { n = arg; }
	void setHeight(int arg) { height = arg; }
	void setRadius1(float arg) { radius1 = arg; }
	void setRadius2(float arg) { radius2 = arg; }
	void setThickness(int arg) { thickness = arg; }
	void setLocation(int arg) { location = arg; }

	Arc2D.Float getArc0() {return arc0;}
	Arc2D.Float getArc1() {return arc1;}
	
}
