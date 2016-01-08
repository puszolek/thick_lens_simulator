package pl.edu.pw.fizyka.pojava.zespol1;

public class Line {

	int posY; //b in y=ax+b, OX = 185
	double angle;
	
	public Line()
	{
		posY = 0;
		angle = 0;
	}
	
	public Line(int y, double ang) 
	{
		posY = y;
		angle = ang;
	}
	
	void setY(int arg) { posY = arg; }
	void setAngle(double arg) { angle = arg; }
	
	int getY() { return posY; }
	double getAngle() { return angle; }
	
	Point getStartPoint() //starting point of a line
	{
		Point p = new Point(0, 185 - posY);
		return p;
	}
	
	Point intersect(Lens lens, int variant) //0 - left, 1 - right lens' surface
	{
		Point point = new Point();
		double tangent =  Math.tan(Math.toRadians(-angle)); //warning!!! minuz!!!
		double x = 0;

		if (variant == 0)
		{
			int circleCenter = lens.getCircleCenter(1).getX();
			if (lens.getRadius1() > 0)
			{
				//if intersection point of a line and a circle exists
				if ((double)((double)Math.abs(Math.tan(Math.toRadians(-angle))*(double)lens.getCircleCenter(1).getX() -
						(double)lens.getCircleCenter(1).getY() + (double)(185 - posY)) /
						(double)(Math.sqrt(Math.tan(Math.toRadians(-angle))*Math.tan(Math.toRadians(-angle)) + 1.0))) >= lens.getRadius1())
					return null; //null - do not draw
				
				x = ((circleCenter + posY*tangent - 
						Math.sqrt(2*posY*tangent*circleCenter - circleCenter*circleCenter*tangent*tangent +
								lens.getRadius1()*lens.getRadius1()*tangent*tangent -
									posY*posY + lens.getRadius1()*lens.getRadius1())) / (tangent*tangent + 1)); 
				
				if (x > (double)lens.vertices[0].getX() || x < (double)lens.vertices[0].getX() - (double)lens.getHeight())
					return null; //if the intersection point is not on a visible part of a circle
			}
			else
			{
				if ((double)((double)Math.abs(Math.tan(Math.toRadians(-angle))*(double)lens.getCircleCenter(1).getX() -
						(double)lens.getCircleCenter(1).getY() + (double)(185 - posY)) /
						(double)(Math.sqrt(Math.tan(Math.toRadians(-angle))*Math.tan(Math.toRadians(-angle)) + 1.0))) <= lens.getRadius1())
					return null;
				
				x = ((circleCenter + posY*tangent + 
						Math.sqrt(2*posY*tangent*circleCenter - circleCenter*circleCenter*tangent*tangent +
								lens.getRadius1()*lens.getRadius1()*tangent*tangent -
									posY*posY + lens.getRadius1()*lens.getRadius1())) / (tangent*tangent + 1)); 
				
				if (x < (double)lens.vertices[0].getX() || x > (double)lens.vertices[0].getX() + (double)lens.getHeight())
					return null;
			}
			point.setX((int)x);
		}
		else if (variant == 1)
		{
			
			int circleCenter = lens.getCircleCenter(2).getX();
			if (lens.getRadius2() > 0)
			{
				if ((double)((double)Math.abs(Math.tan(Math.toRadians(-angle))*(double)lens.getCircleCenter(2).getX() -
						(double)lens.getCircleCenter(2).getY() + (double)(185 - posY))  /
						(double)(Math.sqrt(Math.tan(Math.toRadians(-angle))*Math.tan(Math.toRadians(-angle)) + 1.0))) >= lens.getRadius2())
					return null;
				
				x = ((circleCenter + posY*tangent +
						Math.sqrt(2*posY*tangent*circleCenter - circleCenter*circleCenter*tangent*tangent +
								lens.getRadius2()*lens.getRadius2()*tangent*tangent -
									posY*posY + lens.getRadius2()*lens.getRadius2())) / (tangent*tangent + 1)); 
				
				
				if (x < (double)lens.vertices[3].getX() || x > (double)lens.vertices[3].getX() + (double)lens.getHeight())
					return null;
			}
			else
			{
				if ((double)((double)Math.abs(Math.tan(Math.toRadians(-angle))*(double)lens.getCircleCenter(2).getX() -
						(double)lens.getCircleCenter(2).getY() + (double)(185 - posY))  /
						(double)(Math.sqrt(Math.tan(Math.toRadians(-angle))*Math.tan(Math.toRadians(-angle)) + 1.0))) <= lens.getRadius2())
					return null;
				
				x = ((circleCenter + posY*tangent - 
						Math.sqrt(2*posY*tangent*circleCenter - circleCenter*circleCenter*tangent*tangent +
								lens.getRadius2()*lens.getRadius2()*tangent*tangent -
									posY*posY + lens.getRadius2()*lens.getRadius2())) / (tangent*tangent + 1)); 
				
				if (x > (double)lens.vertices[3].getX() || x < (double)lens.vertices[3].getX() - (double)lens.getHeight())
					return null;
			}
			point.setX((int)x);
		}
		else 
			point.setX((int)x);
		
		//y = ax+b
		double y = (tangent*x + 185 - posY);
		point.setY((int)y);
		
		if ((y < (double)(185 - lens.getHeight())) || (y > (double)(185 + lens.getHeight())) || Double.isNaN(y))
			return null; //again check
		else
			return point;
	}

}

