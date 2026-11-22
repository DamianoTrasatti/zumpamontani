import java.beans.Expression;
import java.io.DataOutputStream;
import java.util.concurrent.locks.ReentrantLock;

public class Giocatore {
    private String nome;
    private int indice; // posizione nell'ordine di gioco
    private int vite;
    private DataOutputStream canaleOutVersoClient;
    private Carta cartaGiocatore;

    private boolean eliminato = false; // all'inizio nessuno è eliminato

    // devo mettere il lock per quando invio da questo canaleoutversoclient perche
    // poterebbero farlo piu giocatori contemporaneamete
    public ReentrantLock mutexGiocatori = new ReentrantLock();

    public Giocatore(String nome, int indice, int vite, DataOutputStream canaleOutVersoClient, Carta cartaGiocatore) {
        this.nome = nome;
        this.indice = indice;
        this.vite = vite;
        this.canaleOutVersoClient = canaleOutVersoClient;
        this.cartaGiocatore = cartaGiocatore;
    }

    public void invia(String message) {
        mutexGiocatori.lock();
        try {
            canaleOutVersoClient.writeBytes(message + "\n");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        } finally {
            mutexGiocatori.unlock();
        }
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

    public DataOutputStream getCanaleOutVersoClient() {
        return canaleOutVersoClient;
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
