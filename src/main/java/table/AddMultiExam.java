package table;
import cls.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.border.TitledBorder;
import java.awt.Color;


/**
 * La classe AddMultiExam permette di aggiungere un esame complesso,
 * costituito da più esami parziali.
 */
public class AddMultiExam extends AddExamEasy {

	private static final long serialVersionUID = 1L;
	/**
	 * Classe privata per le variabili di istanza.
	 */
	private JPanel contentPane; // Pannello principale per l'interfaccia utente

	/**
	 * Variabili di tipo String per memorizzare nome, cognome, materia e peso.
	*/
	private String name, surname, subject, weight; 

	/**
	 * Variabili di tipo int per memorizzare il numero di crediti e il numero di esami.
	*/
	private int CFU, nExams; 

	/**
	 * Campi di testo per l'input dell'utente.
	*/
	private JTextField textField, textField_1, textField_2, textField_4, textField_3, textField_5; 

	/**
	 * ArrayList per memorizzare i dettagli di ogni esame intermedio.
	*/
	private ArrayList<midExam> midExams; 

	/**
	 * Istanza della classe GestioneEsami per le operazioni sul database.
	*/
	private GestioneEsami ge;
	
	private int CFU_MAX = 12;
	private int CFU_MIN = 1;
	/**
	 * Creazione frame addMultiExam.
	*/
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddMultiExam frame = new AddMultiExam();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Creazione del frame
	 */
	public AddMultiExam() {
		setTitle("Add Complex Exam");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 585, 408);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(45, 153, 142));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		JComboBox comboBox = new JComboBox();
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAddMultipleExam = new JLabel("Add Complex Exam");
		lblAddMultipleExam.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddMultipleExam.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblAddMultipleExam.setBounds(0, 10, 562, 54);
		contentPane.add(lblAddMultipleExam);
		
		/**
		 * Variabile contente le opzioni dei JOptionPane di errore ed informazione.
		 */
		Object[] options = {"OK"};
 		
		/**
		 * Bottone per l'aggiunta di un esame.
		 */
		JButton btnNewButton = new JButton("Add Exam");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				/**
				 * Contiene le informazioni delle prove intermedie inserite dall'utente
				 */
				midExams = new ArrayList<>();
				/**
				 * Numero di esami dal quale e' composto
				 */
				nExams = (int) comboBox.getSelectedItem();	
				/*
				 * Nome alunno
				 */
				name = textField.getText();		
				/**
				 * Cognome alunno
				 */
				surname = textField_1.getText();		
				/**
				 * Materia di insegnamento/corso universitario
				 */
				subject = textField_2.getText();	
				/**
				 * Array contenente i voti
				 */
				String[] vote = textField_5.getText().split(",");
				/**
				 * Array contente i pesi 
				 */
				String[] weight = textField_3.getText().split(",");
				/*
				 * Numero di crediti formativi universitari associati all'esame
				 */
				CFU = Integer.parseInt(textField_4.getText());
				
				String query2, id;
					
				if(!checkMultiExam(nExams, name, surname, subject, vote, weight, CFU)) {
					JOptionPane.showOptionDialog(contentPane, 
							"Some missing/wrong information detected, please check.", 
	                           "InfoCheckFalse", 
	                           JOptionPane.DEFAULT_OPTION, 
	                           JOptionPane.ERROR_MESSAGE, 
	                           null,
	                           null,
	                           options[0]
	              );
				}else {	
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
		        		Statement st = con.createStatement();
		        		
		        		/*
		        		 * Variabili utili per la gestione degli errori, rappresentano le righe aggiornate nel database, se 0, nessuna riga e' stata aggiornata.
		        		 */
		        		int result = 0, result2 = 0;
		        		/**
		        		 * Variabile per popolare arraylist (midExams) che contiene peso e voto di ogni prova intermedia.
		        		 */
		        		midExam midExamNow;
		        		MultiExam mulExam;
		        		
		        		for(int i = 0; i < nExams; i++) {
		        			midExamNow = new midExam(Integer.parseInt(vote[i]), Integer.parseInt(weight[i]));
							midExams.add(midExamNow);
						}
						
		        		/**
		        		 * Oggetto utilizzato per il calcolo del voto finale basato sulla media ponderata degli esami intermedi.
		        		 */
		        		mulExam= new MultiExam(name, surname, subject, CFU, midExams);
						mulExam.computeVote();
						
						/**
						 * Query per l'inserimento dei valori all'interno del database.
						 */
						String queryInsert = "INSERT INTO exams (studentName, studentSurname, subject, Grade, Lode, CFU) VALUES (?, ?, ?, ?, 0, ?);";
 						
						try(PreparedStatement ps = con.prepareStatement(queryInsert)){
							ps.setString(1, mulExam.getName());
							ps.setString(2, mulExam.getSurname());
							ps.setString(3, mulExam.getSubject());
							ps.setInt(4, mulExam.getGrade());
							ps.setInt(5, mulExam.getNumeroCrediti());
							
							result = ps.executeUpdate();
						}
						
						//Recupero id dell'ultimo elemento inserito nel db.
						String idLast = getLastElementId(st);
						
						//Inserisco il record relazionato nel db.
						result2 = createRecordRelated(idLast, weight, vote, con);
						
						//Se entrambi le query sono andate a buon fine..
						if (result > 0 && result2 > 0) {
							GestioneEsami.updateTable(null, true);
							JOptionPane.showOptionDialog(contentPane, 
							"Record successfully added to the database!", 
	                        "recordsAdded", 
	                        JOptionPane.DEFAULT_OPTION, 
			                JOptionPane.INFORMATION_MESSAGE, 
			                null,
			                null,
			                options[0]
			                );
								dispose();
				            } else {
				            	JOptionPane.showOptionDialog(contentPane, 
										"No records were added to database, please retry", 
			                            "InfoCheckFalse", 
			                            JOptionPane.DEFAULT_OPTION, 
			                            JOptionPane.ERROR_MESSAGE, 
			                            null,
			                            null,
			                            options[0]
			                    );
				            }
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
			}		}
		}});
		
		btnNewButton.setBounds(451, 321, 101, 33);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Insert Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 75, 542, 235);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name: ");
		lblNewLabel.setBounds(10, 50, 47, 14);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		textField = new JTextField();
		textField.setBounds(78, 48, 96, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblSurname = new JLabel("Surname: ");
		lblSurname.setBounds(10, 97, 74, 14);
		panel.add(lblSurname);
		lblSurname.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		textField_1 = new JTextField();
		textField_1.setBounds(78, 95, 96, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(78, 144, 96, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblSurname_1 = new JLabel("Subject:");
		lblSurname_1.setBounds(10, 146, 74, 14);
		panel.add(lblSurname_1);
		lblSurname_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		JLabel lblSurname_1_1 = new JLabel("Exams:");
		lblSurname_1_1.setBounds(292, 50, 74, 14);
		panel.add(lblSurname_1_1);
		lblSurname_1_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		
		comboBox.setBounds(360, 47, 96, 22);
		panel.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new Integer[] {2, 3}));
		
		JLabel lblWeight = new JLabel("Weight:");
		lblWeight.setBounds(292, 97, 58, 18);
		panel.add(lblWeight);
		lblWeight.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		textField_3 = new JTextField();
		textField_3.setBounds(360, 95, 96, 20);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblVotes = new JLabel("Votes:");
		lblVotes.setBounds(292, 144, 58, 18);
		panel.add(lblVotes);
		lblVotes.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		textField_5 = new JTextField();
		textField_5.setBounds(360, 144, 96, 20);
		panel.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblCfu = new JLabel("CFU:");
		lblCfu.setBounds(292, 195, 36, 18);
		panel.add(lblCfu);
		lblCfu.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		textField_4 = new JTextField();
		textField_4.setBounds(360, 195, 96, 20);
		panel.add(textField_4);
		textField_4.setColumns(10);
		
	}
	
	/**
     * Verifica la correttezza dei dati dell'esame complesso.
     *
     * @param nExams   il numero di esami parziali
     * @param name     il nome dello studente
     * @param surname  il cognome dello studente
     * @param subject  la materia dell'esame
     * @param votes    array voti degli esami parziali
     * @param weights  array pesi degli esami parziali
     * @param cfu      i crediti formativi universitari dell'esame
     * @return true se i dati sono corretti, false altrimenti
     */
	private boolean checkMultiExam(int nExams, String name, String surname, String subject, String[] votes, String[] weights, int cfu) {
		boolean ret = true;
		int weightCheck = 0;
		/**
		 * Filtro regex per i numeri.
		 */
		String regexCheckNum = ".*\\d+.*"; 
		/**
		 * Filtro regex per i caratteri.
		 */
		String regexCheckChar = "[^0-9]";
		
		if(votes.length != nExams || weights.length != nExams) {
			return false;
		}
		
		//Controllo campi vuoti.
		if(name == null || surname == null || subject == null)
			ret = false;
		
		//Controllo match con regex.
		if(name.matches(regexCheckNum) || surname.matches(regexCheckNum) || subject.matches(regexCheckNum)) {
			ret = false;
		}
		
		//Voto out of bounds oppure caratteri nei pesi.
		for(int i = 0; i < nExams; i++) {
			if(Integer.parseInt(votes[i]) > 30 || Integer.parseInt(votes[i]) < 18) {
				ret = false;
			}
			
			if(votes[i].matches(regexCheckChar) || weights[i].matches(regexCheckChar)) {
				ret = false;
			}
			
			weightCheck += Integer.parseInt(weights[i]);
		}
		
		/*
		 * Variabile per controllo somma dei pesi, se non si sommano a 100 i dati non sono corretti.
		 */
		ret = (weightCheck>=99 && weightCheck <= 100) ? ret : false;
		
		//Controllo assegnazione crediti formativi maggiori ai limiti impostati: CFU_MIN & CFU_MAX.
		if(cfu < CFU_MIN || cfu > CFU_MAX)
			ret = false;
		
		return ret;
	}
	
	/**
	 * Trova l'ultimo record inserito e ne recupera l'id.
	 * 
	 * @param st Statement connessione al database.
	 * 
	 * @return id ultimo elemento inserito come stringa.
	 */
	String getLastElementId(Statement st) {
		String queryIdRel = "SELECT idExams FROM db.exams ORDER BY idExams DESC LIMIT 1;";
		ResultSet idRel;
		try {
			idRel = st.executeQuery(queryIdRel);
			if(idRel.next()) {
				return idRel.getString("idExams");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return "";		
	}
	
	/**
     * Funzione atoi personalizzata per la conversione dell'array di stringhe in array in int.
     *
     * @param stringArray l'array di stringhe da convertire
     * @return l'array di interi risultante dalla conversione
     */
    public static int[] atoiOnArray(String[] stringArray) {
        // Creazione di un array di interi della stessa lunghezza dell'array di stringhe
        int[] intArray = new int[stringArray.length];

        // Iterazione su ciascuna stringa dell'array di stringhe
        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }

        return intArray;
    }
    
    /**
     * Crea un record nel database relazionato per il salvataggio delle informazioni.
     * @param id id con il quale inserire il record (primary e foreign key idExams)
     * @param w Array dei pesi delle prove intermedie come String.
     * @param v Array dei voti intermedi come String.
     * @param con Oggetto <tipo>Connection per la connessione e l'esecuzione della query.
     * @return risultato della query.
     */
	int createRecordRelated(String id, String w[], String v[], Connection con) {
		int result = 0;
		String queryInsert = "INSERT INTO multi_exams(idExams, w1, w2, w3, vote1, vote2, vote3) VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		//Converto gli array di String in array di int.
		int weightsInt[] = atoiOnArray(w);
		int gradesInt[] = atoiOnArray(v);
		
		try(PreparedStatement ps = con.prepareStatement(queryInsert)){
			ps.setString(1, id);
			if(w.length < 3) {
				ps.setString(4, "0");
				ps.setString(7, "0");
			}
			int startingQueryIndex = 2;
			
			for(int i = startingQueryIndex; i <= weightsInt.length + 1; i++) {
				ps.setInt(i, weightsInt[i-startingQueryIndex]);
				ps.setInt(i+3, gradesInt[i-startingQueryIndex]);
			}
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}