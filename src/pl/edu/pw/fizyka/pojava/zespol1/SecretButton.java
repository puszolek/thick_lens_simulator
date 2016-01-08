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
			        	JOptionPane.showMessageDialog(message, "Prof. dr hab. in� M. Karpierz: \"Teraz do o�wietlania wykorzystuje si� specjalne lampy, ale kiedy� ich nie by�o. \nKiedy� wykorzystywano �wiat�o z ogniska, kt�rego pilnowa� jaki� murzyn... optyk... mechanik... optoelektronik!\"");
			        	break;
			        case 1:
			        	JOptionPane.showMessageDialog(message, "Prof. dr hab. in� M. Karpierz: \"Zapiszmy teraz r�wnania Maxwella w postaci r�niczkowej, bo tylko tak zapisuj� je prawdziwi m�czy�ni.\"");
			        	break;
			        case 2:
			        	JOptionPane.showMessageDialog(message, "Prof. nzw dr hab. M. Urba�ski: \"Tak jak teraz w CERNie zbudowali. Dali im 3 miliardy euro, �eby znale�li bozon Higgsa. \nNo i znajd�. Jak ja bym tyle dosta�, to te� bym im znalaz�.\"");
			        	break;
			        case 3:
			        	JOptionPane.showMessageDialog(message, "Dr in�. M. Wierzbicki: \"Jest w tym poj�ciu sprzeczno��, bo cz�stki musz� wymienia� mi�dzy sob� energi�, \naby uk�ad m�g� osi�gn�� stan r�wnowagi termodynamicznej. Widocznie robi� to po cichu.\"");
			        	break;
			        case 4:
			        	JOptionPane.showMessageDialog(message, "Dr M. Majchrowski: \"Co� Pa�stwa ma�o... Mam nadziej�, �e to nie moja wina.\"");
			     }
			 }
		});
	}
}
