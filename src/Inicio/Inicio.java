package Inicio;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ATM.ATMUI;
import Admin.AdminUI;
import Prestamo.PrestamoUI;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Inicio {

	private JFrame frmEbanks;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio window = new Inicio();
					window.frmEbanks.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Inicio() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEbanks = new JFrame();
		frmEbanks.setTitle("eBank");
		frmEbanks.setBounds(100, 100, 315, 271);
		frmEbanks.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEbanks.getContentPane().setLayout(null);
		
		JButton btnAdmin = new JButton("Admin");
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFrame admin = new AdminUI(frmEbanks);
				admin.setVisible(true);
				frmEbanks.setEnabled(false);

			}
		});
		btnAdmin.setBounds(72, 11, 157, 57);
		frmEbanks.getContentPane().add(btnAdmin);
		
		JButton btnNewButton = new JButton("ATM");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFrame admin = new ATMUI(frmEbanks);
				admin.setVisible(true);
				frmEbanks.setEnabled(false);
			}
		});
		btnNewButton.setBounds(72, 79, 157, 57);
		frmEbanks.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Prestamos");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				

				JFrame admin = new PrestamoUI(frmEbanks);
				admin.setVisible(true);
				frmEbanks.setEnabled(false);

			}
		});
		btnNewButton_1.setBounds(72, 147, 157, 74);
		frmEbanks.getContentPane().add(btnNewButton_1);
	}
}
