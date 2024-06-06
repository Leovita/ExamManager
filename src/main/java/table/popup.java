package table;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;

/**
 * Classe per la creazione di un pop-up che mostri a schermo le informazioni
 * riguardanti le prove intermedie di ciascun esame composto.
 * 
 * @author Leonardo Vitale
 */
public class popup extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					popup frame = new popup("0","0","0","0","0","0");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
     * Costruttore del frame pop-up.
     * 
     * @param w1 Peso dell'esame 1.
     * @param w2 Peso dell'esame 2.
     * @param w3 Peso dell'esame 3.
     * @param vote1 Voto dell'esame 1.
     * @param vote2 Voto dell'esame 2.
     * @param vote3 Voto dell'esame 3.
    */
	public popup(String w1, String w2, String w3, String vote1, String vote2, String vote3) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 340, 223);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mid Exams recap");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 17));
		lblNewLabel.setBounds(94, 11, 146, 14);
		contentPane.add(lblNewLabel);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(227, 75, 75, 20);
		contentPane.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		textPane_1.setBounds(125, 75, 75, 20);
		contentPane.add(textPane_1);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setBounds(227, 106, 75, 20);
		textPane_2.setEditable(false);
		contentPane.add(textPane_2);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setBounds(125, 106, 75, 20);
		textPane_3.setEditable(false);
		contentPane.add(textPane_3);
		
		JTextPane textPane_4 = new JTextPane();
		textPane_4.setBounds(227, 137, 75, 20);
		textPane_4.setEditable(false);
		contentPane.add(textPane_4);
		
		JTextPane textPane_5 = new JTextPane();
		textPane_5.setBounds(125, 137, 75, 20);
		textPane_5.setEditable(false);
		contentPane.add(textPane_5);
		
		
		textPane.setText(w1); 
		textPane_1.setText(vote1); 
		textPane_2.setText(w2); 
		textPane_3.setText(vote2); 
		textPane_4.setText(w3); 
		textPane_5.setText(vote3); 
		
		JLabel lblNewLabel_1 = new JLabel("WEIGHT");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_1.setBounds(227, 50, 89, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("VOTE");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(132, 50, 89, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("EXAM");
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(33, 50, 89, 14);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_2 = new JLabel("1");
		lblNewLabel_2.setBounds(54, 75, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("2");
		lblNewLabel_2_1.setBounds(54, 106, 46, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("3");
		lblNewLabel_2_2.setBounds(54, 137, 46, 14);
		contentPane.add(lblNewLabel_2_2);
	}
}
