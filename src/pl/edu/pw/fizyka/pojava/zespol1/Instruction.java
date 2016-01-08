package pl.edu.pw.fizyka.pojava.zespol1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Instruction extends JFrame {

	private static final long serialVersionUID = -2394438354136352130L;

	public Instruction() throws HeadlessException {
		super("Instrukcja");
		
		JLabel img = new JLabel(new ImageIcon("test.jpg"));
		
		//panel
		JPanel panel = new JPanel();
		setLayout(new BorderLayout());
		panel.setBackground(Color.WHITE);
		add(panel);
		
		//title
		JLabel title = new JLabel("Uber-pro Thick Lens Simulator                               ");
		title.setFont(new Font("headline", Font.ITALIC, 30));
		title.setForeground(new Color(25,25,150));
		panel.add(title,BorderLayout.LINE_START);
		
		//image
		panel.add(img);
		
		//text
		String text = new String("\n\nProgram Uber-pro Thick Lens Simulator to potê¿ne narzêdzie s³u¿¹ce do modelowania uk³adów rzeczywistych soczewek. "
				+"U¿ytkownik ma mo¿liwoœæ zmiany parametrów soczewki: wspó³czynnika za³amania soczewki, krzywizn, wielkoœci, po³o¿enia"
				+" oraz œrodowiska: iloœæ promieni padaj¹cych, k¹t ich padania, wspó³czynnik za³amania oœrodka. "
				+"Program pozwala równie¿ na eksport oraz import stworzonego projektu do pliku *.txt.\n\n"
				+"Symulator soczewek zosta³ stworzony w ramach zaliczenia projektu z programowania obiektowego.\n\n"
				+"Autorzy: Kmak Mateusz, Kochañska Paula\n"
				+"Politechnika Warszawska, Wydzia³ Fizyki, 2015.");
		
		JTextArea textArea = new JTextArea(text);
		textArea.setPreferredSize(new Dimension(650,300));
		textArea.setBorder(null);
		textArea.setBackground(Color.WHITE);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("text", Font.LAYOUT_LEFT_TO_RIGHT, 12));
		textArea.setEditable(false);
		panel.add(textArea);
		
		//window parameters 
		setSize(700,600);
		setResizable(false);
		setLocationRelativeTo(null);
		
	}
}
