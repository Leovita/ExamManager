package table;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Menu;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import saveload.SaveAndLoad;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * La classe GestioneEsami rappresenta una finestra per la gestione in forma tabellare degli esami.
 * Consente di aggiungere, modificare, eliminare e filtrare esami, nonché di stampare la tabella degli esami.
 */
/**
 * La classe GestioneEsami estende JFrame e gestisce la visualizzazione e la gestione degli esami.
 * 
 * @author Leonardo Vitale.
 */
public class GestioneEsami extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    /**
     * Tabella per visualizzare gli esami.
     */
    public static JTable table;
    private AddExamEasy frame;
    /**
     * Array delle colonne della tabella degli esami.
     */
    private String[] coloumn = {"id", "Name", "Surname", "Subject", "Grade", "Lode", "CFU"};
    /**
     * Oggetto per gestire le query MySQL.
     */
    public MySql sql;
    /**
     * Modello di default per la tabella degli esami.
     */
    public static DefaultTableModel model;
    /*
     * TextField del filtro
     */
    private JTextField textField;
    /*
     * TextField contenente la media ponderata degli esami.
     */
    private JTextField textWeighted;
    /*
     * TextField contente il somma dei crediti accumulati da uno studente.
     */
    private JTextField textTotalCredits;
    /**
     * Flag che indica se la tabella è stata aggiornata.
     */
    private static boolean tableUpdated = false;

    /**
     * Funzione main per la creazione di un frame di tipo GestioneEsami.
     * @param args argomenti della riga di comando.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GestioneEsami frame = new GestioneEsami();
                    frame.setVisible(true);    
                    /*
                     * WindowListener che intercetta la chiusura del frame.
                     */
                    frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            //Se non ci sono state modifiche nella tabella, chiudo il frame.
                            if(!tableUpdated) {
                                frame.dispose();
                            } else {
                                /*Altrimenti apro una finestra di dialogo che fa scegliere all'utente di salvare la tabella
                                * aggiornata localmente oppure annullare le modifiche.
                                */
                                int response = JOptionPane.showConfirmDialog(frame, 
                                        "Data got modified and will not be saved locally!", 
                                        "ConfirmNotSavedInfo",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);
                                    
                                if (response == JOptionPane.YES_OPTION) {
                                    frame.dispose(); 
                                } else {
                                    //Se si vuole procedere si apre il frame per il salvataggio.
                                    SaveAndLoad saveInviteFrame = new SaveAndLoad();
                                    saveInviteFrame.setVisible(true);
                                }
                            }
                        }
                    });
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Costruttore per la classe GestioneEsami.
     * Inizializza la finestra con tutti i componenti necessari.
     */
    public GestioneEsami() {
        setTitle("TblExams");
        setIconImage(Toolkit.getDefaultToolkit().getImage(GestioneEsami.class.getResource("/imgs/table.jpeg")));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 680, 468);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        /**
         * Oggetto MySql utilizzato per recuperare i dati dal database
         */
        sql = new MySql();
        model = new DefaultTableModel(sql.my_db_select(), coloumn);
        table = new JTable(model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 36, 483, 382);
        contentPane.add(scrollPane);

        JButton btnNewButton = new JButton("Add Exam");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame = new AddExamEasy();
                frame.setVisible(true);
            }
        });
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            /**
             * Listener che mostra il pop-up di un esame composto quando viene cliccata la riga della tabella.
             * @param Oggetto ListSelectionEvent.
             */
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    try {
                    	/**
                    	 * Contiene la riga cliccata.
                    	 */
                        int selectedRow = table.getSelectedRow();
                        if (selectedRow != -1) { 
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
                            Statement st = con.createStatement();
                            /**
                             * Oggetto corrispondente alla riga cliccata.
                             */
                            Object id = table.getValueAt(selectedRow, 0);

                            String queryWV = "SELECT * FROM multi_exams WHERE idExams =" + id + ";"; 
                            ResultSet rs = st.executeQuery(queryWV);
                            
                            if(rs.next()) {
                            	/**
                            	 * Creazione del frame di pop-up contenente i voti e i pesi di ciascun esame intermedio.
                            	 */
                                popup p = new popup(rs.getString("w1"), rs.getString("w2"), rs.getString("w3"), rs.getString("vote1"), rs.getString("vote2"), rs.getString("vote3"));
                                p.setVisible(true);
                            }
                        }
                    } catch (SQLException | ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        btnNewButton.setBounds(518, 36, 137, 27);
        btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 11));
        contentPane.add(btnNewButton);

        /**
         * Bottone per la creazione del frame per l'eliminazione di un esame.
         */
        JButton btnDeleteExam = new JButton("Delete Exam");
        btnDeleteExam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/**
            	 * Creazione Frame di rimozione dati.
            	 */
                RemoveRecord r = new RemoveRecord();
                r.setVisible(true);
            }
        });
        btnDeleteExam.setFont(new Font("Times New Roman", Font.BOLD, 11));
        btnDeleteExam.setBounds(518, 150, 137, 27);
        contentPane.add(btnDeleteExam);
        
        /**
         * Bottone per la modifica di un esame, sia semplice che composto.
         */
        JButton btnEditExam = new JButton("Edit Exam");
        btnEditExam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	/*
            	 * Creazione frame per modifica dati.
            	 */
                editExam ee = new editExam();
                ee.setVisible(true);
            }
        });
        btnEditExam.setFont(new Font("Times New Roman", Font.BOLD, 11));
        btnEditExam.setBounds(518, 110, 137, 27);
        contentPane.add(btnEditExam);

        /*
         * Bottone per l'aggiunta di un esame complesso, ossia costituito da almeno 2 esami intermedi.
         */
        JButton btnAddMultipleExams = new JButton("Add Multiple Exams");
        btnAddMultipleExams.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddMultiExam f = new AddMultiExam();
                f.setVisible(true);
            }
        });
        btnAddMultipleExams.setFont(new Font("Times New Roman", Font.BOLD, 11));
        btnAddMultipleExams.setBounds(518, 72, 137, 27);
        contentPane.add(btnAddMultipleExams);

        /*
         * Text field per filtrare le informazioni della tabella.
         */
        textField = new JTextField();
        textField.setBounds(183, 11, 122, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkEmptyField();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkEmptyField();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkEmptyField();
            }
            
            /*
             * Funzione che controlla qualora il campo sia vuoto e riaggiorna la tabella di conseguenza.
             */
            private void checkEmptyField() {
                if (textField.getText().isEmpty()) {
                    try {
                        textWeighted.setText("");
                        textTotalCredits.setText("");
                        GestioneEsami.updateTable(null, false);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        
        /*
         * Bottone per il filtraggio dei dati.
         */
        JButton btnNewButton_1 = new JButton("Filter");
        btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
        btnNewButton_1.setBounds(315, 10, 88, 23);
        contentPane.add(btnNewButton_1);
        
        /*
         * Pannello principale 
         */
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Informations", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setBounds(519, 201, 136, 138);
        contentPane.add(panel);
        panel.setLayout(null);
        
        /*
         * Etichetta del campo contenente la media ponderata nel campo "Informations".
         */
        JLabel lblNewLabel = new JLabel("  Weighted average:");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        lblNewLabel.setBounds(10, 26, 116, 14);
        panel.add(lblNewLabel);
        
        /*
         * Etichetta del campo contenente i crediti totali nel campo "Informations".
         */
        JLabel lblTotalCredits = new JLabel("  Total credits:");
        lblTotalCredits.setFont(new Font("Times New Roman", Font.BOLD, 13));
        lblTotalCredits.setBounds(22, 77, 88, 14);
        panel.add(lblTotalCredits);
        
        /*
         * TextField contenente la media ponderata.
         */
        textWeighted = new JTextField();
        textWeighted.setEditable(false);
        textWeighted.setBounds(30, 51, 69, 20);
        panel.add(textWeighted);
        textWeighted.setColumns(10);
        /*
         * TextField contenente i crediti totali accumulati.
         */
        textTotalCredits = new JTextField();
        textTotalCredits.setEditable(false);
        textTotalCredits.setColumns(10);
        textTotalCredits.setBounds(30, 102, 69, 20);
        panel.add(textTotalCredits);

        /*
         * Oggetto per le opzioni del JOptionPane.
         */
        Object[] options = {"OK"};

        JButton btnNewButton_1_1 = new JButton("Print ");
        btnNewButton_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PrinterJob printerJob = PrinterJob.getPrinterJob();
				
				/*
				 * Risultato della prova di stampa, true se ha funzionato altrimenti false.
				 */
				boolean complete = false;
				
				if (printerJob.printDialog()) {
				    try {
				    	printerJob.print();
				    } catch (Exception ex) {
				        ex.printStackTrace();
				    }
				} 
            }
        });

        /*
         * Bottone per attivare il filtraggio dei dati.
         */
        btnNewButton_1_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
        btnNewButton_1_1.setBounds(405, 10, 88, 23);
        contentPane.add(btnNewButton_1_1);

        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
                    /*
                     * Variabile contenente il filtro con il quale andare a creare una query da usare sul database per ottenere i record corrispondenti.
                     */
                    String f = textField.getText();
                    
                    /*
                     * Query per la ricerca dei dati.
                     */
                    String queryCalc = "SELECT Grade, CFU FROM exams WHERE studentName = ? OR studentSurname = ? OR subject = ?";
                    /*
                     * Variabili per contare il cfu ottenuti da un determinato filtro e la media ponderata degli esami.
                     */
                    int sumProduct = 0, cfuCount = 0; 
                    
                    if(!f.isEmpty()) {
                        try (PreparedStatement preparedStatement = con.prepareStatement(queryCalc)) {
                            for(int i = 1; i <= 3; i++)
                                preparedStatement.setString(i, f);
                            
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                while (resultSet.next()) {
                                	/*
                                	 * Varibili iterative.
                                	 */
                                    int voteNow, cfuNow;
                                    voteNow = resultSet.getInt("Grade");
                                    cfuNow = resultSet.getInt("CFU");
                                    
                                    sumProduct += (voteNow * cfuNow);
                                    cfuCount += cfuNow;
                                }
                                int result = sumProduct/cfuCount;
                                textWeighted.setText(Integer.toString(result));
                                textTotalCredits.setText(Integer.toString(cfuCount));
                            }
                        }

                        try {
                            model.setRowCount(0);
                            String query = "SELECT * FROM exams WHERE studentName='" + f + "' OR studentSurname='" + f + "' OR subject='" + f + "';";
                            updateTable(query, false);
                            
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        updateTable(null, false);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } 
        });
    }

    /**
     * Aggiorna la tabella degli esami con i dati forniti dalla query.
     * @param newQuery la query SQL per selezionare i dati da visualizzare.
     * @param update booleano per indicare se la tabella è stata aggiornata.
     * @throws SQLException se si verifica un errore durante l'esecuzione della query.
     */
    public static void updateTable(String newQuery, boolean update) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
        Statement st = con.createStatement();
        model.setRowCount(0);
        
        //Se e' un aggiornamento allora imposto tableUpdated = true, altrimenti lascio il suo valore.
        GestioneEsami.tableUpdated = update ? true : GestioneEsami.tableUpdated;
        
        
        if(newQuery == null) {
            newQuery = "SELECT * FROM exams;";
        }
        
        ResultSet newResultSet = st.executeQuery(newQuery);

        int numColumns = newResultSet.getMetaData().getColumnCount();
        
        while (newResultSet.next()) {
            Object[] rowData = new Object[numColumns];
            for (int i = 1; i <= numColumns; i++) {
                rowData[i - 1] = newResultSet.getObject(i);
            }
            model.addRow(rowData);
        }
    }
}
