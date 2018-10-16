package Prestamo;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.jdbc.Connection;

import ATM.Fechas;

public class Deudores extends javax.swing.JInternalFrame {
	
	private Connection conexionBD = null;
	private JTable tabla;
	private String fechaHoy;
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				Deudores inst = new Deudores();
	            inst.setVisible(true);
	    		inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        }
	    });
		
	}
	
	public Deudores()
	{
		
        conectarBD();
        initGUI();
	}
	
	private void initGUI(){
		try{
	         javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
	    }catch(Exception e){
	         e.printStackTrace();
	    }
	
		setPreferredSize(new Dimension(1070, 600));

        this.setBounds(0, 0,1070, 600);
        this.setBackground(Color.blue);
        this.setResizable(false);
        setVisible(true);
        BorderLayout thisLayout = new BorderLayout();
        this.setTitle("Clientes Morosos");
        this.setClosable(true);
        getContentPane().setLayout(thisLayout);
        this.setMaximizable(true);
		
		
		JScrollPane scrTabla = new JScrollPane();
		getContentPane().add(scrTabla, BorderLayout.CENTER);
	
		java.util.Date hoy = new Date();
		fechaHoy = Fechas.convertirDateAStringDB(hoy);
		
		
		@SuppressWarnings("serial")
		TableModel morosoModel =  // se crea un modelo de tabla ClienteModel 
                new DefaultTableModel  // extendiendo el modelo DefalutTableModel
                (				  
                   new String[][] {},
                   new String[] {"Numero Cliente", "Tipo Documento", "Numero Documento", "Nombre", "Apellido", "Numero de Prestamo", "Monto", "Cantidad de Meses", "Valor", "Cantidad de Cuotas Atrasadas"}
                )
                {                      // con una clase anónima 
          	     // define la clase java asociada a cada columna de la tabla
          	     Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class };
          	    // define si una columna es editable
                   boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false, false, false };
                    
                  // recupera la clase java de cada columna de la tabla
                   public Class getColumnClass(int columnIndex) 
                   {
                      return types[columnIndex];
                   }
                 // determina si una celda es editable
                   public boolean isCellEditable(int rowIndex, int columnIndex) 
                   {
                      return canEdit[columnIndex];
                   }
                };
        
        
       	tabla = new JTable();
       	tabla.setModel(morosoModel);
		scrTabla.setViewportView(tabla);
		tabla.setAutoCreateRowSorter(true);
		refrescarTabla();
	
	}
	
	
	private void refrescarTabla(){
	      try
	      {
	    	 // se crea una sentencia o comando jdbc para realizar la consulta 
	    	 // a partir de la conexion establecida (conexionBD)
	         Statement stmt = this.conexionBD.createStatement();

	         // se prepara el string SQL de la consulta
	         String sql = "SELECT pr.nro_cliente, c.tipo_doc, c.nro_doc, c.nombre, c.apellido, p.nro_prestamo, pr.monto, pr.cant_meses, pr.valor_cuota, count(p.nro_prestamo) " +
	         		"FROM pago as p, prestamo as pr, cliente as c " +
	         		"WHERE fecha_pago IS NULL and fecha_venc < '"+fechaHoy+"' and p.nro_prestamo = pr.nro_prestamo and pr.nro_cliente = c.nro_cliente "+
					"GROUP BY nro_prestamo;";

	         // se ejecuta la sentencia y se recibe un resultset
	         ResultSet rs = stmt.executeQuery(sql);
	         // se recorre el resulset y se actualiza la tabla en pantalla
	         ((DefaultTableModel) this.tabla.getModel()).setRowCount(0);
	         int i = 0;
	         while (rs.next())
	         {
				int cant = rs.getInt("count(p.nro_prestamo)");
				if (cant > 1)
				{
					// agrega una fila al modelo de la tabla
					((DefaultTableModel) this.tabla.getModel()).setRowCount(i + 1);
					// se agregan a la tabla los datos correspondientes cada celda de la fila recuperada
					
					this.tabla.setValueAt(rs.getString("nro_cliente"), i, 0);
					this.tabla.setValueAt(rs.getString("tipo_doc"), i, 1);
					this.tabla.setValueAt(rs.getString("nro_doc"), i, 2);
					this.tabla.setValueAt(rs.getString("nombre"), i, 3);
					this.tabla.setValueAt(rs.getString("apellido"), i, 4);
					this.tabla.setValueAt(rs.getString("nro_prestamo"), i, 5);
					this.tabla.setValueAt(rs.getString("monto"), i, 6);
					this.tabla.setValueAt(rs.getString("cant_meses"), i, 7);
					this.tabla.setValueAt(rs.getString("valor_cuota"), i, 8);
					this.tabla.setValueAt(cant, i, 9);
					
					i++;
				}
	         }
	         
	         // se cierran los recursos utilizados para recuperar la memoria
	         // utilizada
	         rs.close();
	         stmt.close();
			 
	      }
	      catch (SQLException ex)
	      {
	         // en caso de error, se muestra la causa en la consola
	         System.out.println("SQLException: " + ex.getMessage());
	         System.out.println("SQLState: " + ex.getSQLState());
	         System.out.println("VendorError: " + ex.getErrorCode());
	      }
	   }
	
	private void conectarBD(){
		if (this.conexionBD == null){
			try{
				
				Class.forName("com.mysql.jdbc.Driver").newInstance();
	         }catch (Exception ex){
	        	 System.out.println(ex.getMessage());
	         }
	   
	         try{
	        	 
	        	 String servidor = "localhost:3306";
	        	 String baseDatos = "banco";
	        	 String usuario = "empleado";
	        	 String clave = "empleado";
	        	 String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos;
	        	 //se intenta establecer la conección
	        	 this.conexionBD = (Connection) DriverManager.getConnection(uriConexion, usuario, clave);
	         }catch (SQLException ex){
	        	 
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
