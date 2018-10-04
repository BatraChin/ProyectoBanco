package Prestamo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import com.mysql.jdbc.Connection;


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

public class PrestamoUI extends javax.swing.JFrame 
{
   private Tareas tareas;
   private JLabel empleado;
   private JLabel contraseña;
   protected JTextField loguearEmp;
   private JPasswordField password;
   private JDesktopPaneImagen jDesktopPane1;
   private JButton ingresar;
   protected Connection conexionBD = null;
   private String servidor = "localhost:3306";
   private String baseDatos = "banco";
   private String usuario = "admin";
   private String clave = "admin";
  
   /**
   * Auto-generated main method to display this JFrame
   */
   public static void main(String[] args) 
   {
      SwingUtilities.invokeLater(new Runnable(){
         public void run() 
         {
            PrestamoUI inst = new PrestamoUI();
            inst.setLocationRelativeTo(null);
            inst.setVisible(true);
         }
      });
   }
   
   public PrestamoUI() 
   {
      super();
      conectarBD();
      initGUI();

      this.tareas = new Tareas(this);
      this.tareas.setVisible(false);
      this.jDesktopPane1.add(this.tareas);

   }
   
   private void initGUI() 
   {
      try 
      {
         javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
      } 
      catch(Exception e) 
      {
         e.printStackTrace();
      }
      
      try 
      {
         {
            this.setTitle("Banco");
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         }
         {
            jDesktopPane1 = new JDesktopPaneImagen();
            getContentPane().add(jDesktopPane1, BorderLayout.CENTER);
            jDesktopPane1.setBackground(new Color(25, 25, 112));
            jDesktopPane1.setPreferredSize(new java.awt.Dimension(1100,600));
            jDesktopPane1.setEnabled(false);
         }
         {
           	empleado=new JLabel("Legajo");
           	empleado.setForeground(Color.WHITE);
           	empleado.setBackground(Color.WHITE);
           	empleado.setBounds(346,45,200,200);
           	jDesktopPane1.add(empleado);  
         }
         {
        	 loguearEmp=new JTextField("");
        	 loguearEmp.setBounds(400,130,200,30);
        	 jDesktopPane1.add(loguearEmp);        	 	 
         }
         
         {
        	 ingresar=new JButton("Iniciar sesión");
        	 ingresar.setBounds(450,350,100,20);
        	 jDesktopPane1.add(ingresar);
        	 ingresar.addActionListener(new ActionListener(){
             	public void actionPerformed(ActionEvent arg0)
             	{
             		try 
             		{
						if (authenticate()) 
						{
							JOptionPane.showMessageDialog(null, "Bienvenido,ya puede realizar las consultas");
							tareas.setVisible(true);
							
							  
						} 
						else 
						{	
							JOptionPane.showMessageDialog(null, "Vuelve a ingresar tu contraseña \n La contraseña no es válida. "
							   		+ "Por favor, asegúrate de que el bloqueo de mayúsculas no está activado e inténtalo de nuevo.");
							password.setText("");
						}
					} 
             		catch (HeadlessException | SQLException e) 
             		{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
             	 }
                });
        	 
         
         	{
         			
         		contraseña=new JLabel("Contraseña");
         		contraseña.setForeground(Color.WHITE);
         		contraseña.setBackground(Color.WHITE);
         		contraseña.setSize(200,200);
         		contraseña.setBounds(320,165,200,200);
         		jDesktopPane1.add(contraseña);  
         		password = new JPasswordField();
         		password.setBounds(400,250,200,30);
         		jDesktopPane1.add(password);
         	}
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

   private void conectarBD()
   {
      if (this.conexionBD == null)
      {
         try
         {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
         }
         catch (Exception ex)
         {
            System.out.println(ex.getMessage());
         }
   
         try
         {
           
            this.conexionBD=(Connection) DriverManager.getConnection ("jdbc:mysql://"+servidor+"/"+baseDatos,usuario,clave);
            
         }
         catch (SQLException ex)
         {
            JOptionPane.showMessageDialog(this,
                                          "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
         }
     }
   }
   
   public boolean authenticate() throws SQLException 
   {
	   Statement stmt = this.conexionBD.createStatement();
	   

       String sql = "SELECT password " + 
                    "FROM empleado " +
                    "WHERE legajo LIKE '%"+ loguearEmp.getText()+"%' "; 
 
       ResultSet rs = stmt.executeQuery(sql);
       
       char passArray[] = password.getPassword();
  
       for (int i = 0; i < passArray.length; i++) 
       {
           char c = passArray[i];
           if (!Character.isLetterOrDigit(c)) return false;
       }

       String pass = new String(passArray); 
       String pass2 = null;
       
       while (rs.next ()) 
       {
           pass2 = rs.getString ("password");
           
       }

       if ( pass.equals(pass2))
       {
    	   return true;
       } 
       else 
       {
           return false;
       }
   }
}
      
 

