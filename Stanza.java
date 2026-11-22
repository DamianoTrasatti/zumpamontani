import java.util.ArrayList;

public class Stanza {
    private int pin;
    private int viteIniziali; // 1..5
    private int maxGiocatori; // 2..30
    private int secondiAzione; // timer per la mossa
    private int secondiKick; // time-out di espulsione

    private int indiceMazziere; // Lo inizializziamo a 1
    private boolean isPlaying; // true se hanno iniziato a giocare

    private ArrayList<Giocatore> giocatoriStanza;
    private ArrayList<Carta> mazzo;

    // dati scelti dal primo giocatore quando crea la stanza
    public Stanza(int pin, int viteIniziali, int maxGiocatori, int secondiAzione, int secondiKick) {
        this.pin = pin;
        this.viteIniziali = viteIniziali;
        this.maxGiocatori = maxGiocatori;
        this.secondiAzione = secondiAzione;
        this.secondiKick = secondiKick;
        this.indiceMazziere = 1;
        this.isPlaying = false;
        this.giocatoriStanza = new ArrayList<>(); // all'inizio nessun giocatore
        this.mazzo = new ArrayList<>(); // il mazzo si riempie dopo
    }

    // ###### GET ######
    public int getPin() {
        return pin;
    }

    public int getViteIniziali() {
        return viteIniziali;
    }

    public int getMaxNumGiocatori() {
        return maxGiocatori;
    }

    public int getSecondiAzione() {
        return secondiAzione;
    }

    public int getSecondiKick() {
        return secondiKick;
    }

    public int getIndiceMazziere() {
        return indiceMazziere;
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public ArrayList<Giocatore> getGiocatoriStanza() {
        return giocatoriStanza;
    }

    public int getNumGiocatoriStanza() {
        return giocatoriStanza.size(); // size() conta gia' tutti gli elementi (indice 0 compreso)
    }

    public ArrayList<Carta> getMazzo() {
        return mazzo;
    }

    // ###### SET ######
    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setViteIniziali(int viteIniziali) {
        this.viteIniziali = viteIniziali;
    }

    public void setMaxGiocatori(int maxGiocatori) {
        this.maxGiocatori = maxGiocatori;
    }

    public void setSecondiAzione(int secondiAzione) {
        this.secondiAzione = secondiAzione;
    }

    public void setSecondiKick(int secondiKick) {
        this.secondiKick = secondiKick;
    }

    public void setIndiceMazziere(int indiceMazziere) {
        this.indiceMazziere = indiceMazziere;
    }

    public void setIsPLaying(boolean val) {
        this.isPlaying = val;
    }

    public void setGiocatoriStanza(ArrayList<Giocatore> giocatoriStanza) {
        this.giocatoriStanza = giocatoriStanza;
    }

    public void setMazzo(ArrayList<Carta> mazzo) {
        this.mazzo = mazzo;
    }
}
