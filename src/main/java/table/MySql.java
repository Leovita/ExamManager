package table;
import java.sql.*;


/**
 * Classe SQL per gestiore l'interazione con il database.
 * 
 * @author Leonardo Vitale
 */

public class MySql {
	/*
	 * Dimensione righe dataset.
	 */
	private int ROWS_DATASET = 40;
	/*
	 * Dimensione colonne dataset.
	 */
	private int COLS_DATASET = 7;
	/**
	 * Crea una matrice corrispondente al dataset attuale nel database locale
	 * @return Dataset come matrice di stringhe
	 */
	public  String[][] my_db_select() {
		/**
		 * Matrice contenente il dataset.
		 */
		String[][] data = new String[ROWS_DATASET][COLS_DATASET]; 
				
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/db","root","root");  
			Statement st=con.createStatement();  
			/**
			 * Risultato della query.
			 */
			ResultSet rs=st.executeQuery("SELECT * FROM exams"); 
				int i=0;
				while(rs.next()){
					for(int j=0;j<COLS_DATASET;j++){
						//Creo il dataset.
						data[i][j]=rs.getString(j+1);
				 }
				 i=i+1;
				}
			con.close();  
			}catch(Exception e){ 
				e.printStackTrace();
			} 
			//return valori.
			return data;
		}
}
