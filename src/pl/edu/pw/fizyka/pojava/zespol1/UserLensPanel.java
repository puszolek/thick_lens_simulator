package pl.edu.pw.fizyka.pojava.zespol1;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class UserLensPanel extends JPanel {

	private static final long serialVersionUID = -5185891390850955195L;
	//sliders: position, min, max, value, major, minor, default
	Slider refractionLensSlider = new Slider(JSlider.VERTICAL,100,300,150,50,10,150);
	Slider locationLensSlider = new Slider(JSlider.VERTICAL,0,1000,500,500,50,500);
	Slider thicknessLensSlider = new Slider(JSlider.VERTICAL,0,200,100,50,10,100);
	Slider heightLensSlider = new Slider(JSlider.VERTICAL,50,150,100,25,5,100);
	Slider radius1LensSlider = new Slider(JSlider.VERTICAL,-100,100,30,50,10,30);
	Slider radius2LensSlider = new Slider(JSlider.VERTICAL,-100,100,30,50,10,30);
	Lens lens;
	JTextField refractionTextField;
	JTextField locationTextField;
	JTextField thicknessTextField;
	JTextField heightTextField;
	JTextField radius1TextField;
	JTextField radius2TextField;

	public UserLensPanel() {
		
		Color color = new Color(220,220,220);
		this.setBackground(color);
		
		lens = new Lens();
		
		/********************************************************************************/
		//labels
		JLabel refractionLensLabel = new JLabel("n soczewki:");
		JLabel radius1LensLabel = new JLabel("krzywizna 1:");
		JLabel radius2LensLabel = new JLabel("krzywizna 2:");
		JLabel thicknessLensLabel = new JLabel("gruboœæ:");
		JLabel heightLensLabel = new JLabel("wysokoœæ:");
		JLabel locationLensLabel = new JLabel("po³o¿enie:");
				
		//text fields
		refractionTextField = new JTextField(3);
		refractionTextField.setText(String.valueOf((float)refractionLensSlider.getValue()/100));
		locationTextField = new JTextField(3);
		locationTextField.setText(String.valueOf(locationLensSlider.getValue()));
		thicknessTextField = new JTextField(3);
		thicknessTextField.setText(String.valueOf(thicknessLensSlider.getValue()));
		heightTextField = new JTextField(3);
		heightTextField.setText(String.valueOf(heightLensSlider.getValue()));
		radius1TextField = new JTextField(3);
		radius1TextField.setText(String.valueOf((float)radius1LensSlider.getValue()/100));
		radius2TextField = new JTextField(3);
		radius2TextField.setText(String.valueOf((float)radius2LensSlider.getValue()/100));
		
		/*********************************************************************************/		
		refractionTextField.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent ke) {
				String typed = refractionTextField.getText();
				float value = Float.parseFloat(typed);
				refractionLensSlider.setValue((int)(value));
			}
		});
		
		radius1TextField.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent ke) {
				String typed = radius1TextField.getText();
				float value = Float.parseFloat(typed);
				radius1LensSlider.setValue((int)(100*value));
			}
		});
		
		radius2TextField.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent ke) {
				String typed = radius2TextField.getText();
				float value = Float.parseFloat(typed);
				radius2LensSlider.setValue((int)(100*value));
			}
		});
		
		heightTextField.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent ke) {
				String typed = heightTextField.getText();
				float value = Float.parseFloat(typed);
				heightLensSlider.setValue((int)(value*100));
			}
		});
		
		locationTextField.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent ke) {
				String typed = locationTextField.getText();
				float value = Float.parseFloat(typed);
				locationLensSlider.setValue((int)(value*100));
			}
		});
		
		thicknessTextField.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent ke) {
				String typed = thicknessTextField.getText();
				float value = Float.parseFloat(typed);
				thicknessLensSlider.setValue((int)(value*100));
			}
		});
		
		/********************************************************************************/
		//setting items in panel
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 1;
		c.ipady = 0;
		c.weightx = 0.15;
		c.weighty = 0.0;
		c.insets = new Insets(10,0,10,0);
		
		c.gridx = 0;
		c.gridy = 0;
		this.add(refractionLensLabel, c);
		c.gridy = 1;
		this.add(refractionLensSlider, c);
		c.gridy = 2; 
		this.add(refractionTextField, c);
		
		c.gridx = 1;
		c.gridy = 0;
		this.add(locationLensLabel, c);
		c.gridy = 1;
		this.add(locationLensSlider, c);
		c.gridy = 2; 
		this.add(locationTextField, c);
		
		c.gridx = 2;
		c.gridy = 0;
		this.add(thicknessLensLabel, c);
		c.gridy = 1;
		this.add(thicknessLensSlider, c);
		c.gridy = 2; 
		this.add(thicknessTextField, c);
		
		c.gridx = 3;
		c.gridy = 0;
		this.add(heightLensLabel, c);
		c.gridy = 1;
		this.add(heightLensSlider, c);
		c.gridy = 2; 
		this.add(heightTextField, c);
		
		c.gridx = 4;
		c.gridy = 0;
		this.add(radius1LensLabel, c);
		c.gridy = 1;
		this.add(radius1LensSlider, c);
		c.gridy = 2; 
		this.add(radius1TextField, c);
		
		c.gridx = 5;
		c.gridy = 0;
		this.add(radius2LensLabel, c);
		c.gridy = 1;
		this.add(radius2LensSlider, c);
		c.gridy = 2; 
		this.add(radius2TextField, c);
	}

	int [] getData() //getting data from sliders
	{
		int [] data = new int[6];
		data[0] = refractionLensSlider.getValue();
		data[1] = locationLensSlider.getValue();
		data[2] = thicknessLensSlider.getValue();
		data[3] = heightLensSlider.getValue();
		data[4] = radius1LensSlider.getValue();
		data[5] = radius2LensSlider.getValue();
		return data;
	}

	void setDefaultValues() //setting default values on sliders
	{
		refractionLensSlider.setValue(refractionLensSlider.getDefaultValue());
		locationLensSlider.setValue(locationLensSlider.getDefaultValue());
		thicknessLensSlider.setValue(thicknessLensSlider.getDefaultValue());
		heightLensSlider.setValue(heightLensSlider.getDefaultValue());
		radius1LensSlider.setValue(radius1LensSlider.getDefaultValue());
		radius2LensSlider.setValue(radius2LensSlider.getDefaultValue());
	}

}

