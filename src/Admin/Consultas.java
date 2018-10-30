package Admin;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Types;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.DatabaseMetaData;
import quick.dbtable.*;
import javax.swing.JTree;
import java.awt.Font;
import javax.swing.border.CompoundBorder;
import java.awt.FlowLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.text.BadLocationException;  

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
public class Consultas extends javax.swing.JInternalFrame 
{
   private JPanel pnlConsulta;
   private JTextArea txtConsulta;
   private JButton btnEjecutar;
   private DBTable tabla;    
   private JScrollPane scrConsulta;
   private JButton button;
   private JTree tree;
   private String driver ="com.mysql.jdbc.Driver";
   private String servidor = "localhost:3306";
   private String baseDatos = "banco";
   private String usuario = "admin";
   private String clave = "admin";
   private	Connection Connecticut;
   private	Statement sentencia;
   

	private JList<String> list;
	private JList<String> list_1;
	private DefaultListModel<String> DLM;
	private DefaultListModel<String> DLM_1;
 
   public Consultas() 
   {
      super();
      initGUI();
   }
   
   private void initGUI() 
   {
      try 
      {
         this.setBounds(0, 0,1100, 600);
         this.setResizable(false);
         setVisible(true);
         BorderLayout thisLayout = new BorderLayout();
         this.setTitle("Consultas (Utilizando DBTable)");
         getContentPane().setLayout(thisLayout);
         this.setClosable(true);
         this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
         this.setMaximizable(true);
         this.addComponentListener(new ComponentAdapter(){
            public void componentHidden(ComponentEvent evt) 
            {
               thisComponentHidden(evt);
            }
            public void componentShown(ComponentEvent evt)
            {
               thisComponentShown(evt);
            }
         });
         
         pnlConsulta = new JPanel();
         FlowLayout flowLayout = (FlowLayout) pnlConsulta.getLayout();
         flowLayout.setAlignment(FlowLayout.LEFT);
         flowLayout.setAlignOnBaseline(true);
         flowLayout.setHgap(4);
         pnlConsulta.setBackground(Color.LIGHT_GRAY);
         
         DLM = new DefaultListModel<String>();
		 list = new JList<String>(DLM);  
         
         
         getContentPane().add(pnlConsulta, BorderLayout.NORTH);
         {
        	 btnEjecutar = new JButton();
             btnEjecutar.setBackground(Color.DARK_GRAY);
             pnlConsulta.add(btnEjecutar);
             btnEjecutar.setText("Ejecutar");
             btnEjecutar.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent evt)
                  {
                     btnEjecutarActionPerformed(evt);
                  }
             });
         }
         {
        	 button = new JButton();
             button.setBackground(Color.DARK_GRAY);
             button.setText("Borrar");
             pnlConsulta.add(button);         
             button.addActionListener(new ActionListener(){
            	 public void actionPerformed(ActionEvent arg0)
            	 {
            		 txtConsulta.setText("");            			
            	 }
             });
         }	
         {
        	 txtConsulta = new JTextArea("");
             txtConsulta.setForeground(Color.WHITE);
             txtConsulta.setBackground(Color.DARK_GRAY);
             pnlConsulta.add(txtConsulta);
             txtConsulta.setTabSize(3);
             txtConsulta.setColumns(80);
             txtConsulta.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
             txtConsulta.setText("");
             txtConsulta.setFont(new java.awt.Font("Monospaced",0,12));
             txtConsulta.setRows(10);
         }
         {
             scrConsulta = new JScrollPane();
             pnlConsulta.add(scrConsulta);
         }
         {
        	 completarArbol();
        	 pnlConsulta.add(tree);
         }
         
         {
        	tabla = new DBTable();
        	tabla.setForeground(Color.LIGHT_GRAY);
        	tabla.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        	tabla.getTable().setBackground(Color.LIGHT_GRAY);
        	tabla.setBackground(Color.LIGHT_GRAY);
            getContentPane().add(tabla, BorderLayout.CENTER);           
            tabla.setEditable(false);       
         }
      } 
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
   public void mostrarColumnas() {
		DLM_1.clear();
		if(DLM.getSize()> 0) {         			
			String selected = (String) list.getSelectedValue();
			selected = "'"+selected+"'";
			Statement st = null;
			ResultSet rs = null;
			try {
				st = (Statement) tabla.getConnection().createStatement();
				rs = st.executeQuery("SELECT COLUMN_NAME FROM "
						+ "INFORMATION_SCHEMA.COLUMNS "
						+ "WHERE TABLE_SCHEMA='parquimetros' "
						+ "AND TABLE_NAME="+selected );
				boolean sig = rs.first();
				while(sig) {
					DLM_1.addElement(rs.getString(1));
					sig = rs.next();
				}
			} catch (SQLException ex) {
				salidaError(ex);
			}  finally {
				if(st != null) {
					try {
						st.close();
					} catch (SQLException ex) {
						salidaError(ex);
					}
				}
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException ex) {
						salidaError(ex);
					}
				}
			}         			        			
		}
	}
   private void completarArbol() throws SQLException 
   {
	   Connecticut = (Connection) DriverManager.getConnection ("jdbc:mysql://"+servidor+"/"+baseDatos,usuario,clave);
	   DatabaseMetaData md = (DatabaseMetaData) Connecticut.getMetaData();
	   ResultSet rs = md.getTables(null, null, "%", null);
	   
	   DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Tables");
	   DefaultTreeModel modelo = new DefaultTreeModel(raiz);
		       
	   tree = new JTree(modelo);
	   tree.setBorder(new CompoundBorder());
	   tree.setForeground(Color.BLACK);
	   tree.setFont(new Font("Univers 45 Light", Font.PLAIN, 11));
	   tree.setBackground(Color.WHITE);
	   while (rs.next()) 
	   {  	
		   DefaultMutableTreeNode NODO = new DefaultMutableTreeNode(""+rs.getString(3));    	
		   modelo.insertNodeInto(NODO,raiz, 0);
		   String nomtabla = rs.getString(1);
		   String tabla = rs.getString(3);
		   ResultSet ra = md.getColumns(nomtabla, null, tabla, null);
		   while(ra.next())
		   {
			   DefaultMutableTreeNode nodohijo=new DefaultMutableTreeNode(""+ra.getString(4));
			   modelo.insertNodeInto(nodohijo, NODO,0);
			    		
		   }
			    	
	   }
	
   }

   private void thisComponentShown(ComponentEvent evt) 
   {
      this.conectarBD();
   }
   
   private void thisComponentHidden(ComponentEvent evt) 
   {
      this.desconectarBD();
   }

   private void btnEjecutarActionPerformed(ActionEvent evt) 
   {
      this.refrescarTabla();      
   }
   
   private void conectarBD()
   {
         try
         {
            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos;  
            tabla.connectDatabase(driver, uriConexion, usuario, clave);
           
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
         catch (ClassNotFoundException e)
         {
            e.printStackTrace();
         }
      
   }

   private void desconectarBD()
   {
         try
         {
            tabla.close();            
         }
         catch (SQLException ex)
         {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
         }      
   }

   private void refrescarTabla()
   {
	   
	   try
		{    

		// seteamos la consulta a partir de la cual se obtendrán los datos para llenar la tabla
	    	  tabla.setSelectSql(this.txtConsulta.getText().trim());

	    	  // obtenemos el modelo de la tabla a partir de la consulta para 
	    	  // modificar la forma en que se muestran de algunas columnas  
	    	  tabla.createColumnModelFromQuery();    	    
	    	  for (int i = 0; i < tabla.getColumnCount(); i++)
	    	  { // para que muestre correctamente los valores de tipo TIME (hora)  		   		  
	    		 if	 (tabla.getColumn(i).getType()==Types.TIME)  
	    		 {    		 
	    		  tabla.getColumn(i).setType(Types.CHAR);  
	  	       	 }
	    		 // cambiar el formato en que se muestran los valores de tipo DATE
	    		 if	 (tabla.getColumn(i).getType()==Types.DATE)
	    		 {
	    		    tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
	    		 }
	          }  
	    	  // actualizamos el contenido de la tabla.   	     	  
	    	  tabla.refresh();
		   /*String comando = new String(txtConsulta.getText());
			if(txtConsulta.getText(0, 6).toLowerCase().equals("select")){// Si el comando no modifica la base de datos, ingresa aqui.							 
				// seteamos la consulta a partir de la cual se obtendrán los datos para llenar la tabla
				tabla.setSelectSql(this.txtConsulta.getText().trim());		
			}
			else { //modifica la base de datos				
				PreparedStatement pstmt = tabla.getConnection().prepareStatement(comando);
				pstmt.execute();			
				String selec = list.getSelectedValue();
				mostrarTablas();
				list.setSelectedIndex(DLM.indexOf(selec));
			}*/
			/*if(tabla.getSelectSql() != null) {
				tabla.createColumnModelFromQuery();    	    
				for (int i = 0; i < tabla.getColumnCount(); i++)
				{	   		  
					if	 (tabla.getColumn(i).getType()==Types.TIME)  
					{    		 
						tabla.getColumn(i).setType(Types.CHAR);  
					}
					if	 (tabla.getColumn(i).getType()==Types.DATE)
					{
						tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
					}
				}				
				tabla.refresh();*/
			//}
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
	}
	   
	   
   
   private void mostrarTablas() {
		DLM.clear();
		Statement st = null;
		ResultSet rs = null;
		try {         				
			st = (Statement) tabla.getConnection().createStatement();
			rs = st.executeQuery("SELECT table_name FROM "
					+ "information_schema.tables where "
					+ "table_schema='parquimetros'");
			boolean sig = rs.first();
			while(sig) {
				DLM.addElement(rs.getString(1));
				sig = rs.next();												
			}      			
		} catch (SQLException ex) {
			salidaError(ex);
		} catch (NullPointerException ex2){
			JOptionPane.showMessageDialog(null,
					"Error",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
		finally {					
			if(st != null) {
				try {
					st.close();
				} catch (SQLException ex) {
					salidaError(ex);
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					salidaError(ex);
				}
			}
		}
	}
   
   private void salidaError(SQLException ex) {
		JOptionPane.showMessageDialog(null,
				ex.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE);
		System.out.println("SQLException: " + ex.getMessage());
		System.out.println("SQLState: " + ex.getSQLState());
		System.out.println("VendorError: " + ex.getErrorCode());
	}
   
  }



