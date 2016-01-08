package pl.edu.pw.fizyka.pojava.zespol1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 1173219287462931940L;
	JFrame message = new JFrame();
	SaveFile save;
	OpenFile open;
	Instruction instr = new Instruction();

	public MenuBar(JTabbedPane tabbedPane, UserLensPanel ulp[], UserGlobalPanel ugp, GraphLensPanel glp, InfoPanel infoPanel) {
		
		//creating menu bar
		JMenu menu = new JMenu("Menu g³ówne");
		this.add(menu);
		JMenu menuLens = new JMenu("Soczewki");
		this.add(menuLens);
		JMenu menuHelp = new JMenu("O programie");
		this.add(menuHelp);
		
		/********************************************************************************/
		//menu positions with listeners
		JMenuItem menuItemOpen = new JMenuItem("Otwórz projekt");
		menuItemOpen.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	open = new OpenFile(ugp, ulp, tabbedPane);
		    }
		});
		
		JMenuItem menuItemSave = new JMenuItem("Zapisz projekt");
		menuItemSave.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        save = new SaveFile(ulp, ugp.getData());
		    }
		});
		
		JMenuItem menuItemAddLens = new JMenuItem("Dodaj soczewkê");
		menuItemAddLens.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if (UserInterface.lensCounter < 3){
		    		UserInterface.lensCounter++;
		    		ulp[UserInterface.lensCounter-1].lens.calculate();
		    		ulp[UserInterface.lensCounter-1].lens.setArcs();
		    		ulp[UserInterface.lensCounter-1].lens.setVisible(true);
		    		ulp[UserInterface.lensCounter-2].locationLensSlider.setMaximum((int)ulp[UserInterface.lensCounter-2].lens.getLocation());
		    		glp.repaint();
		    		tabbedPane.addTab("Soczewka "+ UserInterface.lensCounter, ulp[UserInterface.lensCounter-1]);
		    		infoPanel.setFocalLength(UserInterface.lensCounter-1, 317);
		    	}
		    	else JOptionPane.showMessageDialog(message, "Osi¹gniêto maksymaln¹ iloœæ soczewek!");
		    }
		});
		
		JMenuItem menuItemRemoveLens = new JMenuItem("Usuñ soczewkê");
		menuItemRemoveLens.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	if (UserInterface.lensCounter > 1){
			    		UserInterface.lensCounter--;
			    		ulp[UserInterface.lensCounter-1].lens.calculate();
			    		ulp[UserInterface.lensCounter-1].lens.setArcs();
			    		ulp[UserInterface.lensCounter].setDefaultValues();
			    		ulp[UserInterface.lensCounter].lens.setVisible(false);
			    		ulp[UserInterface.lensCounter-1].locationLensSlider.setMaximum(1000);
			       		
			    		glp.repaint();
			    		tabbedPane.remove(UserInterface.lensCounter);
			    	}
			    	else JOptionPane.showMessageDialog(message, "Musi istnieæ przynajmniej jedna soczewka!");
			    }
		});
		
		JMenuItem menuItemResetLens = new JMenuItem("Wyczyœæ");
		menuItemResetLens.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	if (UserInterface.lensCounter > 1){
			    		UserInterface.lensCounter--;
			    		for (int i = 0; i < UserInterface.lensCounter; i++)
			    		{
				    		tabbedPane.remove(UserInterface.lensCounter-i);
				    		ulp[UserInterface.lensCounter-i].setDefaultValues();
				    		ulp[UserInterface.lensCounter-i].lens.setVisible(false);
			    		}
			    		ugp.setDefaultValues();
			    		ulp[0].setDefaultValues();
			    		for (int i = 0; i < 3; i++)
			    		{
			    			ulp[i].lens.setLocation(500+137*i);
				    		ulp[i].locationLensSlider.setValue(500+137*i);
			    		}
			    		ulp[0].lens.calculate();
			    		ulp[0].lens.setArcs();
			    		ulp[0].lens.setVisible(true);
			    		glp.repaint();
			    		UserInterface.lensCounter = 1;
			    	}
			    	else JOptionPane.showMessageDialog(message, "Musi istnieæ przynajmniej jedna soczewka!");
			    	infoPanel.setFocalLength(1, 0);
			    	infoPanel.setFocalLength(2, 0);
			    }
		});
		
		JMenuItem menuItemExit = new JMenuItem("WyjdŸ z programu");
		menuItemExit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(null,"Czy napewno chcesz wyjœæ?", "Wyjœcie z programu", JOptionPane.YES_NO_OPTION);
	        	if(answer == JOptionPane.YES_OPTION)
	        		System.exit(0);
		    }
		});
		
		JMenuItem menuItemHelp = new JMenuItem("Instrukcja");
		menuItemHelp.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	instr.setVisible(true);
		    }
		});
		
		JMenuItem menuItemAbout = new JMenuItem("O programie...");
		menuItemAbout.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	JOptionPane.showMessageDialog(message, "wersja 1.0 Uber-pro Thick Lens Simulator\nKmak Mateusz, "
		    			+ "Kochañska Paula\nWydzia³ Fizyki Politechniki Warszawskiej 2015");
		    }
		});
		
		/********************************************************************************/
		//adding positions to menu
		menu.add(menuItemOpen);
		menu.addSeparator();
		menu.add(menuItemSave);
		menu.addSeparator();
		menu.add(menuItemExit);
		menuLens.add(menuItemAddLens);
		menuLens.addSeparator();
		menuLens.add(menuItemRemoveLens);
		menuLens.addSeparator();
		menuLens.add(menuItemResetLens);
		menuHelp.add(menuItemHelp);
		menuHelp.addSeparator();
		menuHelp.add(menuItemAbout);
	}
}
