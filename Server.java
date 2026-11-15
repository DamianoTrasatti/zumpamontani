
//SERVER (multi-client, piu' stanze)
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    ServerSocket serverSocket = null;

    // lista UNICA di tutte le stanze, condivisa da tutti i thread dei client
    public static ArrayList<Stanza> stanze = new ArrayList<>();

    // mutex: prima di leggere o modificare "stanze" fare lock(), alla fine unlock()
    public static ReentrantLock mutexStanze = new ReentrantLock(); // il mutex deve proteggere stanze, ogni thread non deve avere il suo, deve essere uno globale per tutti

    public void attendi() {
        try {
            System.out.println("Server partito in esecuzione;");
            serverSocket = new ServerSocket(6789); // apre il server in ascolto sulla porta 6789 del tuo computer.

            while (true) {
                Socket clientSocket = serverSocket.accept(); // #### SI CONNETTE IL CLIENT ####
                GestioneServerDelClientThread nuovaGestioneClient = new GestioneServerDelClientThread(clientSocket);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Server servente = new Server();
        servente.attendi();
    }
}