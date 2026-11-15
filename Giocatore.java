public class Giocatore {
    private String nome;
    private int indice; // posizione nell'ordine di gioco
    private int vite;
    private boolean eliminato;
    private Carta cartaGiocatore;

    public Giocatore(String nome, int indice, int vite, Carta cartaGiocatore) {
        this.nome = nome;
        this.indice = indice;
        this.vite = vite;
        this.eliminato = false; // all'inizio nessuno e' eliminato
        this.cartaGiocatore = cartaGiocatore;
    }

    // ###### GET ######
    public String getNome() {
        return nome;
    }

    public int getIndice() {
        return indice;
    }

    public int getVite() {
        return vite;
    }

    public boolean isEliminato() {
        return eliminato;
    }

    public Carta getCartaGiocatore() {
        return cartaGiocatore;
    }

    // ###### SET ######
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void setVite(int vite) {
        this.vite = vite;
    }

    public void setEliminato(boolean eliminato) {
        this.eliminato = eliminato;
    }

    public void setCartaGiocatore(Carta cartaGiocatore) {
        this.cartaGiocatore = cartaGiocatore;
    }
}
