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
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class VentanaConsultasATM extends javax.swing.JFrame 
{

	private int nroTarjeta;
	private ATMUI ventana;
	private Connection conexionBD=null;
	private JLabel consultaSaldo, lblDesde, lblHasta;
	private JFormattedTextField txtDesde,txtHasta;
	private JTextField lblMonto;
	private JTextField lblDestino;
	
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
		
		lblMonto = new JTextField();
		lblMonto.setToolTipText("Monto");
		lblMonto.setBounds(375, 46, 86, 20);
		getContentPane().add(lblMonto);
		lblMonto.setColumns(10);
		
		JLabel txtMonto = new JLabel("Monto:");
		txtMonto.setBounds(283, 44, 55, 25);
		getContentPane().add(txtMonto);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"[Elegir Acci\u00F3n]", "Extraer", "Transferir"}));
		comboBox.setMaximumRowCount(2);
		comboBox.setBounds(375, 18, 163, 20);
		getContentPane().add(comboBox);
		
		lblDestino = new JTextField();
		lblDestino.setBounds(375, 77, 86, 20);
		getContentPane().add(lblDestino);
		lblDestino.setColumns(10);
		
		JLabel txtCuentaDestino = new JLabel("Cuenta Destino:");
		txtCuentaDestino.setBounds(246, 80, 92, 25);
		getContentPane().add(txtCuentaDestino);
		
		JLabel lblAcciones = new JLabel("Acciones:");
		lblAcciones.setBounds(273, 16, 92, 25);
		getContentPane().add(lblAcciones);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					Statement stmt;
					
					stmt = conexionBD.createStatement();
				int monto;
				int destino;
				String sql="";
				String texto=comboBox.getSelectedItem().toString();
				monto= Integer.parseInt(lblMonto.getText());
				String nro_cli = "select distinct nro_cliente from Tarjeta where nro_tarjeta = "+nroTarjeta;
				ResultSet resultado = stmt.executeQuery(nro_cli);
				resultado.next();
				int nro_cliente=resultado.getInt("nro_cliente");

				String nro_origen_sql = "select distinct nro_ca from Tarjeta where nro_tarjeta = "+nroTarjeta;
				ResultSet resultado2 = stmt.executeQuery(nro_origen_sql);
				resultado2.next();
				int nro_origen = resultado2.getInt("nro_ca");
				if(texto.equals("Transferir")){
					destino= Integer.parseInt(lblDestino.getText());
					sql="CALL transferir ("+monto+","+nro_origen+","+destino+","+55567+","+nro_cliente+");";

				}
				else if (texto.equals("Extraer")) {
					
					sql="CALL extraer (55567,"+nro_cliente+","+nro_origen+","+monto+");";
				}		
			
				

				
					stmt.executeQuery(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				comboBox.setSelectedIndex(0);
				
				lblMonto.setVisible(false);
				lblDestino.setVisible(false);
				txtCuentaDestino.setVisible(false);
				txtMonto.setVisible(false);

				lblMonto.setEnabled(false);
				lblDestino.setEnabled(false);
				txtCuentaDestino.setEnabled(false);
				txtMonto.setEnabled(false);
				
				
			}
		});
		btnConfirmar.setBounds(375, 129, 89, 23);
		getContentPane().add(btnConfirmar);
		
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
