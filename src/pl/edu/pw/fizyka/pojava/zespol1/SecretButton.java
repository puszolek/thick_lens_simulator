package pl.edu.pw.fizyka.pojava.zespol1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SecretButton extends JButton {

	private static final long serialVersionUID = -6743058820333241819L;
	Random rand = new Random();
	JFrame message = new JFrame();
	public SecretButton(String arg0) {
		super(arg0);
		this.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e) {
				 switch (rand.nextInt(5))
			     {
			     	case 0:
			        	JOptionPane.showMessageDialog(message, "Prof. dr hab. in¿ M. Karpierz: \"Teraz do oœwietlania wykorzystuje siê specjalne lampy, ale kiedyœ ich nie by³o. \nKiedyœ wykorzystywano œwiat³o z ogniska, którego pilnowa³ jakiœ murzyn... optyk... mechanik... optoelektronik!\"");
			        	break;
			        case 1:
			        	JOptionPane.showMessageDialog(message, "Prof. dr hab. in¿ M. Karpierz: \"Zapiszmy teraz równania Maxwella w postaci ró¿niczkowej, bo tylko tak zapisuj¹ je prawdziwi mê¿czyŸni.\"");
			        	break;
			        case 2:
			        	JOptionPane.showMessageDialog(message, "Prof. nzw dr hab. M. Urbañski: \"Tak jak teraz w CERNie zbudowali. Dali im 3 miliardy euro, ¿eby znaleŸli bozon Higgsa. \nNo i znajd¹. Jak ja bym tyle dosta³, to te¿ bym im znalaz³.\"");
			        	break;
			        case 3:
			        	JOptionPane.showMessageDialog(message, "Dr in¿. M. Wierzbicki: \"Jest w tym pojêciu sprzecznoœæ, bo cz¹stki musz¹ wymieniaæ miêdzy sob¹ energiê, \naby uk³ad móg³ osi¹gn¹æ stan równowagi termodynamicznej. Widocznie robi¹ to po cichu.\"");
			        	break;
			        case 4:
			        	JOptionPane.showMessageDialog(message, "Dr M. Majchrowski: \"Coœ Pañstwa ma³o... Mam nadziejê, ¿e to nie moja wina.\"");
			     }
			 }
		});
	}
}
