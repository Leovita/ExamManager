package cls;

/**
 * Questa classe rappresenta un esame parziale.
 * Contiene informazioni sul voto e sul peso dell'esame.
 *
 * @author Leonardo Vitale.
 */
public class midExam {
  private int voto;
  private int peso;


  /**
   * Costruttore della classe midExam.
   * Inizializza gli attributi della classe con i valori passati come parametri.
   *
   * @param voto Voto dell'esame
   * @param peso Peso dell'esame
   */
  public midExam(int voto, int peso) {
      this.voto = voto;
      this.peso = peso;
  }

  /**
   * Restituisce il voto dell'esame.
   *
   * @return Voto dell'esame
   */
  public float getVoto() {
      return voto;
  }

  /**
   * Imposta il voto dell'esame.
   *
   * @param voto Voto dell'esame
   */
  public void setVoto(int voto) {
      this.voto = voto;
  }

  /**
   * Restituisce il peso dell'esame.
   *
   * @return Peso dell'esame
   */
  public int getPeso() {
      return peso;
  }

  /**
   * Imposta il peso dell'esame.
   *
   * @param peso Peso dell'esame
   */
  public void setPeso(int peso) {
      this.peso = peso;
  }

  /**
   * Restituisce una rappresentazione testuale dell'oggetto midExam, ossia la prova intermedia.
   *
   * @return Rappresentazione testuale dell'oggetto midExam
   */
  @Override
  public String toString() {
      return "\n  Voto: " + voto +
              "\n  Peso: " + peso;
  }
}