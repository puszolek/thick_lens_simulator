package pl.edu.pw.fizyka.pojava.zespol1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

public class PopupMenu extends JPopupMenu {
	
	private static final long serialVersionUID = 5053908076453206247L;
	JFrame message = new JFrame();

	public PopupMenu(JTabbedPane tabbedPane, UserLensPanel lensPanel[], UserGlobalPanel userGlobalPanel, GraphLensPanel glp, InfoPanel infoPanel) {
		
		JMenuItem popupAddLens = new JMenuItem("Dodaj soczewkê");
		popupAddLens.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if (UserInterface.lensCounter < 3){
		    		UserInterface.lensCounter++;
		    		lensPanel[UserInterface.lensCounter-1].lens.calculate();
		    		lensPanel[UserInterface.lensCounter-1].lens.setArcs();
		    		lensPanel[UserInterface.lensCounter-1].lens.setVisible(true);
		    		lensPanel[UserInterface.lensCounter-2].locationLensSlider.setMaximum((int)lensPanel[UserInterface.lensCounter-2].lens.getLocation());
		    		glp.repaint();
		    		tabbedPane.addTab("Soczewka "+UserInterface.lensCounter, lensPanel[UserInterface.lensCounter-1]);
		    	}
		    	else JOptionPane.showMessageDialog(message, "Osi¹gniêto maksymaln¹ iloœæ soczewek!");
		    	infoPanel.setFocalLength(UserInterface.lensCounter-1, 317);
		    }
		});
		
		JMenuItem popupRemoveLens = new JMenuItem("Usuñ soczewkê");
		popupRemoveLens.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	if (UserInterface.lensCounter > 1){
			    		UserInterface.lensCounter--;
			    		lensPanel[UserInterface.lensCounter-1].lens.calculate();
			    		lensPanel[UserInterface.lensCounter-1].lens.setArcs();
			    		lensPanel[UserInterface.lensCounter].setDefaultValues();
			    		lensPanel[UserInterface.lensCounter].lens.setVisible(false);
			    		lensPanel[UserInterface.lensCounter-1].locationLensSlider.setMaximum(1000);
			    		glp.repaint();
			    		tabbedPane.remove(UserInterface.lensCounter);
			    	}
			    	else JOptionPane.showMessageDialog(message, "Musi istnieæ przynajmniej jedna soczewka!");
			    }
		});
		
		JMenuItem popupResetLens = new JMenuItem("Wyczyœæ");
		popupResetLens.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	if (UserInterface.lensCounter > 1){
			    		UserInterface.lensCounter--;
			    		for (int i = 0; i < UserInterface.lensCounter; i++)
			    		{
				    		tabbedPane.remove(UserInterface.lensCounter-i);
				    		lensPanel[UserInterface.lensCounter-i].setDefaultValues();
				    		lensPanel[UserInterface.lensCounter-i].lens.setVisible(false);
			    		}
			    		
			    		userGlobalPanel.setDefaultValues();
			    		lensPanel[0].setDefaultValues();
			    		lensPanel[0].lens.setVisible(true);
			    		for (int i = 0; i < 3; i++)
			    		{
			    			lensPanel[i].lens.setLocation(500+137*i);
				    		lensPanel[i].locationLensSlider.setValue(500+137*i);
			    		}

			    		lensPanel[0].lens.calculate();
			    		lensPanel[0].lens.setArcs();
			    		glp.repaint();
			    		UserInterface.lensCounter = 1;
			    	}
			    	else JOptionPane.showMessageDialog(message, "Musi istnieæ przynajmniej jedna soczewka!");
			    	infoPanel.setFocalLength(1, 0);
			    	infoPanel.setFocalLength(2, 0);
			    }
		});
		
		this.add(popupAddLens);
		this.addSeparator();
		this.add(popupRemoveLens);
		this.addSeparator();
		this.add(popupResetLens);
	}

}

