// arraylist di carte = mazzo

// numero e seme
public class Carta {
    public int numero;
    public String seme;

    public Carta(String seme, int numero){
        this.seme = seme;
        this.numero = numero;
    }

    public int getNumero() {
        return this.numero;
    }

    public String getSeme() {
        return this.seme;
    }
}