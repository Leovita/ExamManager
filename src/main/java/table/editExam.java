package table;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

/**
 * Classe per la modifica di esami.
 * 
 * @author Leonardo Vitale
 */
public class editExam extends JFrame {

	private static final long serialVersionUID = 1L;
	/*
	 * Pannello principale per i componenti della finestra..
	 */
	private JPanel contentPane;
	/*
	 * TextField per l'inserimento dell'id dell'esame da editare.
	 */
	private JTextField textField;
	/**
	 * Avvio applicazione
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Creazione frame.
					editExam frame = new editExam();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Costruttore frame per la modifica di un esame semplice.
	 * 
	 */
	public editExam() {
		setTitle("editExam");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 441, 211);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Edit Exam");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 21));
		lblNewLabel.setBounds(145, 0, 132, 50);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Insert the Id of the exam you would like to edit:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(10, 61, 394, 20);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(152, 102, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		/*
		 * Aggiunta di un pulsante di modifica.
		 * Quando premuto apre un frame con layout in base all'esame selezionato.
		 * 
		 * @param e Evento di azione.
		 */
		JButton btnNewButton = new JButton("EDIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String testo = textField.getText();
				if(testo.isEmpty()) {
					System.exit(ABORT);
				} else {
					//devo capire se il nostro esame e' complex o semplice e creare il frame corrispondente 	
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
						
						String query = "SELECT * FROM multi_exams WHERE idExams = ?";
						String query2;
						
						PreparedStatement statement = con.prepareStatement(query);
						PreparedStatement statement2;
						statement.setInt(1, Integer.parseInt(textField.getText()));
						
						ResultSet resultSet = statement.executeQuery();
						ResultSet resultSet1;
						
						//se e' composto
						if (resultSet.next()) {
							String[] w = new String[3];
							String[] g = new String[3];

							query = "SELECT * FROM exams WHERE idExams = ?";
							query2 = "SELECT * FROM multi_exams WHERE idExams = ?";
							
							statement = con.prepareStatement(query);
							statement2 = con.prepareStatement(query2);
							
							statement.setInt(1, Integer.parseInt(textField.getText())); //statement per recuperare dal main db le info
							statement2.setInt(1, Integer.parseInt(textField.getText())); //statement per recuperare dal db secondario le info (w, g)
							
							resultSet = statement.executeQuery();
							resultSet1 = statement2.executeQuery();
							
							if(resultSet.next() && resultSet1.next()) {
								String name = resultSet.getString("studentName");
								String surname = resultSet.getString("studentSurname");
								String subject = resultSet.getString("subject");
								int lode = resultSet.getInt("Lode");
								String cfu = resultSet.getString("CFU");
								
								//Associazione pesi recuperati dal db.
								w[0] = resultSet1.getString("w1");
								w[1] = resultSet1.getString("w2");
								w[2] = resultSet1.getString("w3");
								g[0] = resultSet1.getString("vote1");
								g[1] = resultSet1.getString("vote2");
								g[2] = resultSet1.getString("vote3");
								
								//Creazione frame con i parametri risultati delle query.
								editComplexExam editComplexExamFrame = new editComplexExam(Integer.parseInt(textField.getText()), name, surname, subject, w, g, lode, cfu);
								editComplexExamFrame.setVisible(true);
								
							}
			                
			            } else {
			            	query = "SELECT * FROM exams WHERE idExams = ?";
			            	
			            	statement = con.prepareStatement(query);
			            	statement.setInt(1, Integer.parseInt(textField.getText()));
			            	
							resultSet = statement.executeQuery();
			            	
							if (resultSet.next()) {
								String name = resultSet.getString("studentName");
								String surname = resultSet.getString("studentSurname");
								String subject = resultSet.getString("subject");
								int grade = resultSet.getInt("Grade");
								int lode = resultSet.getInt("Lode");
								int cfu = resultSet.getInt("CFU");
								
								editSimpleExam editSimpleExamFrame = new editSimpleExam(Integer.parseInt(textField.getText()), name, surname, subject, grade, lode, cfu);
								editSimpleExamFrame.setVisible(true);
								
							
							}
			            }
						
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
					dispose();
				}
			}
		});
		btnNewButton.setBounds(313, 137, 89, 23);
		contentPane.add(btnNewButton);
	}
}