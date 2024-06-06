package main;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import chart.filterChart;
import saveload.SaveAndLoad;
import table.GestioneEsami;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;


/**
 * La classe Menu crea un frame per la scelta delle diverse funzionalita' del programma: organizzazione tabellare degli esami, salvataggio su file locale e
 * creazione di grafici.
 * 
 * @author Leonardo Vitale
 */
public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	/*
	 * Pannello principale.
	 */
	private JPanel contentPane;
	/*
	 * Avvio del menu.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
     * Costruttore della classe Menu che inizializza e configura il frame.
     */
	public Menu() {
		
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 319, 420);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(45, 153, 142));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * JButton per la gestione della visualizzazione della tabella degli esami.
		 * Apre una nuova finestra per la gestione della tabella.
		*/
		JButton btnNewButton = new JButton("Table Exams");
		btnNewButton.setIcon(new ImageIcon(Menu.class.getResource("/imgs/table.jpeg")));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestioneEsami g = new GestioneEsami();
				g.setVisible(true);
			}
		});
		
		/**
		 * JButton per la gestione del salvataggio e del caricamento dei dati.
		 * Apre una nuova finestra per il salvataggio e il caricamento dei dati.
		*/
		JButton btnNewButton_1 = new JButton("Save & Load");
		btnNewButton_1.setIcon(new ImageIcon(Menu.class.getResource("/imgs/save.jpeg")));
		btnNewButton_1.setBounds(33, 149, 232, 64);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveAndLoad sl = new SaveAndLoad();
				sl.setVisible(true);
			}
		});
		btnNewButton.setToolTipText("");
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 11));
		btnNewButton.setBounds(33, 70, 232, 64);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Menu");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		lblNewLabel.setBounds(123, 21, 132, 29);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 200, 311, 202);
		contentPane.add(panel);
		panel.setLayout(null);
		
		/**
		 * JButton per la chiusura dell'applicazione.
		 * Chiude l'applicazione e libera le risorse.
		*/
		JButton btnNewButton_2_1 = new JButton("Close Menu");
		btnNewButton_2_1.setIcon(new ImageIcon(Menu.class.getResource("/imgs/exit.png")));
		btnNewButton_2_1.setBounds(37, 100, 232, 64);
		panel.add(btnNewButton_2_1);
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				dispose();
			}
		});
		btnNewButton_2_1.setFont(new Font("Times New Roman", Font.BOLD, 11));
		btnNewButton_2_1.setBackground(new Color(255, 255, 255));
		
		/**
		 * JButton per la gestione della visualizzazione dei grafici statistici.
		 * Apre una nuova finestra per la visualizzazione dei grafici.
		*/
		JButton btnNewButton_2 = new JButton("Graphic Stats");
		btnNewButton_2.setIcon(new ImageIcon(Menu.class.getResource("/imgs/graph.jpeg")));
		btnNewButton_2.setBounds(37, 26, 232, 64);
		panel.add(btnNewButton_2);
		btnNewButton_2.setBackground(new Color(255, 255, 255));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterChart fc = new filterChart();
				fc.setVisible(true);			
			}
		});
		btnNewButton_2.setFont(new Font("Times New Roman", Font.BOLD, 11));
	}
}