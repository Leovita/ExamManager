package table;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cls.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.awt.event.ActionEvent;

/**
 * Classe utilizzata per aggiungere un esame semplice al database.
 * 
 * @author Leonardo Vitale.
 */
public class AddExamEasy extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Pannello principale del frame.
	 * Contiene tutti gli elementi visibili del frame.
	 */
	private JPanel contentPane;

	/**
	 * Campo di testo per l'inserimento del nome dello studente.
	 */
	private JTextField textField;

	/**
	 * Campo di testo per l'inserimento del cognome dello studente.
	 */
	private JTextField textField_1;

	/**
	 * Campo di testo per l'inserimento della materia dell'esame.
	 */
	private JTextField textField_2;

	/**
	 * Campo di testo per l'inserimento del voto ottenuto.
	 */
	private JTextField textField_3;

	/**
	 * Campo di testo per l'inserimento dei crediti formativi.
	 */
	private JTextField textField_4;

	/**
	 * Riferimento statico al frame AddExamEasy.
	 * Questo campo viene utilizzato per fare riferimento al frame e gestirne l'apertura e la chiusura.
	 */
	public static AddExamEasy frame;

	/**
	 * Riferimento all'oggetto GestioneEsami.
	 * Questo campo viene utilizzato per eseguire operazioni sulla tabella degli esami.
	 */
	private GestioneEsami ge;
	
	/**
	 * CFU_MAX rappresenta il numero massimo assegnabile per ogni esame
	 */
	private int CFU_MAX = 12;
	/**
	 * CFU_MIN rappresenta il numero minimo assegnabile per ogni esame
	 */
	private int CFU_MIN = 1;
	
	/**
     * Funzione main per la creazione di un frame <tipo>AddExamEasy, che da
     * la possibilita' di aggiungere un nuovo esame semplice al database.
     */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new AddExamEasy();
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
	public AddExamEasy() {
		setTitle("Add Single Exam");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 608, 394);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(45, 153, 142));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAddSingleExam = new JLabel("Add Single Exam");
		lblAddSingleExam.setBounds(5, 16, 592, 54);
		lblAddSingleExam.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblAddSingleExam.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAddSingleExam);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Insert Data", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(15, 82, 567, 193);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name: ");
		lblNewLabel.setBounds(10, 58, 47, 14);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		textField = new JTextField();
		textField.setBounds(84, 56, 96, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblSurname = new JLabel("Surname: ");
		lblSurname.setBounds(10, 97, 74, 14);
		panel.add(lblSurname);
		lblSurname.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		textField_2 = new JTextField();
		textField_2.setBounds(84, 133, 96, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblSurname_1 = new JLabel("Subject:");
		lblSurname_1.setBounds(10, 135, 74, 14);
		panel.add(lblSurname_1);
		lblSurname_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		textField_1 = new JTextField();
		textField_1.setBounds(84, 95, 96, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblGrade = new JLabel("Grade:");
		lblGrade.setBounds(269, 58, 47, 14);
		panel.add(lblGrade);
		lblGrade.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		textField_3 = new JTextField();
		textField_3.setBounds(326, 56, 96, 20);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblCfu = new JLabel("CFU:");
		lblCfu.setBounds(269, 95, 36, 18);
		panel.add(lblCfu);
		lblCfu.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		textField_4 = new JTextField();
		textField_4.setBounds(326, 95, 96, 20);
		panel.add(textField_4);
		textField_4.setColumns(10);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Lode");
		chckbxNewCheckBox.setBounds(441, 55, 96, 23);
		panel.add(chckbxNewCheckBox);	
		
		Object[] options = {"Ok"};
		

		/**
     	* Metodo per l'azione eseguita quando si preme il pulsante "Aggiungi Esame".
     	* 
     	* @param e Evento di azione
     	*/
		JButton btnNewButton = new JButton("Add Exam");
		btnNewButton.setBounds(481, 298, 101, 33);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name, surname, subject;
				int vote, lode, CFU;
				
				name = textField.getText();
				surname = textField_1.getText();
				subject = textField_2.getText();
				vote = Integer.parseInt(textField_3.getText());
				lode = chckbxNewCheckBox.isSelected() ? 1 : 0;
				CFU = Integer.parseInt(textField_4.getText());
					
				if(!checkInfo(name, surname, subject, vote, lode, CFU)) {
					JOptionPane.showOptionDialog(contentPane, 
							"Some missing/wrong information detected, please check.", 
                            "InfoCheckFalse", 
                            JOptionPane.DEFAULT_OPTION, 
                            JOptionPane.ERROR_MESSAGE, 
                            null,
                            null,
                            options[0]
                    );
				} else {
					Exam exam = new Exam(name, surname, subject, vote, lode, CFU);	
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
						Statement st = con.createStatement();
						
						String query = "INSERT INTO exams(idExams, studentName, studentSurname, subject, Grade, Lode, CFU) values (0, '" + exam.getName() + "', '" + 
						exam.getSurname() + "', '" + exam.getSubject() + "', " + exam.getGrade() + ", " + exam.isLode() + ", " + exam.getNumeroCrediti() + ")"; 

						
						int result = st.executeUpdate(query);
						
						if (result > 0) {
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
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//dispose();
				}
			}
		});
	}
	
	/**
     * Metodo per la verifica delle informazioni inserite dall'utente.
     * 
     * @param name Nome dello studente
     * @param surname Cognome dello studente
     * @param subject Materia dell'esame
     * @param vote Voto ottenuto
     * @param lode Indicatore di Lode
     * @param cfu Crediti formativi
     * @return true se le informazioni sono valide, false altrimenti
     */
	private boolean checkInfo(String name, String surname, String subject, int vote, int lode, int cfu) {
		boolean ret = true;
		String regex = ".*\\d+.*"; 
		
		//controllo eventuali campi vuoti
		if(name == null || surname == null || subject == null)
			ret = false;
		
		//controllo se dentro la stringa ci sono dei numeri con la regex
		if(name.matches(regex) || surname.matches(regex) || subject.matches(regex)) {
			ret = false;
		}
		//voto out of bounds
		if(vote < 18 || vote > 30)
			ret = false;
		
		//lode ingiustificata
		if(vote != 30 && lode == 1)
			ret = false;
		
		//Limite CFU_MIN e CFU_MAX assegnabili sforato.
		if(cfu < CFU_MIN || cfu > CFU_MAX)
			ret = false;
		
		return ret;
	}
}
