package ATM;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;

public class Fondo extends JDesktopPane
{
	
		public void paintComponent(Graphics g)
		{
			Dimension tam=getSize();
			ImageIcon imagen=new ImageIcon(new ImageIcon(getClass().getResource("banco.jpg")).getImage());
			g.drawImage(imagen.getImage(),0,0,tam.width,tam.height,null);
		}
	

}
