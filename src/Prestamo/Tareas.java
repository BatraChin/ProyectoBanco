package Prestamo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
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

public class Tareas extends javax.swing.JInternalFrame  
{
   private JButton creac_prestamo;
   private JButton pag_cuotas;
   private JButton list_morosos;
   private JDesktopPane jDesktopPane;
   private creacion_prestamo c_prestamo;
   private Deudores clientes_morosos;
   private pago_cuota pago_cuota;
   private static PrestamoUI principal;
   
  
   /**
   * Auto-generated main method to display this JFrame
   */
   public static void main(String[] args) 
   {
      SwingUtilities.invokeLater(new Runnable(){
         public void run() 
         {
            Tareas  inst = new Tareas(principal);
            inst.setVisible(true);
         }
      });
   }
   
   public Tareas(PrestamoUI principal)
   {
	  super();
      this.principal=principal;
      initGUI();
      
     
   }
   
   private void initGUI() 
   {
      try 
      {
    	  setPreferredSize(new Dimension(1100, 600));
          this.setBounds(0, 0,1100, 600);
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.setResizable(false);
          setVisible(true);
          BorderLayout thisLayout = new BorderLayout();
          this.setBackground(Color.blue);
          getContentPane().setLayout(thisLayout);
          this.setClosable(true);
        
      
          this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
          this.setMaximizable(true);
      
         javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
      } 
      catch(Exception e) 
      {
         e.printStackTrace();
      }
      
     
         {
            this.setTitle("Tareas empleado");
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         }
         {
            jDesktopPane = new JDesktopPane();
            jDesktopPane.setBackground(new Color(204, 153, 102));
            getContentPane().add(jDesktopPane, BorderLayout.CENTER);
            jDesktopPane.setPreferredSize(new java.awt.Dimension(1100,700));
            jDesktopPane.setEnabled(false);
         }
 
         {
         	creac_prestamo = new JButton("Creación de Préstamos");
         	creac_prestamo.setBounds(390,100,200,30);
         	jDesktopPane.add(creac_prestamo);
         	
         	//CREACION DE PRESTAMOS
         	creac_prestamo.addActionListener(new ActionListener(){
             	public void actionPerformed(ActionEvent arg0)
             	{
             		c_prestamo = new creacion_prestamo(principal);
             	    c_prestamo.setVisible(false);
             	    jDesktopPane.add(c_prestamo);
             		c_prestamo.setVisible(true);
             	 
             	}
            });
         	
         	
         	//PAGO DE CUOTAS
         	
         	pag_cuotas = new JButton("Pago de Cuentas");
         	pag_cuotas.setBounds(390,150,200,30);
         	jDesktopPane.add(pag_cuotas);
         	pag_cuotas.addActionListener(new ActionListener(){
             	public void actionPerformed(ActionEvent arg0)
             	{
             		pago_cuota = new pago_cuota();
             	    pago_cuota.setVisible(false);
             	    jDesktopPane.add(pago_cuota);
             		pago_cuota.setVisible(true);
             	 
             	}
            });
         	
         	
         	
         	
         	//LISTADO DE CLIENTES
         	list_morosos = new JButton("Listado de clientes morosos");
         	list_morosos.setBounds(390,200,200,30);
         	jDesktopPane.add(list_morosos);
         	list_morosos.addActionListener(new ActionListener(){
             	public void actionPerformed(ActionEvent arg0)
             	{
             		clientes_morosos = new Deudores();
             		clientes_morosos.setVisible(false);
             	    jDesktopPane.add(clientes_morosos);
             	   clientes_morosos.setVisible(true);
             	 
             	}
            });
         }
    
         
         
   }
   
 
}
