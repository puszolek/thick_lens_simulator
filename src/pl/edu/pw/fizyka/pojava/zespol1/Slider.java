package pl.edu.pw.fizyka.pojava.zespol1;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JSlider;

public class Slider extends JSlider {

	private static final long serialVersionUID = 8749906690822193528L;
	int defaultValue;
	int backup;
	
	public Slider(int orientation, int min, int max, int value, int major, int minor, int dv) {
		super(orientation, min, max, value);
		defaultValue = dv;
		backup = value;

		Color color = new Color(220,220,220);
		this.setBackground(color);
		
		this.setPaintTicks(true);
		this.setMajorTickSpacing(major);
		this.setMinorTickSpacing(minor);
		this.setBackground(color);
		
		this.setPreferredSize(new Dimension (30,150));
		this.setMinimumSize(this.getPreferredSize());
	}

	int getDefaultValue()
	{
		return defaultValue;
	}
	
	int getBackup() { return backup; }
	void setBackup(int arg) { backup = arg; }
}

