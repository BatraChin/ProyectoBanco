package ATM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import com.mysql.jdbc.Connection;
import java.awt.Dimension;

public class ATMUI extends javax.swing.JFrame
{
	
	private Connection conexionBD=null;
	private Fondo jDesktopPane;
	private JLabel mensajeTarjeta,mensajePin;
	private JPasswordField passwordT,passwordP;
	private VentanaConsultasATM ventanaConsultasATM;
	private JFrame inicio;
	
	
	public ATMUI(JFrame ini)
	{
		super();
		inicio=ini;
		getContentPane().setBackground(Color.WHITE);
		conectarBD();
		initGUI();
	}
	
	private void initGUI()
	{
		try
		{
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
			this.setTitle("Banco");
	  //      this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	        this.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    inicio.setEnabled(true);
                   
                }
            });
	        
	        this.addComponentListener(new ComponentAdapter() {
	            public void componentHidden(ComponentEvent evt) 
	            {
	               thisComponentHidden(evt);
	            }
	            public void componentShown(ComponentEvent evt)
	            {
	               thisComponentShown(evt);
	            }

	         });
	        
	        jDesktopPane = new Fondo();
	        jDesktopPane.setBackground(Color.WHITE);
	        getContentPane().add(jDesktopPane, BorderLayout.CENTER);
	        jDesktopPane.setPreferredSize(new Dimension(500, 350));
	        jDesktopPane.setEnabled(false);
	        
	        mensajeTarjeta=new JLabel("Ingrese Número de Tarjeta");
	        mensajeTarjeta.setSize(200,200);
	        mensajeTarjeta.setBounds(44,30,140,30);
	        mensajeTarjeta.setForeground(Color.black);
	        mensajeTarjeta.setBackground(Color.white);
	        jDesktopPane.add(mensajeTarjeta);
	        
	        passwordT = new JPasswordField();
	        passwordT.setBounds(44,71,200,30);
	        jDesktopPane.add(passwordT);
	           	
	        mensajePin = new JLabel("Ingrese PIN");
	        mensajePin.setForeground(Color.black);
	        mensajePin.setBackground(Color.white);
	  	    mensajePin.setBounds(44, 123, 140, 30);
	  	    jDesktopPane.add(mensajePin);
	            
	        passwordP = new JPasswordField();
	        passwordP.setBounds(44,164,200,30);
	        jDesktopPane.add(passwordP);
	    
	        
	        passwordP.addActionListener(new ActionListener()
	        {
	        	public void actionPerformed(ActionEvent arg0)
	        	{
	        		
	        		if (!authenticate())
	        		{
	        			
	        			JOptionPane.showMessageDialog(null, "Bienvenido,ya puede realizar las consultas");
	        			
	        			ventanaConsultasATM.setVisible(true);
	            		passwordT.setText("");
	            		passwordP.setText("");
	          		}
	        		else
	          		{
	          			
	            		JOptionPane.showMessageDialog(null, "Vuelve a ingresar tu PIN o Nro. Tarjeta \n "
	            			   		+ "Por favor, asegúrate de que el bloqueo de mayúsculas no está activado e inténtalo de nuevo.");
	            		passwordT.setText("");
	            		passwordP.setText("");
	          		 }
	            }
	        });
	        this.setSize(306, 438);
	        pack(); 
	      }
		catch (Exception e)
		{
	         e.printStackTrace();
	    }
	}
	
	public static String getMD5(String input) 
	   {
		   try 
		   {
			   MessageDigest md = MessageDigest.getInstance("MD5");
			   byte[] messageDigest = md.digest(input.getBytes());
			   BigInteger number = new BigInteger(1, messageDigest);
			   String hashtext = number.toString(16);

		   while (hashtext.length() < 32) 
		   {
			   hashtext = "0" + hashtext;
		   }
		   return hashtext;
		   }
		   catch (NoSuchAlgorithmException e) 
		   {
			   throw new RuntimeException(e);
		   }
	}
	
	private boolean authenticate()
	{
		//INGRESO NRO TARJETA Y PIN
		//para controlar nro tarjeta y nro pin necesito acceder a la tabla ...
		int cant=0;
		try
		{
			Statement stmt = this.conexionBD.createStatement();
			// se prepara el string SQL de la consulta
		    
			String sql = "SELECT count(nro_tarjeta) as cant " + 
						"FROM tarjeta "+
		    		   	"WHERE nro_tarjeta="+Integer.parseInt(passwordT.getText())+" AND pin='"+getMD5(passwordP.getText())+"'";
		    

			// se ejecuta la sentencia y se recibe un resultset
		    ResultSet resultado = stmt.executeQuery(sql);
		       
			System.out.println(cant);		    
		    if(resultado.next())
		    {
		    	cant= resultado.getInt("cant");
		    	ventanaConsultasATM = new VentanaConsultasATM(Integer.parseInt(passwordT.getText()),this);
		  		this.setVisible(false);
		    }
		       
		    return (cant==0);
		   
		 }
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (cant==0);
	}
	
	private void thisComponentShown(ComponentEvent evt) 
	{
	      this.conectarBD();
	     
	}
	   
	private void thisComponentHidden(ComponentEvent evt) 
	{
	      this.desconectarBD();
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
	        	 
	        	 String servidor = "localhost:3306";
	        	 String baseDatos = "banco";
	        	 String usuario = "atm";
	        	 String clave = "atm";
	        	 String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos;
	        	 //se intenta establecer la conección
	        	 this.conexionBD = (Connection) DriverManager.getConnection(uriConexion, usuario, clave);
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
	
	private void desconectarBD()
	{
		if (this.conexionBD != null)
		{
	         try
	         {
	        	 
	            this.conexionBD.close();
	            this.conexionBD = null;
	         }
	         catch (SQLException ex)
	         {
	        	 
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }
		}
	}

}
