import java.io.*;
import java.net.*;

public class Client {
	String nomeServer = "localhost";
	int portaServer = 6789;
	Socket miosocket;
	BufferedReader tastiera;
	String stringaUtente;
	String stringaRicevutaDalServer;
	DataOutputStream outVersoServer;
	BufferedReader inDalServer;

	public Socket connetti() {
		System.out.println("2 UN CLIENT partito in esecuzione");
		try {
			tastiera = new BufferedReader(new InputStreamReader(System.in));
			miosocket = new Socket(nomeServer, portaServer);
			outVersoServer = new DataOutputStream(miosocket.getOutputStream());
			inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.out.println("HOST sconosciuto");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Errore durante la connessione");
			System.exit(1);
		}
		return miosocket;
	}

	public void comunica() {
		try {
			stringaRicevutaDalServer = inDalServer.readLine();
			System.out.println("...prima scritta dal server:" + '\n' + stringaRicevutaDalServer);

			stringaUtente = tastiera.readLine();

			System.out.println("...invio la stringa al server e attendo...");
			outVersoServer.writeBytes(stringaUtente + '\n');



			System.out.println("...CLIENT termina eleborazione e chiude connessione");
			miosocket.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Errore durante la comunicazione col server");
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		Client cliente = new Client();
		cliente.connetti();
		cliente.comunica();
	}
}