import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class GestioneServerDelClientThread extends Thread {
    Socket miosocket; // questo è il socket del client appena connesso
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    String stringaRicevuta = null;

    // stanza e giocatore di questo client (null finche' non crea o entra)
    Stanza stanza = null;
    Giocatore giocatore = null;

    public GestioneServerDelClientThread(Socket clientSocket) {
        this.miosocket = clientSocket;

        // invoca automaticamente il metodo run()
        this.start();
    }

    @Override
    public void run() {
        try {
            inDalClient = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));
            outVersoClient = new DataOutputStream(miosocket.getOutputStream());

            // messaggi possibili dal client
            // CREA-Nome-Pin-MaxGicoatori-Vite-Timer-Timer2
            // ENTRA-Nome-Pin
            // il client puo' riprovare finche' non riesce a creare o entrare in una stanza
            while (stanza == null) {
                stringaRicevuta = inDalClient.readLine();
                if (stringaRicevuta == null) { // il client si e' disconnesso
                    miosocket.close();
                    return;
                }
                System.out.println("Ricevuto: " + stringaRicevuta);

                String[] parti = stringaRicevuta.split("-");
                String risposta;
                switch (parti[0]) {
                    case "CREA":
                        risposta = creaStanza(parti);
                        break;
                    case "ENTRA":
                        risposta = entraStanza(parti);
                        break;
                    default:
                        risposta = "ERRORE-Comando sconosciuto";
                        break;
                }
                outVersoClient.writeBytes(risposta + "\n");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server");
            try {
                miosocket.close();
            } catch (Exception e2) {
                System.out.println(e.getMessage());
            }
        }

    }

    // cerca la stanza con quel pin, null se non esiste
    // ATTENZIONE: chiamarlo solo quando si ha gia' il lock di Server.mutexStanze
    private Stanza cercaStanza(int pin) {
        for (Stanza s : Server.stanze) {
            if (s.getPin() == pin) {
                return s;
            }
        }
        return null;
    }

    // CREA-Nome-Pin-MaxGiocatori-Vite-SecondiAzione-SecondiKick
    private String creaStanza(String[] parti) {
        if (parti.length != 7) {
            return "ERRORE-Formato CREA non valido";
        }
        String nome = parti[1];
        int pin, maxGiocatori, vite, secondiAzione, secondiKick;
        try {
            pin = Integer.parseInt(parti[2]);
            maxGiocatori = Integer.parseInt(parti[3]);
            vite = Integer.parseInt(parti[4]);
            secondiAzione = Integer.parseInt(parti[5]);
            secondiKick = Integer.parseInt(parti[6]);
        } catch (NumberFormatException e) {
            return "ERRORE-Pin giocatori vite e secondi devono essere numeri";
        }

        Server.mutexStanze.lock();
        try {
            if (cercaStanza(pin) != null) {
                return "ERRORE-Esiste gia una stanza con questo pin";
            }
            // attenzione all'ordine: il costruttore vuole (pin, vite, maxGiocatori, ...)
            Stanza nuova = new Stanza(pin, vite, maxGiocatori, secondiAzione, secondiKick);
            // il creatore e' il primo giocatore, la carta arriva quando si distribuisce
            Giocatore creatore = new Giocatore(nome, 1, vite, null);
            nuova.getGiocatoriStanza().add(creatore);
            Server.stanze.add(nuova);

            stanza = nuova;
            giocatore = creatore;
            System.out.println(nome + " ha creato la stanza " + pin);
            return "OK-Stanza creata";
        } finally {
            Server.mutexStanze.unlock(); // eseguito sempre, anche dopo i return
        }
    }

    // ENTRA-Nome-Pin
    private String entraStanza(String[] parti) {
        if (parti.length != 3) {
            return "ERRORE-Formato ENTRA non valido";
        }
        String nome = parti[1];
        int pin;
        try {
            pin = Integer.parseInt(parti[2]);
        } catch (NumberFormatException e) {
            return "ERRORE-Il pin deve essere un numero";
        }

        Server.mutexStanze.lock();
        try {
            Stanza s = cercaStanza(pin);
            if (s == null) {
                return "ERRORE-Non esiste nessuna stanza con questo pin";
            }
            if (s.getIsPlaying()) {
                return "ERRORE-La partita e gia iniziata";
            }
            if (s.getGiocatoriStanza().size() >= s.getMaxGiocatori()) {
                return "ERRORE-La stanza e piena";
            }
            for (Giocatore g : s.getGiocatoriStanza()) {
                // Mario e mario contano come lo stesso nome
                if (g.getNome().equalsIgnoreCase(nome)) {
                    return "ERRORE-Nome gia usato in questa stanza";
                }
            }
            // il nuovo giocatore va in fondo: indice = numero giocatori + 1
            Giocatore nuovo = new Giocatore(nome, s.getGiocatoriStanza().size() + 1, s.getViteIniziali(), null);
            s.getGiocatoriStanza().add(nuovo);

            stanza = s;
            giocatore = nuovo;
            System.out.println(nome + " e entrato nella stanza " + pin);
            return "OK-Sei entrato nella stanza";
        } finally {
            Server.mutexStanze.unlock(); // eseguito sempre, anche dopo i return
        }
    }
}