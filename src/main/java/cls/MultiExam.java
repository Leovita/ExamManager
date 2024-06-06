//package EsameProg;
package cls;

import java.util.ArrayList;

/**
 * Questa classe rappresenta un esame composto, che estende la classe {@link Exam}.
 * Contiene informazioni su nome, cognome, materia, voto, stato di lode, numero di crediti, e prove intermedie.
 *
 * @author Leonardo Vitale.
 */

public class MultiExam extends Exam {
    /**
     * Lista di prove intermedie per l'esame multi-esame.
     */
    private ArrayList<midExam> proveIntermedie;

    /**
     * Costruttore della classe MultiExam.
     * Inizializza gli attributi della classe con i valori passati come parametri.
     *
     * @param nome Nome dello studente
     * @param cognome Cognome dello studente
     * @param insegnamento Materia dell'esame
     * @param CFU Numero di crediti dell'esame
     * @param proveIntermedie Lista di prove intermedie dell'esame multi-esame
     */
    public MultiExam(String nome, String cognome, String insegnamento, int CFU, ArrayList<midExam> proveIntermedie) {
        super(nome, cognome, insegnamento, 0, 0, CFU);
        this.proveIntermedie = proveIntermedie;
    }

    /**
     * Calcola e imposta il voto dell'esame multi-esame in base alle prove intermedie.
     * Il voto viene calcolato come la media pesata dei voti delle prove intermedie. 
     * Viene assegnato come attributo all'oggetto chiamante.
     * 
     */
    public void computeVote() {
        float weightedSum = 0;
        
        if (!proveIntermedie.isEmpty()) {
            for (midExam prova : proveIntermedie) 
                weightedSum += prova.getVoto() * (prova.getPeso() / 100.0); 
            this.setGrade((int)(weightedSum));
        }
    }
}