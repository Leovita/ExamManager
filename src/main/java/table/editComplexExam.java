package table;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

/**
 * Classe per la modifica di esami complessi.
 * 
 * @author Leonardo Vitale.
 */
public class editComplexExam extends JFrame {

	private static final long serialVersionUID = 1L;
	/*
	 * Pannello principale.
	 */

	private JPanel contentPane;
	/*
	 * TextField inserimento nome.
	 */
	private JTextField textField;
	/*
	 * TextField inserimento cognome.
	 */
	private JTextField textField_1;
	/*
	 * TextField inserimento materia di insegnamento.
	 */
	private JTextField textField_2;
	/*
	 * TextField inserimento pesi prove intermedie.
	 */
	private JTextField textField_3;
	/*
	 * TextField inserimento voti prove intermedie.
	 */
	private JTextField textField_4;
	/*
	 * TextField inserimento crediti formativi universitari.
	 */
	private JTextField textField_5;
	/*
	 * Oggetto <tipo>GestioneEsami per l'aggiornamento della tabella.
	 */
	private GestioneEsami ge;

	/**
     * Costruttore della classe editComplexExam.
     * 
     * @param id ID dell'esame da modificare
     * @param name Nome dello studente
     * @param surname Cognome dello studente
     * @param subject Materia dell'esame
     * @param weights Pesi degli esami
     * @param grades Voti degli esami
     * @param lode Indicatore di lode
     * @param cfu Crediti formativi
     */
	public editComplexExam(int id, String name, String surname, String subject, String[] weights, String[] grades, int lode, String cfu) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 549, 340);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(45, 153, 142));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Edit Complex Exam");
		lblNewLabel.setBounds(161, 11, 241, 34);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		contentPane.add(lblNewLabel);
		
		//Variabili per precaricare i dati all'interno dei TextField corrispondenti.
		// ["50", "20", "30"] -> "50,20,30"
		String weightsPre = createVisualString(weights);
		String gradesPre = createVisualString(grades);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Insert Data", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 56, 515, 199);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textField_3 = new JTextField();
		textField_3.setBounds(330, 52, 86, 17);
		panel.add(textField_3);
		textField_3.setText(weightsPre);
		textField_3.setColumns(10);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("lode");
		chckbxNewCheckBox.setBounds(438, 100, 75, 23);
		panel.add(chckbxNewCheckBox);
		
		textField_4 = new JTextField();
		textField_4.setBounds(330, 95, 86, 17);
		panel.add(textField_4);
		textField_4.setText(gradesPre);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_1_3 = new JLabel("Weights");
		lblNewLabel_1_3.setBounds(256, 54, 86, 14);
		panel.add(lblNewLabel_1_3);
			
		JLabel lblNewLabel_1_4 = new JLabel("Grades");
		lblNewLabel_1_4.setBounds(256, 95, 64, 19);
		panel.add(lblNewLabel_1_4);
			
		JLabel lblNewLabel_1_5 = new JLabel("CFU");
		lblNewLabel_1_5.setBounds(258, 137, 42, 14);
		panel.add(lblNewLabel_1_5);
			
		textField_5 = new JTextField();
		textField_5.setBounds(330, 135, 86, 17);
		panel.add(textField_5);
		textField_5.setText(cfu);
		textField_5.setColumns(10);
			
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setBounds(24, 54, 77, 14);
		panel.add(lblNewLabel_1);
			
			
		textField = new JTextField();
		textField.setBounds(111, 51, 86, 20);
		panel.add(textField);
		textField.setText(name);
		textField.setEditable(false);
		textField.setColumns(10);
			
		JLabel lblNewLabel_1_1 = new JLabel("Surname");
		lblNewLabel_1_1.setBounds(24, 97, 77, 14);
		panel.add(lblNewLabel_1_1);
			
		textField_1 = new JTextField();			
		textField_1.setBounds(111, 99, 86, 17);
		panel.add(textField_1);
		textField_1.setText(surname);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
			
		textField_2 = new JTextField();
		textField_2.setBounds(111, 135, 86, 17);
	
		panel.add(textField_2);
		textField_2.setText(subject);
		textField_2.setColumns(10);
			
		if(lode == 1) {
			chckbxNewCheckBox.setSelected(true);
		}
		
		JLabel lblNewLabel_1_2 = new JLabel("Subject");
		lblNewLabel_1_2.setBounds(24, 137, 57, 14);
		panel.add(lblNewLabel_1_2);
		/*
		 * Aggiunte del pulsante di modifica.
		 * Quando premuto cambia l'aspetto della tabella in base alle modifiche e aggiurna il dati nel DB.
		 */
		JButton btnNewButton = new JButton("EDIT");
		btnNewButton.setBounds(411, 268, 99, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String[] w = createArray(textField_3.getText());  
				String[] g = createArray(textField_4.getText());  
				String cfu = textField_5.getText();
				
				int lode = chckbxNewCheckBox.isSelected() ? 1 : 0;
				
				float newVote = 0;
				
				Object[] options = {"OK"};
				
				for(int i = 0; i < g.length; i++) {
					newVote += Integer.parseInt(g[i]) * (Integer.parseInt(w[i]) / 100.0);
				}
		
				//QUERY SQL per aggiornare il database.
				String sql = "UPDATE multi_exams SET w1 = '" + w[0] + "', w2 = '" + w[1] + "', w3 = '" + w[2] + "', vote1 = '" + g[0] + "', vote2 = '" + g[1] + "'," + "vote3 = '" + g[2] + "' WHERE idExams = " + id + ";";  
				String sql2 = "UPDATE exams SET lode = '" + Integer.toString(lode) + "', cfu = '" + cfu + "', Grade = '" + newVote + "' WHERE idExams = " + id + ";";
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
					Statement st = con.createStatement();
					
					//Esecuzione query SQL.
					int rowsUpdated = st.executeUpdate(sql);
					int rowsUpdated2 = st.executeUpdate(sql2);
					
					//Controlla se viene aggiornata almeno 1 riga.
					if(rowsUpdated > 0 && rowsUpdated2 > 0) {

						JOptionPane.showOptionDialog(contentPane, 
								"Information successfully updated!", 
                                "InfoFrame", 
                                JOptionPane.DEFAULT_OPTION, 
                                JOptionPane.INFORMATION_MESSAGE, 
                                null,  
                                null,
                                options[0]);
						GestioneEsami.updateTable(null, false);
					} else {
						JOptionPane.showOptionDialog(contentPane,
								"Error",
								"Frame error",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE,
								null,
								options,
								options[0]);
					}
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
				dispose();
				
			}
		});
		contentPane.add(btnNewButton);
	}
	/**
	 * Questo metodo viene utilizzato per creare una rappresentazione visiva di stringa dei pesi e dei voti.
	 * Formatta l'array di input in una stringa, separando ogni elemento con una virgola,
	 * ad eccezione dell'ultimo elemento che non è seguito da una virgola.
	 *
	 * @param values Un array di stringhe che rappresentano i pesi o i voti.
	 * @return Una stringa che è una rappresentazione visiva dell'array di input.
	 */
	public String createVisualString(String[] values){
		// Inizializza la stringa di ritorno.
		String visualString = "";

		// Costruisci la stringa visiva rappresentazione dei pesi e dei voti.
		for(int i = 0; i < values.length; i++) {
			if(i < 2) {
				// Aggiungi una virgola dopo ogni elemento ad eccezione dell'ultimo.
				visualString += values[i] + ",";
			} else {
				// Aggiungi l'ultimo elemento senza una virgola.
				visualString += values[i];
			}
		}

		// Restituisci la stringa creata.
		return visualString;
	}
	/**
     * Metodo per creare un array di stringhe da una stringa di input.
     * Divide l'input con le virgole e assegna 0 qualora non ci sia voto o peso.
	 * 
     * @param input Stringa di input
     * @return Array di stringhe
     */
	public static String[] createArray(String input) {
        // Dividi la stringa di input in base alle virgole
        String[] values = input.split(",");
        
        // Inizializza un array con 3 elementi
        String[] result = new String[3];
        
        // Copia i valori dall'array 'values' all'array 'result'
        for (int i = 0; i < values.length && i < 3; i++) {
            result[i] = values[i];
        }
        
        // Riempie eventuali elementi rimanenti con "0"
        for (int i = values.length; i < 3; i++) {
            result[i] = "0";
        }
        
        return result;
    }
}