package pl.edu.pw.fizyka.pojava.zespol1;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UserInterface extends JFrame {

	private static final long serialVersionUID = 1219896379777515045L;
	public static int lensCounter = 1;
	UserLensPanel[] lensPanel;
	GraphLensPanel graphLensPanel;

	public UserInterface() throws HeadlessException {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//creating panes
		JPanel userPanel = new JPanel();
		InfoPanel infoPanel = new InfoPanel();
		UserGlobalPanel userGlobalPanel = new UserGlobalPanel();
		
		//tabbedPane
		JTabbedPane tabbedPane = new JTabbedPane();
		lensPanel = new UserLensPanel[3];
		for (int i = 0; i < 3; i++)
		{
			lensPanel[i] = new UserLensPanel();
		}
		
		lensPanel[0].lens.setVisible(true); //defining starting lenses settings
		lensPanel[1].lens.setVisible(true); 
		lensPanel[0].lens.calculate();      
		lensPanel[1].lens.calculate();
		lensPanel[2].lens.calculate();
	    calculatePosLimits(lensPanel[0]);
		calculatePosLimits(lensPanel[1]);
		calculatePosLimits(lensPanel[2]);
		lensPanel[1].lens.setVisible(false); 

		graphLensPanel = new GraphLensPanel(lensPanel,userGlobalPanel);
		tabbedPane.addTab("Soczewka "+lensCounter, lensPanel[lensCounter-1]);
		
		//setting background
		Color background = new Color(220,220,220);
		tabbedPane.setBackground(background);
		userPanel.setBackground(new Color(216,191,216));
		
		//border
		Border borderLine = BorderFactory.createLineBorder(new Color(30,144,255));
		infoPanel.setBorder(borderLine);
		userGlobalPanel.setBorder(borderLine);
		graphLensPanel.setBorder(borderLine);
		
		//scrollable container
		Container container = getContentPane();
		JScrollPane scrollPanel = new JScrollPane(container);
		setContentPane(scrollPanel);
	
		/********************************************************************************/
		//adding subpanels to main panels
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.7;
		c.ipady = 361;
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		container.add(graphLensPanel, c);
		
		c.weighty = 0.3;
		c.ipady = 0;
		c.gridy = 1;
		container.add(userPanel, c);
		
		userPanel.setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,2,2,2);
		c.weightx = 0.6;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LAST_LINE_START;
		userPanel.add(tabbedPane, c);
		
		c.weightx = 0.2;
		c.gridx = 1;
		c.ipady = 5;
		userPanel.add(userGlobalPanel, c);
		
		c.ipady = 57;
		c.gridx = 2;
		userPanel.add(infoPanel, c);

		//menu
		MenuBar menuBar = new MenuBar(tabbedPane, lensPanel, userGlobalPanel, graphLensPanel, infoPanel);
		setJMenuBar(menuBar);
		
		//popupmenu
		PopupMenu popupMenu = new PopupMenu(tabbedPane, lensPanel, userGlobalPanel, graphLensPanel, infoPanel);
		
		//mouse right-click 
		container.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					popupMenu.show(graphLensPanel, (int)(MouseInfo.getPointerInfo().getLocation().getX()-graphLensPanel.getLocationOnScreen().getX()), 
						(int)(MouseInfo.getPointerInfo().getLocation().getY()-graphLensPanel.getLocationOnScreen().getY()));
				}
			}
		});
		
		/********************************************************************************/
		//sliders listeners
		userGlobalPanel.beamSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				userGlobalPanel.beamTextField.setText(String.valueOf(userGlobalPanel.beamSlider.getValue()));
				graphLensPanel.setBeams(userGlobalPanel.beamSlider.getValue());

				graphLensPanel.repaint();
			}
		});
		
		userGlobalPanel.beamAngleSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				userGlobalPanel.beamAngleTextField.setText(String.valueOf(userGlobalPanel.beamAngleSlider.getValue()));
				graphLensPanel.setStartAngle(userGlobalPanel.beamAngleSlider.getValue());

				graphLensPanel.repaint();
			}
		});
		
		userGlobalPanel.beamPositionSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				userGlobalPanel.beamPositionTextField.setText(String.valueOf(userGlobalPanel.beamPositionSlider.getValue()));
				graphLensPanel.setStartY(userGlobalPanel.beamPositionSlider.getValue());
				graphLensPanel.repaint();
			}
		});
		
		userGlobalPanel.refractionGlobalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				userGlobalPanel.refractionGlobalTextField.setText(String.valueOf((float)(userGlobalPanel.refractionGlobalSlider.getValue())/100));
				graphLensPanel.setN((double)userGlobalPanel.refractionGlobalSlider.getValue()/100);

				graphLensPanel.setBackground(new Color((float)(356-userGlobalPanel.refractionGlobalSlider.getValue())/256,
						(float)(356-userGlobalPanel.refractionGlobalSlider.getValue())/256,(float)(356-userGlobalPanel.refractionGlobalSlider.getValue())/256));
				graphLensPanel.repaint();
				
				for (int i = 2; i >= 0; i--)
				{
					UserLensPanel tmp = lensPanel[i];
					int t = i;
					calculateInfoPanel(tmp, infoPanel, userGlobalPanel, t);
				}
			}
		});
		
		//listeners for each lens
		for (int i = 2; i >= 0; i--)
		{
			UserLensPanel tmp = lensPanel[i];
			int t = i;
			
			tmp.refractionLensSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e)
				{
					tmp.refractionTextField.setText(String.valueOf(((float)tmp.refractionLensSlider.getValue()/100)));
					tmp.lens.setRefraction((double) tmp.refractionLensSlider.getValue()/100);
					tmp.lens.calculate();
					tmp.lens.setArcs();
					
					graphLensPanel.repaint();
					calculateInfoPanel(tmp, infoPanel, userGlobalPanel, t);
				}
			});
	
			tmp.radius1LensSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e)
				{
					if (tmp.radius1LensSlider.getValue() != 0)
					{
						tmp.radius1TextField.setText(String.valueOf((float)tmp.radius1LensSlider.getValue()/100));
						tmp.lens.setRadius1(tmp.lens.getHeight()/((float)tmp.radius1LensSlider.getValue()/100));
					}
					else 
					{
						tmp.radius1TextField.setText("0");
						tmp.lens.setRadius1(100000);
					}
					tmp.lens.calculate();
					tmp.lens.setArcs();
					tmp.lens.setMinRad(2);

					tmp.lens.setMaxHght();
					tmp.heightLensSlider.setMaximum(tmp.lens.getMaxHght());
					
					tmp.lens.setMinThick();
					tmp.thicknessLensSlider.setMinimum(tmp.lens.getMinThick());
					
					tmp.radius2LensSlider.setMinimum(tmp.lens.getMinRad(2));
					
					calculatePosLimits(tmp);
					graphLensPanel.repaint();
					calculateInfoPanel(tmp, infoPanel, userGlobalPanel, t);
				}
			});
			
			tmp.radius2LensSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e)
				{
					if (tmp.radius2LensSlider.getValue() != 0.0)
					{
						tmp.radius2TextField.setText(String.valueOf((float)tmp.radius2LensSlider.getValue()/100));
						tmp.lens.setRadius2(tmp.lens.getHeight()/((float)tmp.radius2LensSlider.getValue()/100));
					}
					else 
					{
						tmp.radius2TextField.setText("0");
						tmp.lens.setRadius2(100000);
					}
				
					tmp.lens.calculate();
					tmp.lens.setArcs();
					tmp.lens.setMinRad(1);
				
					tmp.lens.setMinThick();
					tmp.thicknessLensSlider.setMinimum(tmp.lens.getMinThick());
					tmp.lens.setMaxHght();
					tmp.heightLensSlider.setMaximum(tmp.lens.getMaxHght());
				
					tmp.radius1LensSlider.setMinimum(tmp.lens.getMinRad(1));
					
					calculatePosLimits(tmp);					
					graphLensPanel.repaint();
					calculateInfoPanel(tmp, infoPanel, userGlobalPanel, t);
				}
			});
			
			tmp.heightLensSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e)
				{
					tmp.heightTextField.setText(String.valueOf((tmp.heightLensSlider.getValue())));
					tmp.lens.setHeight(tmp.heightLensSlider.getValue());
					if (tmp.radius1LensSlider.getValue() != 0)
					{
						tmp.lens.setRadius1(tmp.lens.getHeight()/(((float)tmp.radius1LensSlider.getValue()/100)));
					}
					else
					{
						tmp.lens.setRadius1(100000);
					}
					if (tmp.radius2LensSlider.getValue() != 0)
					{
						tmp.lens.setRadius2(tmp.lens.getHeight()/(((float)tmp.radius2LensSlider.getValue()/100)));
					}
					else
					{
						tmp.lens.setRadius2(100000);
					}
					tmp.lens.calculate();
					tmp.lens.setArcs();
					tmp.lens.setMinThick();
					
					calculatePosLimits(tmp);
					
					tmp.thicknessLensSlider.setMinimum(tmp.lens.getMinThick());
					graphLensPanel.repaint();
					calculateInfoPanel(tmp, infoPanel, userGlobalPanel, t);
				}
			});
			
			tmp.locationLensSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e)
				{
					tmp.locationTextField.setText(String.valueOf((tmp.locationLensSlider.getValue())));
					tmp.lens.setLocation(tmp.locationLensSlider.getValue());
					tmp.lens.calculate();
					tmp.lens.setArcs();
					
					calculatePosLimits(tmp);
					
					graphLensPanel.repaint();
					calculateInfoPanel(tmp, infoPanel, userGlobalPanel, t);
				}
			});
			
			tmp.thicknessLensSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e)
				{
					tmp.thicknessTextField.setText(String.valueOf((tmp.thicknessLensSlider.getValue())));
					tmp.lens.setThickness(tmp.thicknessLensSlider.getValue());
					tmp.lens.calculate();
					tmp.lens.setArcs();
					tmp.lens.setMaxHght();
					
					calculatePosLimits(tmp);
					
					tmp.heightLensSlider.setMaximum(tmp.lens.getMaxHght());
					graphLensPanel.repaint();
					calculateInfoPanel(tmp, infoPanel, userGlobalPanel, t);
				}
			});
		}
	}
	
	public void calculatePosLimits(UserLensPanel tmp) //awesome function, tmp - current lens
	{
		if(tmp == lensPanel[0]) //lens 0
		{	
			lensPanel[1].lens.setPosLimit(1, tmp.lens); //setting position limit for next lens
			tmp.lens.setPosLimit(2, lensPanel[1].lens);
			tmp.lens.setPosLimit(1, null); //1- left, 2 - right, null - no limit
			
			lensPanel[1].locationLensSlider.setMinimum(lensPanel[1].lens.getPosLimit(1)); //setting on sliders
			tmp.locationLensSlider.setMaximum(tmp.lens.getPosLimit(2));
			tmp.locationLensSlider.setMinimum(tmp.lens.getPosLimit(1));			
		}
		else if (tmp == lensPanel[1]) //lens 1
		{
			lensPanel[2].lens.setPosLimit(1, tmp.lens);
			lensPanel[0].lens.setPosLimit(2, tmp.lens);
			tmp.lens.setPosLimit(2, lensPanel[2].lens);
			tmp.lens.setPosLimit(1, lensPanel[0].lens);

			
			lensPanel[2].locationLensSlider.setMinimum(lensPanel[2].lens.getPosLimit(1));
			lensPanel[0].locationLensSlider.setMaximum(lensPanel[0].lens.getPosLimit(2));
			tmp.locationLensSlider.setMaximum(tmp.lens.getPosLimit(2));
			tmp.locationLensSlider.setMinimum(tmp.lens.getPosLimit(1));
		}
		else //lens 2
		{
			lensPanel[1].lens.setPosLimit(2, tmp.lens);
			tmp.lens.setPosLimit(1, lensPanel[1].lens);

			lensPanel[1].locationLensSlider.setMaximum(lensPanel[1].lens.getPosLimit(2));
			tmp.locationLensSlider.setMinimum(tmp.lens.getPosLimit(1));
		}
	}
	
	//calculating focal lengths
	public void calculateInfoPanel(UserLensPanel tmp, InfoPanel infoPanel, UserGlobalPanel userGlobalPanel, int t)
	{
		int focal = 0;
		float n = (float)tmp.refractionLensSlider.getValue()/(float)userGlobalPanel.refractionGlobalSlider.getValue(); //effective refractive index
		
		if (tmp.refractionLensSlider.getValue() == userGlobalPanel.refractionGlobalSlider.getValue())
		{
			infoPanel.setFocalLength(t, "INF"); //infinitive focal
		}
		else
		{
			focal = - (int) ((n*tmp.lens.getRadius2()*tmp.lens.getRadius1())/
					((n-1)*(n*(-tmp.lens.getRadius2()-tmp.lens.getRadius1())+(n-1)*tmp.lens.getThickness())));
			infoPanel.setFocalLength(t, focal);
			
			if (lensCounter == 1)
			{
				infoPanel.setFocalLength(1, 0);
				infoPanel.setFocalLength(2, 0);
			}
			else if (lensCounter == 2)
			{
				infoPanel.setFocalLength(2, 0);
			}
		}
	}

	public static void main(String[] args) {
		
		UserInterface frame = new UserInterface(); 
		frame.setTitle("Uber-pro Thick Lens Simulator");
	    final GraphicsConfiguration config = frame.getGraphicsConfiguration();

	    final int left = Toolkit.getDefaultToolkit().getScreenInsets(config).left;
	    final int right = Toolkit.getDefaultToolkit().getScreenInsets(config).right;
	    final int top = Toolkit.getDefaultToolkit().getScreenInsets(config).top;
	    final int bottom = Toolkit.getDefaultToolkit().getScreenInsets(config).bottom;

	    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    final int width = (int)(screenSize.width - left - right);
	    final int height = (int)(screenSize.height - top - bottom);

	    frame.setResizable(true);
	    frame.setSize(width - 500,height - 15);
	    frame.setMinimumSize(new Dimension(1000,500));
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
	    frame.setVisible(true);
	}

}
