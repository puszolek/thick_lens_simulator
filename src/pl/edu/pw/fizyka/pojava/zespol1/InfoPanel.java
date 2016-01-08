package pl.edu.pw.fizyka.pojava.zespol1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 8128324085216151671L;
	
	public int focal;
	int frontFocal;
	public JLabel [] focalLengthLabel;

	public InfoPanel() {
		
		Color color = new Color(220,220,220);
		this.setBackground(color);
		this.setPreferredSize(new Dimension(120,196));
		
		//parameters
		int [] focal = new int[3];
		focalLengthLabel = new JLabel[3];
		focal[0] = 317;
		
		//labels
		for (int i = 0; i < 3; i++)
		{
			focalLengthLabel[i] = new JLabel("ogniskowa " + (i+1) + ":  " + focal[i] +" mm");
		}
	
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//quote of the day button
		SecretButton secretButton = new SecretButton("Cytat dnia");
		
		/********************************************************************************/
		//adding labels and button
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 1;
		c.ipady = 0;
		c.weightx = 0.15;
		c.weighty = 0.0;
		c.insets = new Insets(0,0,10,0);
		
		this.setLayout(new GridBagLayout());
		c.insets = new Insets(0,15,20,0);
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		
		for (int i = 0; i < 3; i++)
		{
			c.gridy = i;
			this.add(focalLengthLabel[i], c);
		}
		c.gridy = 3;
		this.add(secretButton, c);
	}
	
	//template, cause could be string or int
	<T> void setFocalLength(int i, T x) { 
		focalLengthLabel[i].setText("ogniskowa " + (i+1) + ": " + String.valueOf(x) +" mm");
	}
}
