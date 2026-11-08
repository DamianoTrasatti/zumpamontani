import java.util.ArrayList;

public class Stanza {
    public int pin;
    private int viteIniziali; // 1..5
    private int maxGiocatori; // 2..30
    private int secondiAzione; // timer per la mossa
    private int secondiKick; // time-out di espulsione

    public int indiceMazziere = 1; // Lo inizializziamo a 1


    public ArrayList<Giocatore> giocatoriStanza = new ArrayList<>();
    public ArrayList<Carta> mazzo = new ArrayList<>();
}
