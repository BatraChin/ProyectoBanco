package Prestamo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.DatabaseMetaData;



import javax.swing.JOptionPane;
import javax.swing.JComboBox;

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

public class creacion_prestamo extends javax.swing.JInternalFrame  
{
  
   private JDesktopPane jDesktopPane2;
   private JTextField nrodoc;
   private JLabel tipodoc_lab;
   private JLabel nrodoc_lab; 
   private JButton ingresar;
   private JTextField monto;
   private JLabel monto_lab;
   private JLabel periodo_lab;
   private JTextField periodo;
   protected Connection conexionBD = null;
   private DatabaseMetaData banco; 
   private String driver ="com.mysql.jdbc.Driver";
   private String servidor = "localhost:3306";
   private String baseDatos = "banco";
   private String usuario = "admin";
   private String clave = "admin";
   private String monto_superior;
   private String monto_inferior;
   private String monto_pedido;
   private String periodos[];
   private String valor_periodo;
   private String tasa;
   private static PrestamoUI principal;
   private int nroC = 0;
   private JLabel datos;
   private JComboBox comboBox;
   private JLabel label;
   private JComboBox comboPeriodo;
   private JButton btnIngresarMonto;
   Date myDate ;
   String strDate;
   private JButton btnPeriodo;

   /**
   * Auto-generated main method to display this JFrame
   */
   public static void main(String[] args) 
   {
      SwingUtilities.invokeLater(new Runnable(){
         public void run() 
         {
           
			creacion_prestamo  inst = new creacion_prestamo(principal);
            inst.setVisible(true);
         }
      });
   }
   
   public creacion_prestamo(PrestamoUI principal) 
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
      		getContentPane().add(jDesktopPane2, BorderLayout.CENTER);
      		jDesktopPane2.setPreferredSize(new java.awt.Dimension(1100,700));
      		jDesktopPane2.setEnabled(false);
          
      		nrodoc = new JTextField();
      		nrodoc.setBounds(421, 116,100,20);
      		jDesktopPane2.add(nrodoc);
          
      		tipodoc_lab = new JLabel("Tipo doc Cliente");
      		tipodoc_lab.setForeground(new Color(255, 255, 224));
      		tipodoc_lab.setFont(new Font("Tahoma", Font.PLAIN, 12));
      		tipodoc_lab.setBounds(311,69,100,30);
      		jDesktopPane2.add(tipodoc_lab);
      
      		nrodoc_lab = new JLabel("Número doc Cliente");
      		nrodoc_lab.setForeground(new Color(255, 255, 224));
      		nrodoc_lab.setFont(new Font("Tahoma", Font.PLAIN, 12));
      		nrodoc_lab.setBounds(297,110,114,30);
      		jDesktopPane2.add(nrodoc_lab);
      		
      		periodo_lab = new JLabel("Período");
      		periodo_lab.setFont(new Font("Tahoma", Font.PLAIN, 12));
      		periodo_lab.setForeground(new Color(255, 255, 224));
      	    periodo_lab.setBounds(343,269,68,20);
      	    periodo_lab.setVisible(true);
      	    jDesktopPane2.add(periodo_lab);
      	    
    	    datos=new JLabel("");
    	    datos.setVisible(false);
    	    datos.setForeground(new Color(255, 255, 224));
    	    datos.setBounds(50,339,672,220);
    	    jDesktopPane2.add(datos);

      		monto = new JTextField();
      		monto.setEnabled(false);
      		monto.setBounds(421,206,100,20);
      		monto.setVisible(true);
      		jDesktopPane2.add(monto);
      		
      		
      		monto_lab=new JLabel("Monto");
      		monto_lab.setFont(new Font("Tahoma", Font.PLAIN, 12));
      		monto_lab.setForeground(new Color(255, 255, 224));
      		monto_lab.setBounds(343,205,68,20);
      		monto_lab.setVisible(false);
      		jDesktopPane2.add(monto_lab);
      		
      		ingresar= new JButton("Ingresar cliente");
      		ingresar.setBounds(421,147,121,20);
      		jDesktopPane2.add(ingresar);
      		
      		comboBox = new JComboBox();
      		comboBox.setMaximumRowCount(2);
      		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
      		comboBox.setBounds(421, 74, 100, 20);
      		comboBox.addItem("LE");
      		comboBox.addItem("DNI");
      		jDesktopPane2.add(comboBox);
      		
      		label = new JLabel("");
      		label.setBounds(531, 206, 258, 14);
      		label.setVisible(true);
      		jDesktopPane2.add(label);
      		
      		btnIngresarMonto = new JButton("Ingresar monto");
      		btnIngresarMonto.setEnabled(false);
      		btnIngresarMonto.setBounds(421, 236, 121, 23);
      		btnIngresarMonto.addActionListener(new ActionListener(){
             	public void actionPerformed(ActionEvent arg0)
             	{
             		try 
             		{
             			monto_pedido= monto.getText();
             			if((Double.parseDouble(monto_pedido)>Double.parseDouble(monto_superior)) || 
								(Double.parseDouble(monto_pedido)<Double.parseDouble(monto_inferior)))
						{
							JOptionPane.showMessageDialog(null, "El monto ingresado es incorrecto.");
							btnIngresarMonto.setEnabled(true);
							monto.setText("");
						}
             			else
						{
             			periodo_prestamo();
						int i=0;
						String periodos_mostrar="";
						while(i<periodos.length)
						{
							periodos_mostrar=periodos_mostrar+" "+periodos[i];
							i++;
						}
						
						/**JOptionPane.showMessageDialog(null, "Ingrese período a pagar\n. El período debe corresponder a:"+
								periodos_mostrar+ " meses.");*/
						comboPeriodo.setEnabled(true);
						for(Object objeto : periodos) {
						    comboPeriodo.addItem(objeto.toString());
						}
						monto.setEnabled(false);
						btnIngresarMonto.setEnabled(false);
						btnPeriodo.setEnabled(true);
	
						}
             		} 
             		catch (HeadlessException|SQLException e)
             		{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
             	 
             	}
            });
      		jDesktopPane2.add(btnIngresarMonto);
      		
      		comboPeriodo = new JComboBox();
      		comboPeriodo.setBounds(421, 270, 100, 20);
      		comboPeriodo.setEnabled(false);
      		
      		jDesktopPane2.add(comboPeriodo);
      		
      		btnPeriodo = new JButton("Ingresar periodo");
      		btnPeriodo.setEnabled(false);
      		btnPeriodo.setBounds(421, 301, 121, 23);
      		
      		btnPeriodo.addActionListener(new ActionListener() {
      	
      			public void actionPerformed(ActionEvent arg0)
      			{
      				      				
     				try 
             		{
						valor_periodo=comboPeriodo.getSelectedItem().toString();
						
						
						boolean encontre=false;
						int j=0;
						
						/**while((j<periodos.length) && !encontre)
						{
							encontre=Integer.parseInt(periodos[j])==Integer.parseInt(valor_periodo);
							j++;
						}
						if((Double.parseDouble(monto_pedido)>Double.parseDouble(monto_superior)) || 
								(Double.parseDouble(monto_pedido)<Double.parseDouble(monto_inferior)))
						{
							JOptionPane.showMessageDialog(null, "El monto ingresado es incorrecto.");
							btnIngresarMonto.setEnabled(true);
							monto.setText("");
						}
						else
						{*/
							tasa_interes();
							crear_Prestamo();
							JOptionPane.showMessageDialog(null, "El préstamo se efectuó correctamente.");
							JDialog.setDefaultLookAndFeelDecorated(true);
							    int response = JOptionPane.showConfirmDialog(null, "Desea realizar otra operación?", "Confirmar",
							        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							    if (response == JOptionPane.NO_OPTION) {
							    	setClosed(true);
							    	dispose();
							    } else if (response == JOptionPane.YES_OPTION) {
							    	monto.setText("");
							    	monto.setEnabled(false);
									comboPeriodo.removeAll();
									comboPeriodo.setEnabled(false);
									nrodoc.setText("");
									nrodoc.setEnabled(true);
									btnPeriodo.setEnabled(false);
									
							    } else if (response == JOptionPane.CLOSED_OPTION) {
							      
							    }
							
						//}
		
					} 
             		catch (HeadlessException | SQLException e)
             		{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (PropertyVetoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
      				
      				
      				
      			}
      		}     				
      				
      		);
      		
      		jDesktopPane2.add(btnPeriodo);
      		ingresar.addActionListener(new ActionListener(){
             	public void actionPerformed(ActionEvent arg0)
             	{
             		try {
             			int j=authenticate();
						if(j==0)
						{
							JOptionPane.showMessageDialog(null, "YA TIENE PRESTAMOS");
							setClosed(true);
						} 
						else
						{
							if(j==1)
							{
								tasa_prestamo();
								//JOptionPane.showMessageDialog(null, "Ingrese monto.\n El valor debe estar en el rango ["+
								//			monto_inferior+","+monto_superior+"]");
								label.setText("Valor entre ["+monto_inferior+","+monto_superior+"]");
								ingresar.setEnabled(false);
								btnIngresarMonto.setEnabled(true);
								comboBox.setEnabled(false);
								nrodoc.setEnabled(false);
								monto.setEnabled(true);
							}
							else
							{
								
							}					
						}
					} catch (HeadlessException | SQLException | PropertyVetoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
             	 
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
           
            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos;
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
   
   public int authenticate() throws SQLException 
   {
	   Statement stmt = this.conexionBD.createStatement();
	   
       String sql = "SELECT nro_cliente " + 
                    "FROM cliente " +
                    "WHERE nro_doc = "+ Integer.parseInt(nrodoc.getText())+" AND tipo_doc LIKE '%"+ comboBox.getSelectedItem().toString()+"%' "; 
       
       ResultSet rs = stmt.executeQuery(sql);
       
       
       if (rs.next ()) 
       {
           nroC = Integer.parseInt(rs.getString ("nro_cliente"));
           
           String sql2=" SELECT nro_prestamo" +
    		   	   " FROM prestamo"+
    		   	   " WHERE nro_cliente="+nroC;
           ResultSet ra=stmt.executeQuery(sql2);
           
           if (ra.next())
           {
        	   return 0;
           }
           else
        	   return 1;
           
       }

       else
       {
    	   JOptionPane.showMessageDialog(null, "Cliente no válido");
    	   nrodoc.setText("");
    	   
    	   return 2;
       }
   
   }
   
   public void tasa_prestamo() throws SQLException
   {
	   Statement stmt = this.conexionBD.createStatement();
	   
       String sql = "SELECT max(monto_sup) AS 'monto_sup'"+
       				"FROM tasa_prestamo";
       
       ResultSet rs = stmt.executeQuery(sql);
       
       
       while (rs.next ()) 
       {
            monto_superior=rs.getString ("monto_sup");
           
       }
       
       

       String sql2 = "SELECT min(monto_inf) AS 'monto_inf'"+
       				"FROM tasa_prestamo";
       
       ResultSet rs2 = stmt.executeQuery(sql2);
       
       
       while (rs2.next ()) 
       {
            monto_inferior=rs2.getString ("monto_inf");
           
       }
       
      
   }
   
   public void periodo_prestamo() throws SQLException
   {
	   Statement stmt= this.conexionBD.createStatement();
	   String sql=" SELECT DISTINCT periodo"+
			   	  " FROM tasa_prestamo";
	   ResultSet rs = stmt.executeQuery(sql);
	   
	   rs.last();
	   int cantFilas = rs.getRow();
	   rs.beforeFirst();
	   periodos=new String[cantFilas];
	  
	   int i=0;
	   while(rs.next())
	   {
		   periodos[i]=rs.getString("periodo");
		   i++;
	   }	   
   }
   
   public void tasa_interes() throws SQLException
   {
	   Statement stmt= this.conexionBD.createStatement();
	   
	  
	   String sql=" SELECT tasa"+
			   	  " FROM tasa_prestamo"+
			   	  " WHERE (monto_inf < " + Double.parseDouble(monto_pedido)+
			   	  " OR monto_inf = "+Double.parseDouble(monto_pedido) +
			   	  " ) AND (monto_sup > "+Double.parseDouble(monto_pedido)+
			   	  " OR monto_sup = "+ Double.parseDouble(monto_pedido) +
			   	  " ) AND periodo = " + Integer.parseInt(valor_periodo);  

	   ResultSet rs = stmt.executeQuery(sql);
	   
	   while (rs.next ()) 
       {
            tasa=rs.getString("tasa");
           
       }
	   
	  
   }
   
   public void crear_Prestamo() throws SQLException
   {
	   double interes=(Double.parseDouble(monto_pedido)*Double.parseDouble(tasa)*Double.parseDouble(valor_periodo))/1200;
	   double valorCuota=(Double.parseDouble(monto_pedido)+interes)/Double.parseDouble(valor_periodo);
	   myDate = new Date();
	   SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
	   strDate = sm.format(myDate);
	  
	   
	   
	  
	   
	   Statement stmt= this.conexionBD.createStatement();
	   
	   String sql = "INSERT INTO prestamo(nro_prestamo,fecha,cant_meses,monto,tasa_interes,interes,valor_cuota,legajo,nro_cliente) " +
	                      "VALUES (NULL, '" + strDate +"', '"+Integer.parseInt(valor_periodo) +"', '"+ Double.parseDouble(monto_pedido)+
	                      "', '"+Double.parseDouble(tasa)+ "', '"+interes+"', '"+valorCuota+"', '"+Integer.parseInt(principal.loguearEmp.getText())+"', '"+nroC+"')";
			
	   stmt.execute(sql);
	   
	   String sql2=" SELECT nro_prestamo" +
			   		" FROM prestamo"+
			   			" WHERE nro_cliente="+nroC;
	   
	  
	   
	   ResultSet rs = stmt.executeQuery(sql2);
	   if(rs.next())
	   {
		   crear_Cuotas(valorCuota,Integer.parseInt(rs.getString("nro_prestamo")));
	   }
	   
	   
       String sql3=" SELECT *"+
    		   	   " FROM prestamo NATURAL JOIN cliente"+
    		   	   " WHERE nro_cliente="+nroC;
			   
       ResultSet ra=stmt.executeQuery(sql3);
       
       if(ra.next())
       {
    	  
    			
    	   datos.setText("<html>número préstamo:"+ra.getString("nro_prestamo")+" <br> fecha: "+ra.getString("fecha")+" <br> cantidad Meses: "+
  		   		 ra.getString("cant_meses")+" <br> Monto:"+ra.getString("monto")+" <br> Interes: "+ra.getString("interes")+" <br> Nombre Cliente:"+
  		   		ra.getString("nombre")+" <br> Apellido:"+ra.getString("apellido")+"</html>");
    	   
    	   datos.setVisible(true);
       }
       
       
   }
   
   public void crear_Cuotas(double valorCuota,int nroPrestamo) throws SQLException
   {
	   
	   
	   String strDate2=""+strDate;

	   int j=1;
	   int i;
	   for(i=1;i<=Integer.parseInt(valor_periodo);i++)
	   {
		   Statement stmt= this.conexionBD.createStatement();
		   
		   String sql2="SELECT DATE_ADD('"+strDate2+"',INTERVAL 1 MONTH) AS 'fecha_venc'";
		  
		   ResultSet rs = stmt.executeQuery(sql2);
		   
		   if(rs.next())
		   {
			   strDate2=""+rs.getString("fecha_venc");
			  
		   }
		   
		   String sql = "INSERT INTO pago(nro_prestamo,nro_pago,fecha_venc,fecha_pago) " +
                   "VALUES ( '"+ nroPrestamo+"', '"+ i +"', '" +strDate2+ "', NULL)";
		   		 
		   stmt.execute(sql);
		   
	   }
	   
	   
	   
	   
   }
}
