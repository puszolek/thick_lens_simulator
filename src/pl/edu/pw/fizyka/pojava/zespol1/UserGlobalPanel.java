package pl.edu.pw.fizyka.pojava.zespol1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
public class UserGlobalPanel extends JPanel {

	private static final long serialVersionUID = -1082256343824521412L;
	
	//sliders
	JSlider beamSlider = new JSlider(JSlider.VERTICAL,1,10,1);
	JSlider beamAngleSlider = new JSlider(JSlider.VERTICAL,-45,45,0);
	JSlider beamPositionSlider = new JSlider(JSlider.VERTICAL,-150,150,0); 
	JSlider refractionGlobalSlider = new JSlider(JSlider.VERTICAL,100,300,100);
	
	//labels
	JLabel beamLabel = new JLabel("promienie:");
	JLabel beamAngleLabel = new JLabel("k¹t:     ");
	JLabel beamPositionLabel = new JLabel("po³o¿enie:");
	JLabel refractionGlobalLabel = new JLabel("n oœrodka:");
	
	//text fields
	JTextField beamTextField = new JTextField(3);
	JTextField beamAngleTextField = new JTextField(3);
	JTextField beamPositionTextField = new JTextField(3);
	JTextField refractionGlobalTextField = new JTextField(3);
	
	public UserGlobalPanel() {
		
		Color color = new Color(220,220,220);
		this.setBackground(color);
		
		//sliders
		beamSlider.setPaintTicks(true);
		beamSlider.setMajorTickSpacing(5);
		beamSlider.setMinorTickSpacing(1);
		beamSlider.setBackground(color);
		beamAngleSlider.setPaintTicks(true);
		beamAngleSlider.setMajorTickSpacing(20);
		beamAngleSlider.setMinorTickSpacing(10);
		beamAngleSlider.setBackground(color);
		beamPositionSlider.setPaintTicks(true);
		beamPositionSlider.setMajorTickSpacing(100);
		beamPositionSlider.setMinorTickSpacing(50);
		beamPositionSlider.setBackground(color);
		refractionGlobalSlider.setPaintTicks(true);
		refractionGlobalSlider.setMajorTickSpacing(50);
		refractionGlobalSlider.setMinorTickSpacing(10);
		refractionGlobalSlider.setBackground(color);
		
		//text fields
		beamTextField.setText("0");
		beamAngleTextField.setText("0");
		beamPositionTextField.setText("0");
		refractionGlobalTextField.setText("1.0");
		
		//adding items to panel
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 1;
		c.ipady = 0;
		c.weightx = 0.15;
		c.weighty = 0.0;
		c.insets = new Insets(10,0,10,0);
		
		c.gridx = 0;
		c.gridy = 0;
		this.add(beamLabel, c);
		c.gridy = 1;
		this.add(beamSlider, c);
		c.gridy = 2; 
		this.add(beamTextField, c);
		
		c.gridx = 1;
		c.gridy = 0;
		this.add(beamAngleLabel, c);
		c.gridy = 1;
		this.add(beamAngleSlider, c);
		c.gridy = 2; 
		this.add(beamAngleTextField, c);
		
		c.gridx = 2;
		c.gridy = 0;
		this.add(beamPositionLabel, c);
		c.gridy = 1;
		this.add(beamPositionSlider, c);
		c.gridy = 2; 
		this.add(beamPositionTextField, c);
		
		c.gridx = 3;
		c.gridy = 0;
		this.add(refractionGlobalLabel, c);
		c.gridy = 1;
		this.add(refractionGlobalSlider, c);
		c.gridy = 2; 
		this.add(refractionGlobalTextField, c);
		
		//sliders - parameters 
		beamSlider.setPreferredSize(new Dimension (30,150));
		beamSlider.setMinimumSize(beamSlider.getPreferredSize());
		
		beamAngleSlider.setPreferredSize(new Dimension (30,150));
		beamAngleSlider.setMinimumSize(beamAngleSlider.getPreferredSize());

		beamPositionSlider.setPreferredSize(new Dimension (30,150));
		beamPositionSlider.setMinimumSize(beamPositionSlider.getPreferredSize());
			
		refractionGlobalSlider.setPreferredSize(new Dimension (30,150));
		refractionGlobalSlider.setMinimumSize(refractionGlobalSlider.getPreferredSize());
			
		//text fields listeners
		beamTextField.setPreferredSize(new Dimension (37,20));
		beamTextField.setMinimumSize(beamTextField.getPreferredSize());
		beamTextField.addKeyListener(new KeyAdapter(){
				public void keyReleased(KeyEvent ke) {
				String typed = beamTextField.getText();
				int value = Integer.parseInt(typed);
				beamSlider.setValue(value);
				}
		});
		
		beamAngleTextField.setPreferredSize(new Dimension (37,20));
		beamAngleTextField.setMinimumSize(beamAngleTextField.getPreferredSize());
		beamAngleTextField.addKeyListener(new KeyAdapter(){
				public void keyReleased(KeyEvent ke) {
				String typed = beamAngleTextField.getText();
				int value = Integer.parseInt(typed);
				beamAngleSlider.setValue(value);
				}
		});
		
		beamPositionTextField.setPreferredSize(new Dimension (37,20));
		beamPositionTextField.setMinimumSize(beamPositionTextField.getPreferredSize());
		beamPositionTextField.addKeyListener(new KeyAdapter(){
				public void keyReleased(KeyEvent ke) {
				String typed = beamPositionTextField.getText();
				int value = Integer.parseInt(typed);
				beamPositionSlider.setValue(value);
				}
		});
		
		refractionGlobalTextField.setPreferredSize(new Dimension (37,20));
		refractionGlobalTextField.setMinimumSize(refractionGlobalTextField.getPreferredSize());			
		refractionGlobalTextField.addKeyListener(new KeyAdapter(){
				public void keyReleased(KeyEvent ke) {
				String typed = refractionGlobalTextField.getText();
				float value = Float.parseFloat(typed);
				refractionGlobalSlider.setValue((int)(100*value));
				}
		});
	}

	int [] getData()
	{
		int [] data = new int[4];
		data[0] = beamSlider.getValue();
		data[1] = beamAngleSlider.getValue();
		data[2] = beamPositionSlider.getValue();
		data[3] = refractionGlobalSlider.getValue();
		return data;
	}
	
	void setDefaultValues()
	{
		beamSlider.setValue(1);
		beamAngleSlider.setValue(0);
		refractionGlobalSlider.setValue(100);
		beamPositionSlider.setValue(0);
	}
}
