import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class GestioneServerDelClientThread extends Thread {
    Socket miosocket; // questo è il socket del client appena connesso
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    String stringaRicevuta = null;

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

            outVersoClient.writeBytes("Vuoi creare una stanza(CREA-NomeGiocatore-Pin-MaxGiocatori-Vite-SecondiAzione-SecondiKick) o accedere ad una già esistente(ENTRA-NomeGiocatore-Pin)? (CREA/ENTRA)" + "\n");
            stringaRicevuta = inDalClient.readLine();
            // risposta possibile 
            // CREA-Nome-Pin-MaxGicoatori-Vite-Timer-Timer2
            // ENTRA-Nome-Pin

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
}