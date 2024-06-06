package chart;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

/**
 * Questa classe rappresenta una finestra di filtro per il grafico.
 * Consente all'utente di filtrare i dati del grafico in base a un campo selezionato e un valore di filtro.
 */
public class filterChart extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Avvia l'applicazione.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					filterChart frame = new filterChart();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea la finestra.
	 */
	public filterChart() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * Etichetta per il titolo del filtro del grafico.
		 */
		JLabel lblNewLabel = new JLabel("Chart filter");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblNewLabel.setBounds(157, 11, 180, 25);
		contentPane.add(lblNewLabel);
		
		/**
		 * Combo box per la selezione del campo su cui filtrare.
		 */
		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"Subject", "Student"}));
		comboBox.setBounds(68, 115, 100, 22);
		contentPane.add(comboBox);
		
		/**
		 * Etichetta per il campo su cui filtrare.
		 */
		JLabel lblNewLabel_1 = new JLabel("Filter by: ");
		lblNewLabel_1.setBounds(10, 119, 67, 14);
		contentPane.add(lblNewLabel_1);
		
		/**
		 * Etichetta per il campo di input del valore di filtro.
		 */
		JLabel lblNewLabel_2 = new JLabel("Insert filter: ");
		lblNewLabel_2.setBounds(181, 119, 120, 14);
		contentPane.add(lblNewLabel_2);
		
		/**
		 * Campo di testo per l'inserimento del valore di filtro.
		 */
		textField = new JTextField();
		textField.setBounds(270, 116, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		/**
		 * Pulsante per applicare il filtro.
		 * Quando premuto, crea una nuova finestra del grafico con i dati filtrati.
		 */
		JButton btnNewButton = new JButton("Filter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedValue = (String)comboBox.getSelectedItem();
				chart c;
				try {
					c = new chart("Grafico", selectedValue, textField.getText());
					c.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(299, 201, 89, 23);
		contentPane.add(btnNewButton);
	}
}