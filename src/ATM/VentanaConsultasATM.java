package ATM;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;
import com.mysql.jdbc.Connection;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaConsultasATM extends javax.swing.JFrame 
{

	private int nroTarjeta;
	private ATMUI ventana;
	private Connection conexionBD=null;
	private JLabel consultaSaldo, lblDesde, lblHasta;
	private JFormattedTextField txtDesde,txtHasta;
	
	public VentanaConsultasATM(int nroTarjeta,ATMUI v)
	{
		super();
		ventana=v;
		setResizable(true);
		setSize(600, 300);
		
		//setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//Para que vuelva a aparecer la ventana anterior cuando cierro esta ventana.
		addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                ventana.setVisible(true);
                e.getWindow().dispose();
            }
        });
		
		setTitle("TRANSACCIONES");
		this.nroTarjeta=nroTarjeta;
		
		conectarBD();
		initGUI();
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
		
		getContentPane().setBackground(new Color(255, 222, 173));
	
		//saldo
		consultaSaldo = new JLabel("Consulta de Saldo");
        consultaSaldo.setBounds(10, 11, 172, 35);
		consultaSaldo.setHorizontalAlignment(SwingConstants.LEFT);
		
		String saldo = getSaldo();
		getContentPane().setLayout(null);
		
		consultaSaldo.setText(consultaSaldo.getText()+" : "+saldo);
		getContentPane().add(consultaSaldo);
		
		JButton btnMovimientosPorPeriodo = new JButton("Movimientos por Per\u00EDodo");
		btnMovimientosPorPeriodo.setBounds(11, 189, 171, 23);
		getContentPane().add(btnMovimientosPorPeriodo);
		
		JButton btnltimosMovimientos = new JButton("\u00DAltimos Movimientos");
		btnltimosMovimientos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				new UltimosMovimientos(nroTarjeta);
			}
		});
		btnltimosMovimientos.setBounds(10, 45, 171, 23);
		getContentPane().add(btnltimosMovimientos);
		
		lblDesde = new JLabel("Desde:");
		lblDesde.setBounds(10, 129, 42, 23);
		getContentPane().add(lblDesde);
		
		try 
		{
			
			txtDesde = new JFormattedTextField(new MaskFormatter("##'/##'/####"));
			getContentPane().add(txtDesde);
			txtDesde.setBounds(62, 129, 171, 23);
			txtHasta = new JFormattedTextField(new MaskFormatter("##'/##'/####"));
			getContentPane().add(txtHasta);
			txtHasta.setBounds(62, 155, 171, 23);
			
			btnMovimientosPorPeriodo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				if(validar())
					new MovimientosPeriodo(nroTarjeta,txtDesde.getText(),txtHasta.getText());
			}
			});
			
		} 
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(10, 155, 42, 23);
		getContentPane().add(lblHasta);
		
	}
	
	private boolean validar()
	{
		String mensajeError = null;
	       if (this.txtHasta.getText().isEmpty())
	       {
	          mensajeError = "Debe ingresar un valor para el campo 'Hasta'.";
	       }
	       else if (this.txtDesde.getText().isEmpty())
	       {
	          mensajeError = "Debe ingresar un valor para el campo 'Desde'.";
	       }
	       else if (! Fechas.validar(this.txtDesde.getText().trim()))
	       {
	          mensajeError = "En el campo 'Desde' debe ingresar un valor con el formato dd/mm/aaaa.";
	       }
	       else if (! Fechas.validar(this.txtHasta.getText().trim()))
	       {
	          mensajeError = "En el campo 'Hasta' debe ingresar un valor con el formato dd/mm/aaaa.";
	       }

	       if (mensajeError != null)
	       {
	          JOptionPane.showMessageDialog(this,
	                                        mensajeError,
	                                        "Error",
	                                        JOptionPane.ERROR_MESSAGE);
	          return false;
	       }
	       return true;
	}
	
	private String getSaldo()
	{
		BigDecimal saldo;
		try 
		{
			
			Statement stmt = this.conexionBD.createStatement();
			//hago la consulta
			String query =  "SELECT saldo " + 
	    	   				"FROM trans_cajas_ahorro tc , (SELECT nro_ca FROM tarjeta where nro_tarjeta="+nroTarjeta+") t "+
	    	   				"WHERE tc.nro_ca=t.nro_ca "+
	    	   				"GROUP BY tc.nro_ca";
			ResultSet resultado = stmt.executeQuery(query);
			
			//si llegué hasta acá es porque existe la tarjeta entonces la consulta no me va a dar vacía nunca.
			if(resultado.next())
			{
				saldo = resultado.getBigDecimal("saldo");
				return "$"+saldo;
			}
			
			return "$"+0;
				
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "$"+0;
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
}
