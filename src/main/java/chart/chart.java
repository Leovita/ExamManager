package chart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * Questa classe rappresenta un JFrame che mostra un grafico a barre basato sui voti degli studenti.
 * Si connette a un database MySQL e recupera i dati in base al filtro fornito.
 *
 * @author Leonardo Vitale
 * @version 1.0
 */
public class chart extends JFrame {

	private static final long serialVersionUID = 1L;

    /**
     * Costruttore della classe chart.
     *
     * @param title Il titolo del JFrame.
     * @param filterBy Il campo su cui filtrare (sia "Subject" che "Student").
     * @param filter Il valore su cui filtrare.
     * @throws SQLException Se si verifica un errore durante la connessione al database o durante l'esecuzione della query SQL.
     */

	public chart(String title, String filterBy, String filter) throws SQLException {
        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String sql;
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
			Statement st = con.createStatement();
			
			//Creo la query SQL in base a quello che devo cercare.
			if(filterBy == "Subject") {
				sql = "SELECT * FROM exams WHERE subject='" + filter + "';";
			} else {
				sql = "SELECT * FROM exams WHERE studentName='" + filter + "' OR studentSurname='" + filter + "';";
			}
			
			try (ResultSet resultSet = st.executeQuery(sql)) {
                while (resultSet.next()) {
                	int grade = resultSet.getInt("Grade");	
                	if(filterBy == "Subject") {
                		//Se filtro per materia di insegnamento, sull'asse X ho i cognomi degli studenti.
                		String surname = resultSet.getString("studentSurname");
                		dataset.addValue(grade, "Grade", surname);
                	} else {
                		//Se filtro per studente, sull'asse X ho gli insegnamenti.
                		String subject = resultSet.getString("subject");
                		dataset.addValue(grade, "Grade", subject);
                	}
                }
            }
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
        String x;
        
        x = (filterBy == "Subject") ? "Surname" : "Subject";
        
        /**
         * Creazione del grafico.
         */
        JFreeChart chart = ChartFactory.createBarChart(
                "Bar Chart",
                x,
                "Grade",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        setBounds(500, 500, 766, 531);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        chartPanel.setVisible(true);
        setContentPane(chartPanel);
        chartPanel.setLayout(null);
    }
}
