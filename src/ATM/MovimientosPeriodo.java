package ATM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;

public class MovimientosPeriodo extends javax.swing.JFrame
{

	private int nroTarjeta;
	private String fDesde,fHasta;
	private Connection conexionBD=null;
	private JTable tabla;
	
	public MovimientosPeriodo(int nroTarjeta, String desde, String hasta)
	{
		super();
		setTitle("Movimientos por Periodos");
		getContentPane().setBackground(Color.WHITE);
		setSize(600, 300);
		conectarBD();
		this.nroTarjeta=nroTarjeta;
		this.setLocationRelativeTo(null);
		this.fDesde=desde;
		this.fHasta=hasta;
		
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
         
		JScrollPane scrTabla = new JScrollPane();
        getContentPane().add(scrTabla, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		tabla = new JTable();
		scrTabla.setViewportView(tabla);
		tabla.setAutoCreateRowSorter(true);
		
		setVisible(true);
		
		java.util.Date desdeDia = Fechas.convertirStringADate(fDesde);
		java.util.Date hastaDia = Fechas.convertirStringADate(fHasta);
		
		String desde = Fechas.convertirDateAStringDB(desdeDia);
		String hasta = Fechas.convertirDateAStringDB(hastaDia);
		
		generarTabla("SELECT tca.fecha, tca.hora, tca.tipo, tca.monto, tca.cod_caja, tca.destino "+
				"FROM trans_cajas_ahorro as tca, tarjeta as t "+
				"WHERE (tca.nro_ca = t.nro_ca and t.nro_tarjeta = "+nroTarjeta+") and (tca.fecha >= '"+desde+"') and (tca.fecha <= '"+hasta+
				"') ORDER BY tca.fecha ;", tabla);
		
	}
	
	private void generarTabla(String consulta, JTable tabla)
	{
		boolean flag = false;
		try
		{
			Statement stmt = this.conexionBD.createStatement();

			// se ejecuta la sentencia y se recibe un resultset
			ResultSet rs = stmt.executeQuery(consulta);
        
			// se adapta el modelo de la tabla según los metadatos del resultado
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			ConsultasTableModel modelo = new ConsultasTableModel();
			for (int i = 1; i <= rsmd.getColumnCount(); i++)
			{
				String columnaNombre = rsmd.getColumnLabel(i);
				Class columnaTipo = Class.forName("java.lang.String");

           
				boolean columnaEditable = false;
				modelo.agregarColumna(columnaNombre, columnaTipo, columnaEditable);
			}
			tabla.setModel(modelo);
        
			// se recorre el resulset y se actualiza la tabla en pantalla
			while (rs.next())
			{
				Vector<Object> fila = new Vector<Object>();  
				for (int j = 1; j <= rsmd.getColumnCount(); j++)
				{
					if (j == 3) 
					{
						String tipo = (String) rs.getObject(j);
						if (!(tipo.equals("Deposito")))
							flag = true;
							fila.add(tipo);
					
					}
				
					else if (j == 4 && flag)
					{
						BigDecimal monto = (BigDecimal) rs.getObject(j);
						//SI NO ES UN DEPOSITO, EL MONTO DEBE SER NEGATIVO
						if (!(  (String) rs.getObject(j-1)  ).equals("deposito"))
							monto = monto.multiply(new BigDecimal("-1"));
						fila.add(monto);
						flag = false;

					}
										
					else 
					{   
						fila.add(rs.getObject(j));
					}
				}
				((ConsultasTableModel) tabla.getModel()).addRow(fila);
			}

			rs.close();
			stmt.close();
		}
	
		catch (SQLException ex)
		{
			// en caso de error, se muestra la causa en la consola
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                                      ex.getMessage() + "\n", 
                                      "Error al ejecutar la consulta.",
                                      JOptionPane.ERROR_MESSAGE);
        
		}
		catch (ClassNotFoundException e)
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
	
	class ConsultasTableModel extends DefaultTableModel
	{
	      Vector<Class> tipos = null; 
	      Vector<Boolean> sonEditables = new Vector<Boolean>();
	      
	      public ConsultasTableModel()
	      {
	         super();
	         this.tipos = new Vector<Class>(); 
	         this.sonEditables = new Vector<Boolean>();
	      }

	      public void agregarColumna(String nombre, Class tipo, boolean esEditable)
	      {
	         this.addColumn(nombre);
	         this.tipos.add(tipo);
	         this.sonEditables.add(esEditable);
	      }

	      public Class getColumnClass(int columnIndex)
	      {
	         return this.tipos.get(columnIndex);
	      }

	      public boolean isCellEditable(int rowIndex, int columnIndex)
	      {
	         return this.sonEditables.get(columnIndex);
	      }
	  }
	
	
	
	
	
}
