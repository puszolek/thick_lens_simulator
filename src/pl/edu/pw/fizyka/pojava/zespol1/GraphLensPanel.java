package pl.edu.pw.fizyka.pojava.zespol1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.util.Vector;

import javax.swing.JPanel;

public class GraphLensPanel extends JPanel {

	private static final long serialVersionUID = 6530566532921502280L;
	UserLensPanel [] lensPanel;
	UserGlobalPanel globalPanel;
	int [] data;
	int beams, startY;
	double refraction, startAngle;
	Vector< Vector<Point> > vecBeams; //vector of vectors of points
	Boolean [] beamErrors; //array[10], if false - draw beam, else - do not
	Vector<Line> startBeams; //vector of starting lines
	
	void setStartAngle (int arg) { startAngle = arg; }
	void setStartY (int arg) { startY = arg; }
	void setN (double arg) { refraction = arg; }
	
	//setting how many beams on screen
	void setBeams (int arg) 
	{ 
		beams = arg; 
		
		//deleting elements from vector of vectors
		for (int i = 0; i < vecBeams.size(); i++)
		{
			vecBeams.elementAt(i).removeAllElements();
		}
		vecBeams.removeAllElements();
		
		//adding beams represented as vectors of points = arg
		for (int i = 0; i < beams; i++)
		{
			vecBeams.add(new Vector<Point>(0));
		}
		calculate();
	}
	
	public GraphLensPanel(UserLensPanel [] lP, UserGlobalPanel gP) {
		this.setBackground(Color.WHITE);
		lensPanel = lP;
		globalPanel = gP;
		data = new int[4];
		beams = 1;
		startAngle = 0.0;
		startY = 0;
		refraction = 1.0;
		startBeams = new Vector<Line>(0);
		
		for (int i = 0; i < 10; i++)
		{
			startBeams.add(new Line());
		}
		startBeams.elementAt(0).setY(startY);
		startBeams.elementAt(0).setAngle(startAngle);
		
		vecBeams = new Vector< Vector<Point> >(1);
		
		beamErrors = new Boolean[10];
		beamErrors[0] = false;
		
		lP[0].lens.calculate();
		lP[0].lens.setArcs();
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		calculate(); 
		
		for (int ii = 0; ii < UserInterface.lensCounter; ii++)
		{
			Color fillColor = new Color(255 - lensPanel[ii].refractionLensSlider.getValue() / 2, 
										255 - lensPanel[ii].refractionLensSlider.getValue() / 2, 
										255);
			Stroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT,0);
			g2.setColor(fillColor);
			
			//base rectangle of a lens
			Rectangle rect = new Rectangle((int)((lensPanel[ii].lens.getLocation()-lensPanel[ii].lens.getThickness()/2)),
					(int)(lensPanel[ii].lens.getPosY()-lensPanel[ii].lens.getHeight()), 
					(int)lensPanel[ii].lens.getThickness(), (int)(2*lensPanel[ii].lens.getHeight()));
			
			Area lensArea = new Area(rect);
			int x = (int)(lensPanel[ii].lens.getLocation()-lensPanel[ii].lens.getThickness()/2);
			int y1 = (int)(lensPanel[ii].lens.getPosY()-lensPanel[ii].lens.getHeight());
			int y2 = (int)(lensPanel[ii].lens.getPosY()+lensPanel[ii].lens.getHeight());
			
			if (lensPanel[ii].lens.getRadius1() >= 0 && lensPanel[ii].lens.getRadius2() >= 0)
			{
				g2.setStroke(stroke);
				lensArea.add(new Area(lensPanel[ii].lens.getArc0()));
				lensArea.add(new Area(lensPanel[ii].lens.getArc1()));
				g2.draw(lensArea);
				g2.setColor(fillColor);
				g2.fill(lensArea);
			}
			else if(lensPanel[ii].lens.getRadius1() <= 0 && lensPanel[ii].lens.getRadius2() >= 0)
			{
				g2.setStroke(stroke);
				lensArea.add(new Area(lensPanel[ii].lens.getArc1()));
				lensArea.subtract(new Area(lensPanel[ii].lens.getArc0()));
				g2.draw(lensArea);
				g2.setColor(fillColor);
				g2.fill(lensArea);
				
				g2.setColor(this.getBackground());
				g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND,0));
				g2.draw(new Line2D.Double(x+1, y1, x+1, y2));
			}
			else if(lensPanel[ii].lens.getRadius1() >= 0 && lensPanel[ii].lens.getRadius2() <= 0)
			{
				g2.setStroke(stroke);
				lensArea.add(new Area(lensPanel[ii].lens.getArc0()));
				lensArea.subtract(new Area(lensPanel[ii].lens.getArc1()));
				g2.draw(lensArea);
				g2.setColor(fillColor);
				g2.fill(lensArea);
				
				g2.setColor(this.getBackground());
				g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND,0));
				g2.draw(new Line2D.Double(x + lensPanel[ii].lens.getThickness(), y1, x + lensPanel[ii].lens.getThickness(), y2));
			}
			else if(lensPanel[ii].lens.getRadius1() <= 0 && lensPanel[ii].lens.getRadius2() <= 0)
			{
				g2.setStroke(stroke);
				lensArea.subtract(new Area(lensPanel[ii].lens.getArc0()));
				lensArea.subtract(new Area(lensPanel[ii].lens.getArc1()));
				g2.draw(lensArea);
				g2.setColor(fillColor);
				g2.fill(lensArea);
				
				g2.setColor(this.getBackground());
				g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND,0));
				g2.draw(new Line2D.Double(x+1, y1, x+1, y2));
				g2.draw(new Line2D.Double(x + (int)lensPanel[ii].lens.getThickness(), y1, x + lensPanel[ii].lens.getThickness(), y2));
			}
		}
		
		//axis
		float dash[] = {10.0f};
		Stroke strokeAxis = new BasicStroke(1.0f,
		       BasicStroke.CAP_BUTT,
		       BasicStroke.JOIN_MITER,
		       10.0f, dash, 0.0f);
		g2.setStroke(strokeAxis);
		g2.setColor(Color.CYAN);
		g2.drawLine(0, lensPanel[0].lens.posY, 1500, lensPanel[0].lens.posY);
		
		//drawing beams
		g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,0));
		Color beamColor = Color.RED;
		g2.setColor(beamColor);
		data = globalPanel.getData(); //getting all the values from globalPanel: 0-beamSlider,1-refraction,2-angle,3-position
		
		//here beams are being drawn
		for (int j = 0; j < vecBeams.size(); j++)
		{
			if (beamErrors[j] == false)
			{
				for (int i = 0; i < vecBeams.elementAt(j).size() - 1; i++)
				{
					g2.drawLine(vecBeams.elementAt(j).elementAt(i).getX(), vecBeams.elementAt(j).elementAt(i).getY(), 
							vecBeams.elementAt(j).elementAt(i + 1).getX(), vecBeams.elementAt(j).elementAt(i + 1).getY());
				}
			}
		}
	}
	
	void setStartingPoints(int size)
	{
		if (size % 2 == 1)
		{
			int centr = ((size - 1) / 2);
			for (int i = 0; i <= (size - 1) / 2; i++)
			{
				startBeams.elementAt(centr - i).setY(startY - i*20);
				startBeams.elementAt(centr + i).setY(startY + i*20);
			}
		}
		else
		{
			int up = (size - 2) / 2;
			int down = size / 2;
			for (int i = 0; i <= size - down - 1; i++)
			{
				startBeams.elementAt(up - i).setY(startY - 10 - i*20);
				startBeams.elementAt(down + i).setY(startY + 10 + i*20);
			}
		}
	}
	
	//calculates beams
	void calculate()
	{	
		setStartingPoints(beams);
		
		for (int i = 0; i < vecBeams.size(); i++)
		{
			beamErrors[i] = false;
			Lens lens = lensPanel[0].lens;
			
			//clear
			if (vecBeams.elementAt(i).size() > 0)
			{
				vecBeams.elementAt(i).removeAllElements();
			}
			startBeams.elementAt(i).setAngle(startAngle); //angle for current beam
			
			//adding to vector starting point and first intersection
			vecBeams.elementAt(i).add(startBeams.elementAt(i).getStartPoint());
			vecBeams.elementAt(i).add(startBeams.elementAt(i).intersect(lensPanel[0].lens, 0));
			if (vecBeams.elementAt(i).lastElement() == null) //checking if it's null
			{
				beamErrors[i] = true;
				continue;
			}
			
			Line next = refract(startBeams.elementAt(i), lensPanel[0].lens, 
					vecBeams.elementAt(i).elementAt(1), refraction, 0); //next line and refraction, refract returns line, 0 - left
			if (next == null)
			{
				beamErrors[i] = true;
				continue;
			}
			
			vecBeams.elementAt(i).add(next.intersect(lensPanel[0].lens, 1));
			if (vecBeams.elementAt(i).lastElement() == null)
			{
				beamErrors[i] = true;
				continue;
			}
			
			next = refract(next, lensPanel[0].lens, vecBeams.elementAt(i).elementAt(2), refraction, 1); //1 - right surface
			if (next == null)
			{
				beamErrors[i] = true;
				continue;
			}
			
			//check if the last "next" does not intersect lens
			if ((Math.tan(Math.toRadians(-next.getAngle()))*lens.vertices[3].getX() + 185 - next.getY()) >= (double)(185 + lens.getHeight()) ||
					(Math.tan(Math.toRadians(-next.getAngle()))*lens.vertices[3].getX() + 185 - next.getY()) <= (double)(185 - lens.getHeight()))
			{
				beamErrors[i] = true;
				continue;
			}
			
			if (UserInterface.lensCounter == 2 || UserInterface.lensCounter == 3)
			{
				lens = lensPanel[1].lens; //set on another lens and continue
				vecBeams.elementAt(i).add(next.intersect(lensPanel[1].lens, 0));
				if (vecBeams.elementAt(i).lastElement() == null)
				{
					beamErrors[i] = true;
					continue;
				}
				
				next = refract(next, lensPanel[1].lens, vecBeams.elementAt(i).elementAt(3), refraction, 0);
				if (next == null)
				{
					beamErrors[i] = true;
					continue;
				}
				vecBeams.elementAt(i).add(next.intersect(lensPanel[1].lens, 1));
				if (vecBeams.elementAt(i).lastElement() == null)
				{
					beamErrors[i] = true;
					continue;
				}
				
				next = refract(next, lensPanel[1].lens, vecBeams.elementAt(i).elementAt(4), refraction, 1);
				if (next == null)
				{
					beamErrors[i] = true;
					continue;
				}
				
				if ((Math.tan(Math.toRadians(-next.getAngle()))*lens.vertices[3].getX() + 185 - next.getY()) >= (double)(185 + lens.getHeight()) ||
						(Math.tan(Math.toRadians(-next.getAngle()))*lens.vertices[3].getX() + 185 - next.getY()) <= (double)(185 - lens.getHeight()))
				{
					beamErrors[i] = true;
					continue;
				}
			}
			if (UserInterface.lensCounter == 3)
			{
				lens = lensPanel[2].lens; 
				vecBeams.elementAt(i).add(next.intersect(lensPanel[2].lens, 0));
				if (vecBeams.elementAt(i).lastElement() == null)
				{
					beamErrors[i] = true;
					continue;
				}
				
				next = refract(next, lensPanel[2].lens, vecBeams.elementAt(i).elementAt(5), refraction, 0);
				if (next == null)
				{
					beamErrors[i] = true;
					continue;
				}
				vecBeams.elementAt(i).add(next.intersect(lensPanel[2].lens, 1));
				if (vecBeams.elementAt(i).lastElement() == null)
				{
					beamErrors[i] = true;
					continue;
				}
				
				next = refract(next, lensPanel[2].lens, vecBeams.elementAt(i).elementAt(6), refraction, 1);
				if (next == null)
				{
					beamErrors[i] = true;
					continue;
				}
			}
			
			//checks if the last "next" does not intersect lens
			if ((Math.tan(Math.toRadians(-next.getAngle()))*lens.vertices[3].getX() + 185 - next.getY()) >= (double)(185 + lens.getHeight()) ||
					(Math.tan(Math.toRadians(-next.getAngle()))*lens.vertices[3].getX() + 185 - next.getY()) <= (double)(185 - lens.getHeight()))
			{
				beamErrors[i] = true;
				continue;
			}
			else
			{
				int x = 1500;		
				vecBeams.elementAt(i).add(new Point(x, (int)(Math.tan(Math.toRadians(-next.getAngle()))*x + 185 - next.getY())));
			}	
		}
	}
	
	Line refract(Line input, Lens lens, Point p, double n, int variant) //variant, 0 - left, 1 - right
	{
		Line output = new Line(0, 0);
		int circleX = lens.getCircleCenter(variant+1).getX();
		int circleY = lens.getCircleCenter(variant+1).getY();
		Line normal = new Line(0, 0);
		double alpha = 0;
		double beta = 0;
		if (variant == 0)
		{
			if ((p.getX()-circleX) == 0)
				return null;
			normal.setY((circleY - circleX*((p.getY()-circleY))/(p.getX()-circleX)));
			normal.setAngle(-Math.toDegrees(Math.atan((double)(p.getY()-circleY)/(p.getX()-circleX))));
				
			if ((normal.getAngle() > 0 && input.getAngle() > 0) || (normal.getAngle() < 0 && input.getAngle() < 0))
			{
				alpha = Math.abs(input.getAngle() - normal.getAngle());
				if (Double.isNaN(alpha) || alpha > 90.0)
					return null;
				beta = Math.toDegrees(Math.asin((double)n*Math.sin(Math.toRadians(alpha)) / (double)lens.getRefraction()));  //Snellius
				if (Double.isNaN(beta) || beta < 0.0)
					return null;
				if (input.getAngle() > normal.getAngle())
				{
					output.setAngle(normal.getAngle() + beta);
				}
				else
				{
					output.setAngle(normal.getAngle() - beta);
				}
			}
			else
			{
				alpha = Math.abs(-input.getAngle() + normal.getAngle());
				if (Double.isNaN(alpha) || alpha > 90.0)
					return null;
				beta = Math.toDegrees(Math.asin((double)n*Math.sin(Math.toRadians(alpha)) / (double)lens.getRefraction()));
				if (Double.isNaN(beta) || beta < 0.0)
					return null;
				if (input.getAngle() > normal.getAngle())
				{
					output.setAngle(normal.getAngle() + beta);
				}
				else
				{
					output.setAngle(normal.getAngle() - beta);
				}
			}
			output.setY((int)(circleY - p.getY() - p.getX()*Math.tan(Math.toRadians(output.getAngle()))));
		}
		else //variant 2.
		{
			if ((p.getX()-circleX) == 0)
				return null;
			normal.setY((circleY - circleX*((p.getY()-circleY))/(p.getX()-circleX)));
			normal.setAngle(-Math.toDegrees(Math.atan((double)(p.getY()-circleY)/(p.getX()-circleX))));
			
			if ((normal.getAngle() > 0 && input.getAngle() > 0) || (normal.getAngle() < 0 && input.getAngle() < 0))
			{
				alpha = Math.abs(input.getAngle() - normal.getAngle());
				if (Double.isNaN(alpha) || alpha > 90.0)
					return null;
				beta = Math.toDegrees(Math.asin((double)lens.getRefraction()*Math.sin(Math.toRadians(alpha)) / (double)n));
				if (Double.isNaN(beta) || beta < 0.0)
					return null;
				if (input.getAngle() > normal.getAngle())
				{
					output.setAngle(normal.getAngle() + beta);
				}
				else
				{
					output.setAngle(normal.getAngle() - beta);
				}
			}
			else
			{
				alpha = Math.abs(-input.getAngle() + normal.getAngle());
				if (Double.isNaN(alpha) || alpha > 90.0)
					return null;
				beta = Math.toDegrees(Math.asin(lens.getRefraction()*(double)Math.sin(Math.toRadians(alpha)) / (double)n));
				if (Double.isNaN(beta) || beta < 0.0)
					return null;
				if (input.getAngle() > normal.getAngle())
				{
					output.setAngle(normal.getAngle() + beta);
				}
				else
				{
					output.setAngle(normal.getAngle() - beta);
				}
			}
			output.setY((int)(circleY - p.getY() - p.getX()*Math.tan(Math.toRadians(output.getAngle()))));
		}
		return output;
	}
}
