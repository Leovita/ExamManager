package cls;

/**
     * Questa classe rappresenta un record di esame.
     * Contiene informazioni su nome, cognome, materia, voto, stato di lode e numero di crediti dello studente.
     */
public class Exam {
    public String name;
    public String surname;
    public String subject;
    public int grade;
    public int lode;   
    public int cfu;

    /**
     * Costruttore della classe Exam.
     * Inizializza gli attributi della classe con i valori passati come parametri.
     * 
     * @param name Nome dello studente.
     * @param surname Cognome dello studente.
     * @param subject Materia di insegnamento.
     * @param grade Voto esame.
     * @param cfu Crediti universitari esame.
     */
    public Exam(String name, String surname, String subject, int grade, int lode, int cfu) {
        this.name = name;
        this.surname = surname;
        this.subject = subject;
        this.grade = grade;
        this.lode = lode;
        this.cfu = cfu;
    }

    // Getters and Setters

    /**
     * Restituisce il nome dello studente.
     * 
     * @return Stringa nome studente.
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome dello studente.
     * 
     * @param name nome dello studente.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce il cognome dello studente.
     * 
     * @return Stringa cognome studente.
     */ 
    public String getSurname() {
        return surname;
    }

    /**
     * Imposta il cognome dello studente.
     * 
     * @param surname cognome dello studente.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Restituisce la materia dell'esame.
     * 
     * @return Stringa materia esame.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Imposta la materia dell'esame.
     * 
     * @param subject materia dell'esame.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Restituisce il voto dell'esame.
     * 
     * @return Voto intero.
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Imposta il voto dell'esame.
     * 
     * @param grade voto dell'esame.
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    /**
     * Restituisce lo stato di lode dell'esame.
     */
    public int isLode() {
        return lode;
    }

    /**
     * Imposta lo stato di lode dell'esame.
     * 
     * @param lode Se lode 1 altrimenti 0.
     */
    public void setLode(int lode) {
        this.lode = lode;
    }

    /**
     * Restituisce il numero di crediti dell'esame.
     * 
     * @return numero crediti esame.
     */
    public int getNumeroCrediti() {
        return this.cfu;
    }

    /**
     * Imposta il numero di crediti dell'esame.
     * 
     * @param cfuSet Numero crediti dell'esame
     */
    public void setNumeroCrediti(int cfuSet) {
        this.cfu = cfuSet;
    }
}