package pl.edu.pw.fizyka.pojava.zespol1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class OpenFile {
	
	JFrame message = new JFrame();

	public OpenFile(UserGlobalPanel ugp, UserLensPanel [] ulp, JTabbedPane tabbedPane) {
		
		JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Wybierz plik");
        chooser.showDialog(null, "Wybierz");
        
        FileInputStream fis = null;
		try 
		{
			fis = new FileInputStream(chooser.getSelectedFile());
		} 
		catch (FileNotFoundException e) 
		{
			JOptionPane.showMessageDialog(message, "Nie znaleziono pliku!");
		}
		
		InputStreamReader reader = new InputStreamReader(fis, Charset.forName("utf-8"));
		BufferedReader br = new BufferedReader(reader);
		
		try 
		{
			int tmp = UserInterface.lensCounter;
			UserInterface.lensCounter = Integer.valueOf(br.readLine());
			int difference = UserInterface.lensCounter - tmp;
			
			if (difference > 0)
			{
				for (int i = 1; i <= difference; i++)
				{
					tabbedPane.addTab("Soczewka "+(tmp+i), ulp[tmp+i-1]);
				}
			}
			else if (difference < 0)
			{
				for (int i = 1; i <= -difference; i++)
				{
					tabbedPane.remove(tmp-i);
		    		ulp[tmp-i] = new UserLensPanel();
				}
			}
			ugp.beamSlider.setValue(Integer.valueOf(br.readLine()));
			ugp.beamAngleSlider.setValue(Integer.valueOf(br.readLine()));
			ugp.beamPositionSlider.setValue(Integer.valueOf(br.readLine()));
			ugp.refractionGlobalSlider.setValue(Integer.valueOf(br.readLine()));
		} 
		catch (NumberFormatException e1) 
		{
			e1.printStackTrace();
			JOptionPane.showMessageDialog(message, "Nieprawid³owe dane!");
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
			JOptionPane.showMessageDialog(message, "ERROR!");
		}
		
		for (int i = 1; i <= UserInterface.lensCounter; i++)
		{
			try 
			{
				ulp[i-1].refractionLensSlider.setValue(Integer.valueOf(br.readLine()));
				ulp[i-1].locationLensSlider.setValue(Integer.valueOf(br.readLine()));
				ulp[i-1].thicknessLensSlider.setValue(Integer.valueOf(br.readLine()));
				ulp[i-1].heightLensSlider.setValue(Integer.valueOf(br.readLine()));
				ulp[i-1].radius1LensSlider.setValue(Integer.valueOf(br.readLine()));
				ulp[i-1].radius2LensSlider.setValue(Integer.valueOf(br.readLine()));
			} 
			catch (NumberFormatException e) 
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(message, "Nieprawid³owe dane!");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(message, "ERROR!");
			}
		}
		
		try 
		{
			fis.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(message, "ERROR!");
		}
	}

}

