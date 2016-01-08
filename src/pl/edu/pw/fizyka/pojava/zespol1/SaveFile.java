package pl.edu.pw.fizyka.pojava.zespol1;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SaveFile {
	
	JFrame message = new JFrame();

	public SaveFile(UserLensPanel [] lensPanel , int [] tabGlobal) {
		
		//window for choosing the file
		JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Wybierz plik");
        chooser.showDialog(null, "Wybierz");
		
		BufferedWriter writer = null;
		try 
		{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(chooser.getSelectedFile()),Charset.forName("utf-8")));
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
			JOptionPane.showMessageDialog(message, "ERROR!");
		}
			
		//saving lensCounter
		int line = UserInterface.lensCounter;
		try 
		{
			writer.write(String.valueOf(line));
			writer.newLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(message, "ERROR!");
		}
		
		//saving user global panel
		for (int i = 0; i < 4; i++)
		{
			line = tabGlobal[i];
			try 
			{
				writer.write(String.valueOf(line));
				writer.newLine();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(message, "ERROR!");
			}
		}	
	
		//saving user lens panel
		for (int i = 1; i <= UserInterface.lensCounter; i++)
		{
			int [] tabLens = new int[6];
			tabLens = lensPanel[i-1].getData();
			for (int j = 0; j < 6; j++)
			{
				line = tabLens[j];
				try 
				{
					writer.write(String.valueOf(line));
					writer.newLine();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(message, "ERROR!");
				}
			}
		}
					
		try 
		{
			writer.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(message, "ERROR!");
		}
	}
}
