package Prestamo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import com.mysql.jdbc.Connection;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;


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

public class pago_cuota extends javax.swing.JInternalFrame  
{
   
   private JDesktopPane jDesktopPane2;
   private JScrollPane scroll;
   private JTextField tipodoc;
   private JTextField nrodoc;
   private JLabel tipodoc_lab;
   private JLabel nrodoc_lab; 
   private JButton ingresar;
   protected Connection conexionBD = null;
   private String servidor = "localhost:3306";
   private String baseDatos = "banco";
   private String usuario = "admin";
   private String clave = "admin";
   private JRadioButton[] opciones;
   private JButton pagar;
   private String nro_prestamo;
  

   /**
   * Auto-generated main method to display this JFrame
   */
   public static void main(String[] args) 
   {
      SwingUtilities.invokeLater(new Runnable(){
         public void run() 
         {
           
			pago_cuota  inst = new pago_cuota();
            inst.setVisible(true);
         }
      });
   }
   
   public pago_cuota()
   {
      super();
      initGUI();    
   }
   
   private void initGUI() 
   {
      try 
      {
    	  setPreferredSize(new Dimension(1100, 600));
          this.setBounds(0, 0,1100, 600);
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.setBackground(Color.blue);
          this.setResizable(false);
          setVisible(true);
          BorderLayout thisLayout = new BorderLayout();
          this.setTitle("Creación de préstamo");
          getContentPane().setLayout(thisLayout);
          this.setClosable(true);
          this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
          this.setMaximizable(true);
         
          conectarBD();
      
          javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
      } 
      catch(Exception e) 
      {
         e.printStackTrace();
      }
      
      		
      
      		jDesktopPane2 = new JDesktopPane();
      		jDesktopPane2.setBackground(new Color(204, 153, 102));
      		jDesktopPane2.setSize(1100,800);
      	
      		scroll=new JScrollPane(jDesktopPane2);
      		scroll.setBackground(Color.red);
      		//scroll.add(jDesktopPane2);
      		
      		getContentPane().add(scroll, BorderLayout.CENTER);
      		
      		jDesktopPane2.setPreferredSize(new java.awt.Dimension(1000,1000));
      		jDesktopPane2.setEnabled(false);
      
      		tipodoc = new JTextField();
      		tipodoc.setBackground(new Color(255, 255, 224));
      		tipodoc.setBounds(110,50,100,20);
      		jDesktopPane2.add(tipodoc);
          
      		nrodoc = new JTextField();
      		nrodoc.setBounds(350, 50,100,20);
      		jDesktopPane2.add(nrodoc);
          
      		tipodoc_lab = new JLabel("Tipo doc Cliente");
      		tipodoc_lab.setForeground(new Color(255, 255, 224));
      		tipodoc_lab.setFont(new Font("Univers 45 Light", Font.PLAIN, 11));
      		tipodoc_lab.setBounds(20,50,100,30);
      		jDesktopPane2.add(tipodoc_lab);
      
      		nrodoc_lab = new JLabel("Número doc Cliente");
      		nrodoc_lab.setForeground(new Color(255, 255, 224));
      		nrodoc_lab.setFont(new Font("Univers 45 Light", Font.PLAIN, 11));
      		nrodoc_lab.setBounds(240,50,100,30);
      		jDesktopPane2.add(nrodoc_lab);
      		
      		ingresar=new JButton("ingresar");
      		ingresar.setBounds(500,50,100,20);
      		jDesktopPane2.add(ingresar);
      		
      	
      		ingresar.addActionListener(new ActionListener(){
             	public void actionPerformed(ActionEvent arg0)
             	{
             		try 
             		{
             			buscar_cuotas();
					} catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
             	 
             	}
            });
      		
      		pagar=new JButton("Pagar");
      		pagar.setBounds(500,200,100,20);
      		pagar.setVisible(false);
      		jDesktopPane2.add(pagar);
      		pagar.addActionListener(new ActionListener(){
             	public void actionPerformed(ActionEvent arg0)
             	{
             		for(int i=0;i<opciones.length;i++)
             		{
             			if(opciones[i].isSelected())
             			{
             				try {
								pagar_cuota(i+1);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
             			}
             		}
             		
             		JOptionPane.showMessageDialog(null, "Las cuotas fueron saldadas");
             	 
             	}
            });
      		
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
   
   
   public void buscar_cuotas() throws SQLException
   {

	   Statement stmt = this.conexionBD.createStatement();
	   int nroC=0;
	   
       String sql = "SELECT nro_cliente " + 
                    "FROM cliente " +
                    "WHERE nro_doc = "+ Integer.parseInt(nrodoc.getText())+" AND tipo_doc LIKE '%"+ tipodoc.getText()+"%' "; 
       
       ResultSet rs = stmt.executeQuery(sql);
       
       if(rs.next())
       {
    	   nroC=Integer.parseInt(rs.getString("nro_cliente"));
 
       }
       else
       {
    	   JOptionPane.showMessageDialog(null, "Cliente no válido");
       }
       String sql2= " SELECT C.nro_prestamo,nro_pago,fecha_venc,valor_cuota,fecha_pago"+
    		   		" FROM pago NATURAL JOIN (SELECT * FROM prestamo WHERE nro_cliente="+nroC+") C"+
    		   		" WHERE pago.fecha_pago is NULL ";
       
       ResultSet ra=stmt.executeQuery(sql2);
       
       ra.last();
	   int cantFilas = ra.getRow();
	   ra.beforeFirst();
	  
	   if(cantFilas==0)
	   {
		   JLabel mensaje=new JLabel("Cuotas adeudadas: 0 ");
		   jDesktopPane2.add(mensaje);
		   mensaje.setBounds(700,80,200,50);
		   mensaje.setForeground(Color.white);
	   }
	   else
	   {
		   pagar.setVisible(true);
		   opciones=new JRadioButton[cantFilas];
	       
	       int i=0;
	       int j=0;
		   while(ra.next())
		   {
			   if(i==0)
				   nro_prestamo=ra.getString("nro_prestamo");
			   opciones[i]=new JRadioButton("Nro préstamo:"+ra.getString("nro_prestamo")+
					   		", Nro pago:"+ra.getString("nro_pago")+", Fecha vencimiento:"+ra.getString("fecha_venc")+", Monto:"+ra.getString("valor_cuota"));
			   opciones[i].setBounds(600,j+18,450,15 );
			   opciones[i].setForeground(Color.white);
			   opciones[i].setBackground(new Color(204, 153, 102));
			   opciones[i].setVisible(true);
			   j=j+18;
			   jDesktopPane2.add(opciones[i]);
			   i++;
		   }
	   }
       
   }
   
   public void pagar_cuota(int nro_cuota) throws SQLException
   {
	   Statement stmt = this.conexionBD.createStatement();
	   Date myDate = new Date();
	   SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
	   String strDate = sm.format(myDate);
	  
	   String sql=" UPDATE pago"+
			   	  " SET fecha_pago= ' "+strDate+
			   	  " ' WHERE nro_pago="+nro_cuota+
			   	  " AND nro_prestamo="+nro_prestamo;
	   stmt.execute(sql);
	   stmt.close();
	  
	   
      
   }
}
