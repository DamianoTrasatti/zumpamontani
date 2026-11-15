public class Carta {
    private int numero; // 1..10
    private String seme; // sempre "Denari", "Spade", "Coppe" o "Bastoni"
    private String percorso_immagine;
    private int punteggioCarta; // denari = *4, spade = *3, coppe = *2, bastoni = *1

    public Carta(int numero, String seme) {
        cambiaCarta(numero, seme);
    }

    // cambia completamente la carta: numero, seme, immagine e punteggio
    public void cambiaCarta(int numero, String seme) {
        this.numero = numero;
        this.seme = seme;
        this.percorso_immagine = calcolaPercorso(this.numero, this.seme);
        this.punteggioCarta = calcolaPunteggio(this.numero, this.seme);
    }

    // es. 1 Bastoni -> image/Bastoni01.png
    // seme deve arrivare scritto bene! Bastoni no BASTONI no bastoni
    private String calcolaPercorso(int numero, String seme) {
        String numeroDueCifre = String.format("%02d", numero); // 1 -> 01
        return "image/" + seme + numeroDueCifre + ".png";
    }

    // punteggio = numero * moltiplicatore del seme
    private int calcolaPunteggio(int numero, String seme) {
        int moltiplicatore = 0; // il compilatore richiede un valore iniziale
        switch (seme) {
            case "Denari":
                moltiplicatore = 4;
                break;
            case "Spade":
                moltiplicatore = 3;
                break;
            case "Coppe":
                moltiplicatore = 2;
                break;
            case "Bastoni":
                moltiplicatore = 1;
                break;
        }
        return numero * moltiplicatore;
    }

    // ###### GET ######
    public int getNumero() {
        return numero;
    }

    public String getSeme() {
        return seme;
    }

    public String getPercorso_immagine() {
        return percorso_immagine;
    }

    public int getPunteggioCarta() {
        return punteggioCarta;
    }

    // ###### SET ######
    // cambiando numero o seme il punteggio viene ricalcolato
    public void setNumero(int numero) {
        this.numero = numero;
        this.punteggioCarta = calcolaPunteggio(this.numero, this.seme);
    }

    public void setSeme(String seme) {
        this.seme = seme;
        this.punteggioCarta = calcolaPunteggio(this.numero, this.seme);
    }

    public void setPercorso_immagine(String percorso_immagine) {
        this.percorso_immagine = percorso_immagine;
    }

    public void setPunteggioCarta(int punteggioCarta) {
        this.punteggioCarta = punteggioCarta;
    }
}
