package saveload;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.io.FileReader;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.awt.event.ActionEvent;

/**
 * Questa classe rappresenta una finestra per salvare e caricare dati da un database MySQL.
 * Fornisce due pulsanti: "Salva" e "Carica".
 * Il pulsante "Salva" salva i dati della tabella "esami" in un file di testo.
 * Il pulsante "Carica" carica i dati da un file di testo e li inserisce nella tabella degli esami.
 */
public class SaveAndLoad extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Avvia l'applicazione.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SaveAndLoad frame = new SaveAndLoad();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Custruttore per il Frame.
	 */
	public SaveAndLoad() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 329, 299);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Object[] options = {"OK"};
		/*
		 * Quando il pulsante "Salva" viene premuto, questa azione viene eseguita.
		 * Salva le informazioni su un file di testo.
		 */
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					String sql = "SELECT * FROM exams";
            		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
            		Statement st = con.createStatement();
            		
					ResultSet result = st.executeQuery(sql);
                    ResultSetMetaData metaData = (ResultSetMetaData) result.getMetaData();
                    
                    JFileChooser fileChooser = new JFileChooser();
                    int userSelection = fileChooser.showSaveDialog(null);
                    
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                    	File fileToSave = fileChooser.getSelectedFile();
                    	String filePath = fileToSave.getAbsolutePath();
                    	//Controllo se il file gia' esiste e se si desidera sovrascrivere.
                    	if(fileToSave.exists()) {
    		            	int response = JOptionPane.showOptionDialog(null, 
    		                        "The file already exists. Do you want to overwrite it?", 
    		                        "File Exists", 
    		                        JOptionPane.YES_NO_OPTION, 
    		                        JOptionPane.WARNING_MESSAGE, 
    		                        null, 
    		                        new Object[]{"Yes", "No"}, 
    		                        "No");
    		                if (response != JOptionPane.YES_OPTION) {
    		                    return;
    		                }
    		                
    		            }
                    	/*
						 * Prima scrivere le colonne del database.
						 */
						int columnCount1 = metaData.getColumnCount();
                        BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave));
                        for (int i = 1; i <= columnCount1; i++) {
                            writer.write(metaData.getColumnName(i));
                            if (i < columnCount1) {
                                writer.write(",");
                            }
                        }
                        
                        writer.newLine();

                        //Poi copiare ogni valore diviso da virgole.
                        while (result.next()) {
                            for (int i = 1; i <= columnCount1; i++) {
                                writer.write(result.getString(i));
                                if (i < columnCount1) {
                                    writer.write(",");
                                }
                            }
                            writer.newLine();
                        }

                        writer.close();
                        JOptionPane.showOptionDialog(contentPane, 
								"Information successfully saved!", 
                                "InfoUpdate", 
                                JOptionPane.DEFAULT_OPTION, 
                                JOptionPane.INFORMATION_MESSAGE, 
                                null,  
                                null,
                                options[0]);
				}
			} catch(Exception e1) {
				e1.printStackTrace();
			}
			}
		});
		
		
		btnNewButton.setBounds(5, 64, 303, 63);
		contentPane.add(btnNewButton);
		
		/*
		 * Quando il pulsante "Carica" viene premuto, questa azione viene eseguita.
		 * Carica le informazioni da un file di testo.
		 */
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
		        int result = fileChooser.showOpenDialog(null);
		        
		        if (result == JFileChooser.APPROVE_OPTION) {
		            java.io.File selectedFile = fileChooser.getSelectedFile();
		            String fileName = selectedFile.getAbsolutePath();
		            String sqlDelete = "DELETE FROM exams;";
		            
		            if (!fileName.toLowerCase().endsWith(".txt")) {
		                JOptionPane.showMessageDialog(null, 
		                        "The selected file is not a .txt file. Please select a valid .txt file.", 
		                        "Invalid File", 
		                        JOptionPane.ERROR_MESSAGE);
		                return; 
		            }
		            
		            
		            try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
						Statement st = con.createStatement();
						st.executeUpdate(sqlDelete);
						int res = 0;
						
						PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO exams (idExams, studentName, studentSurname, subject, Grade, Lode, CFU) VALUES (?, ?, ?, ?, ?, ?, ?)");
						String line;
						/*
						 * Oggetto <tipo>BufferedReader per leggere il file.
						 */
						try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
							br.readLine();
							
							//Scannerizza il file fino a che non trova una riga vuota(null). 
							while ((line = br.readLine()) != null) {
								//Split ogni riga diviso da virgola.
				                String[] data = line.split(",");
				
				                for (int i = 0; i < data.length; i++) {
									//Inserimento riga all'interno del <tipo>PreparedStatement.
				                    preparedStatement.setString(i + 1, data[i]);
				                }
								//Eseguo query appena creata.
				                res = preparedStatement.executeUpdate();
				            }
							
							if(res > 0) {
								JOptionPane.showOptionDialog(contentPane, 
										"Information successfully Uploaded!", 
		                                "InfoUpload", 
		                                JOptionPane.DEFAULT_OPTION, 
		                                JOptionPane.INFORMATION_MESSAGE, 
		                                null,  
		                                null,
		                                options[0]);
							} else {
								JOptionPane.showMessageDialog(null, 
				                        "Error occurred during file upload, please check the source extension.", 
				                        "FileError", 
				                        JOptionPane.ERROR_MESSAGE);
							}
							
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		            
		            } catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
		            
		        }
			}
		});
	

		
		btnLoad.setBounds(5, 153, 303, 63);
		contentPane.add(btnLoad);
		
		JLabel lblNewLabel = new JLabel("Save & Load menu");
		lblNewLabel.setFont(new Font("Times New Roman", Font.ITALIC, 15));
		lblNewLabel.setBounds(101, 22, 150, 14);
		contentPane.add(lblNewLabel);
	}
}
