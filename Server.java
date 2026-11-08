
//SERVER (multi-client, piu' stanze)
import java.net.*;
import java.io.*;

public class Server {
    ServerSocket serverSocket = null;

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