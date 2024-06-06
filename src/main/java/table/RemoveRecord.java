package table;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

/**
 * Questa classe rappresenta una finestra per la rimozione di un record dalla tabella.
 * 
 * @author leonardo Vitale
*/
public class RemoveRecord extends JFrame {

	private static final long serialVersionUID = 1L;
	/**
     * Pannello principale per i componenti della finestra.
     */
	private JPanel contentPane;
	
	/**
     * Campo per l'inserimento dell'id del record da rimuovere.
     */
	private JTextField textField;

	/**
     * Riferimento all'istanza della classe GestioneEsami.
    */
	private GestioneEsami ge;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RemoveRecord frame = new RemoveRecord();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
     * Costruttore del frame per la rimozione di un record.
     */
	public RemoveRecord() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 369, 256);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Insert idExams to remove a record:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_1.setBounds(10, 84, 243, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Remove Table Record");
		lblNewLabel.setBounds(41, 11, 296, 31);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(246, 82, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Ok");
		Object[] options = {"OK"};
		
		/**
		 * Metodo per l'impostazione di un ascoltatore per gli eventi per la rimozione di record per id.
		 * 
		 * @param listener Ascoltatore per gli eventi.
		*/
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//variabile che rappresenta le righe modificate eseguendo la query.
					int result = sqlRemoveRecordById();
					
					//Se almeno una riga e' stata modificata..
					if (result > 0) {
						GestioneEsami.updateTable(null, true);
						JOptionPane.showOptionDialog(contentPane, 
								"Record from table successfully removed!", 
                                "InfoFrame", 
                                JOptionPane.DEFAULT_OPTION, 
                                JOptionPane.INFORMATION_MESSAGE, 
                                null,  
                                null,
                                options[0]);
		                dispose();
						
		            } else {
		            	JOptionPane.showOptionDialog(contentPane, 
								"No record found to remove, please check your informations!", 
                                "ErrorFrame", 
                                JOptionPane.DEFAULT_OPTION, 
                                JOptionPane.ERROR_MESSAGE, 
                                null,  
                                null,
                                options[0]);
		            }
				}catch(Exception err) {
					System.out.print(err);
				}
			}
		});
		btnNewButton.setBounds(255, 170, 89, 23);
		contentPane.add(btnNewButton);
	}
	/**
	 * Rimuove il record nel database con id corrispondente al valore del textField.
	 * 
	 * @return numero intero di righe aggiornate nel db.
	 */
	int sqlRemoveRecordById() throws SQLException {
		int rowsUpdated = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
			String queryRemove = "DELETE FROM exams WHERE idExams = ?";
			
			try(PreparedStatement ps = con.prepareStatement(queryRemove)){
				ps.setString(1, textField.getText());
				
				rowsUpdated = ps.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rowsUpdated;
	}
}
