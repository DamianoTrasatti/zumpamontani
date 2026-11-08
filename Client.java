import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Font;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class Client {
	// ###### ATTRIUTI SOCKET #######
	String nomeServer = "localhost";
	int portaServer = 6789;
	Socket miosocket;
	BufferedReader tastiera;
	String stringaUtente;
	String stringaRicevutaDalServer;
	DataOutputStream outVersoServer;
	BufferedReader inDalServer;

	// ###### ATTRIBUTI PANNELLO + JFRAME (prima parte [scelta entra o crea stanza])
	// ######
	JFrame finestra;
	JPanel leftPanel;
	JPanel rightPanel;
	JSplitPane splitPane;

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

	public void creaInterfacciaCliente() {
		try {
			finestra = new JFrame("Zompa Montani client - Entra o Crea stanza");
			finestra.setSize(1000, 800);
			finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			// Creazione dei due JPanel
			leftPanel = new JPanel();
			leftPanel.setBackground(Color.decode("#1C6634"));
			JLabel labelCreaPartita = new JLabel("CREA UNA STANZA");
			labelCreaPartita.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 40));
			labelCreaPartita.setForeground(Color.BLACK);
			leftPanel.add(labelCreaPartita);

			rightPanel = new JPanel();
			rightPanel.setBackground(Color.decode("#1C6634"));
			JLabel labelEntraPartita = new JLabel("ENTRA IN UNA STANZA");
			labelEntraPartita.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 40));
			labelEntraPartita.setForeground(Color.BLACK);
			rightPanel.add(labelEntraPartita);

			// Creazione del JSplitPane diviso orizzontalmente
			splitPane = new JSplitPane(
					JSplitPane.HORIZONTAL_SPLIT,
					leftPanel,
					rightPanel);
			splitPane.setDividerLocation(500); // Posizione iniziale del divisore in pixel
			splitPane.setDividerSize(3); // grandezza del divisore

			finestra.add(splitPane);
			finestra.setVisible(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Errore durante la comunicazione col server");
			System.exit(1);
		}
	}

	public void comunica() {
		try {
			stringaRicevutaDalServer = inDalServer.readLine();
			System.out.println("...prima scritta dal server:" + '\n' + stringaRicevutaDalServer);

			stringaUtente = tastiera.readLine();

			System.out.println("...invio la stringa al server e attendo...");
			outVersoServer.writeBytes(stringaUtente + '\n');

			/*
			 * System.out.println("...CLIENT termina eleborazione e chiude connessione");
			 * miosocket.close();
			 */
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Errore durante la comunicazione col server");
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		Client cliente = new Client();
		cliente.connetti();
		cliente.creaInterfacciaCliente();
		cliente.comunica();
	}
}