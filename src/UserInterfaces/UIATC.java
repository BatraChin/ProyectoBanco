package UserInterfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class UIATC extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIATC frame = new UIATC();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UIATC() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnConsultaSaldo = new JButton("Consulta Saldo");
		btnConsultaSaldo.setBounds(131, 34, 165, 51);
		contentPane.add(btnConsultaSaldo);
		
		JButton btnUltimosMovimientos = new JButton("Ultimos Movimientos");
		btnUltimosMovimientos.setBounds(131, 96, 165, 62);
		contentPane.add(btnUltimosMovimientos);
		
		JButton btnMovimientosPorPerodos = new JButton("Movimientos por per\u00EDodos");
		btnMovimientosPorPerodos.setBounds(131, 172, 165, 62);
		contentPane.add(btnMovimientosPorPerodos);
	}

}
