package table;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import java.awt.Color;

/**
 * Questa classe permette la modifica di un esame semplice nel database.
 * 
 * @author Leonardo Vitale.
 */
public class editSimpleExam extends JFrame {

	private static final long serialVersionUID = 1L;
	/**
     * Pannello principale per i componenti della finestra.
     */
    private JPanel contentPane;

    /**
     * Campo di testo per immettere il nome dell'esame.
     */
    private JTextField textField;

    /**
     * Campo di testo per immettere il cognome dell'esame.
     */
    private JTextField textField_1;

    /**
     * Campo di testo per immettere la materia dell'esame.
     */
    private JTextField textField_2;

    /**
     * Campo di testo per immettere i crediti formativi dell'esame.
     */
    private JTextField textField_4;

    /**
     * Riferimento all'istanza della classe GestioneEsami.
     */
    private GestioneEsami GE;
	
	/**
     * Array per memorizzare i dati modificati.
     */
	String edit[] = {"", "", "", ""};

	/**
     * Costruttore della classe editSimpleExam.
     * Inizializza la finestra e i componenti.
     *
     * @param id ID dell'esame da modificare.
     * @param name Nome dell'esame.
     * @param surname Cognome dell'esame.
     * @param subject Materia dell'esame.
     * @param Grade Voto dell'esame.
     * @param lode Lode dell'esame.
     * @param CFU Crediti formativi dell'esame. 
	*/

	public editSimpleExam(int id, String name, String surname, String subject, int Grade, int lode, int CFU) {
		setTitle("editSimpleExam");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 567, 354);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(45, 153, 142));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Edit simple exam");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblNewLabel.setBounds(192, 11, 177, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblSubject = new JLabel("Subject");
		lblSubject.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblSubject.setBounds(22, 209, 65, 14);
		contentPane.add(lblSubject);
		
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Lode");
		chckbxNewCheckBox.setBounds(407, 45, 97, 23);
		
		if(lode == 1) {
			chckbxNewCheckBox.setSelected(true);
		}
		
		
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Insert data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 64, 531, 207);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JComboBox<String> comboBox = new JComboBox();
		comboBox.setBounds(315, 45, 86, 22);
		panel.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[]{"18","19","20","21","22","23","24","25","26","27","28","29","30",}));
		comboBox.setSelectedItem(Integer.toString(Grade));
		panel.add(chckbxNewCheckBox);
		
		textField = new JTextField();
		textField.setBounds(80, 46, 86, 20);
		textField.setEditable(false);
		panel.add(textField);
		textField.setText(name);
		textField.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 48, 52, 14);
		panel.add(lblName);
		lblName.setFont(new Font("Times New Roman", Font.BOLD, 16));
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(10, 96, 65, 14);
		panel.add(lblSurname);
		lblSurname.setFont(new Font("Times New Roman", Font.BOLD, 16));
		
		textField_1 = new JTextField();
		textField_1.setBounds(80, 94, 86, 20);
		textField_1.setEditable(false);
		panel.add(textField_1);
		textField_1.setText(surname);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(80, 141, 86, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText(subject);
		
		JLabel lblGrade = new JLabel("Grade");
		lblGrade.setBounds(253, 48, 52, 14);
		panel.add(lblGrade);
		lblGrade.setFont(new Font("Times New Roman", Font.BOLD, 16));
		
		JLabel lblCfu = new JLabel("CFU");
		lblCfu.setBounds(253, 96, 52, 14);
		panel.add(lblCfu);
		lblCfu.setFont(new Font("Times New Roman", Font.BOLD, 16));
		
		textField_4 = new JTextField();
		textField_4.setBounds(315, 94, 86, 20);
		panel.add(textField_4);
		textField_4.setText(Integer.toString(CFU));
		textField_4.setColumns(10);
		
		/**
		 * Metodo per gestire l'azione del pulsante "EDIT".
		 * Aggiorna i dati dell'esame nel database.
		 *
		 * @param e Evento di azione del pulsante.
		*/
		JButton btnNewButton = new JButton("EDIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edit[0] = (String) comboBox.getSelectedItem();
				edit[1] = chckbxNewCheckBox.isSelected() ? "1" : "0";
				edit[2] = textField_4.getText();
				edit[3] = textField_2.getText();
				
				Object[] options = {"OK"};
				
				String sql = "UPDATE exams SET Grade = '" + edit[0] + "', Lode = '" + edit[1] + "', CFU = '" + edit[2] +"', subject = '" + edit[3] + "' WHERE idExams = " + id + ";";  
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
					Statement st = con.createStatement();
					
					int rowsUpdated = st.executeUpdate(sql);
					
					//Controllo risultato query (righe modificate)
					if(rowsUpdated > 0) {
						JOptionPane.showOptionDialog(contentPane, 
								"Information successfully updated!", 
                                "InfoFrame", 
                                JOptionPane.DEFAULT_OPTION, 
                                JOptionPane.INFORMATION_MESSAGE, 
                                null, 
                                options, 
                                options[0]);
						 GestioneEsami.updateTable(null, false);
						 dispose();
					} else {
						JOptionPane.showOptionDialog(contentPane, 
								"The server could't accept you request, please check your informations.", 
                                "ErrorFrame", 
                                JOptionPane.DEFAULT_OPTION, 
                                JOptionPane.ERROR_MESSAGE, 
                                null, 
                                options, 
                                options[0]);
					}
					 	
					
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton.setBounds(452, 282, 89, 23);
		contentPane.add(btnNewButton);
		
	}
}