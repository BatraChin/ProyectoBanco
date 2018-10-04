package Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
@SuppressWarnings("serial")

public class AdminUI extends JFrame 
{
   private Consultas ventanaConsultas;
   private JPasswordField password;
   private JLabel passw;
   private JDesktopPaneImagen jDesktopPane1;
   
   
   /**
   * Auto-generated main method to display this JFrame
   */
   public static void main(String[] args) 
   {
      SwingUtilities.invokeLater(new Runnable(){
         public void run() 
         {
            AdminUI inst = new AdminUI();
            inst.setLocationRelativeTo(null);
            inst.setVisible(true);
         }
      });
   }
   
   public AdminUI() 
   {
      super();
      initGUI();
   }
   
   private void initGUI() 
   {
      try 
      {
         javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
         this.setTitle("Banco");
         this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    
         {
            jDesktopPane1 = new JDesktopPaneImagen();
            getContentPane().add(jDesktopPane1, BorderLayout.CENTER);
            jDesktopPane1.setPreferredSize(new java.awt.Dimension(1100,600));
            jDesktopPane1.setBackground(Color.gray);
            jDesktopPane1.setEnabled(false);
         } 
        
         {
        	 passw=new JLabel("Password");
        	 passw.setForeground(Color.WHITE);
        	 passw.setBackground(Color.WHITE);
        	 passw.setSize(200,200);
        	 passw.setBounds(520,310,200,40);
        	 jDesktopPane1.add(passw);
         }
         	
         {
        	 password = new JPasswordField();
        	 password.setBounds(460,350,200,30);
            
        	 jDesktopPane1.add(password);
        	 password.addActionListener(new ActionListener(){
            	public void actionPerformed(ActionEvent arg0)
            	{
            		if (authenticate()) 
            		{
            			JOptionPane.showMessageDialog(null, "Bienvenido,ya puede realizar las consultas");
            			
            			ventanaConsultas = new Consultas();
            			ventanaConsultas.setVisible(false);
                 	    jDesktopPane1.add(ventanaConsultas);
                 	    ventanaConsultas.setVisible(true);
                 	   
            			password.setText("");
            			  
          		    } 
            		else 
            		{	
            			JOptionPane.showMessageDialog(null, "Vuelve a ingresar tu contraseña \n La contraseña no es válida. "
            			   		+ "Por favor, asegúrate de que el bloqueo de mayúsculas no está activado e inténtalo de nuevo.");
            			password.setText("");
          		    }
            	}
           });
         }
         this.setSize(1100, 800);
         this.setResizable(false);
         pack();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
   
  
   public boolean authenticate() 
   {
       char passArray[] = password.getPassword();
       for (int i = 0; i < passArray.length; i++) 
       {
           char c = passArray[i];
           if (!Character.isLetterOrDigit(c)) return false;
       }

       String pass = new String(passArray);

       if ( pass.equals("admin"))
       {
    	   return true;
       } 
       else 
       {
           return false;
       }
   }
 
}
