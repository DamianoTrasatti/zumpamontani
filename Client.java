import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

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

	// ###### ATTRIBUTI PER MESSAGGIO SOCKET ######
	int viteSelezionate = 0;

	// ###### ATTRIBUTI PANNELLO + JFRAME (prima parte [scelta entra o crea stanza])
	// ######
	JFrame finestra;
	JPanel leftPanel;
	JPanel rightPanel;
	JSplitPane splitPane;
	JTextField insertNomeCrea, insertPinCrea, insertMaxGiocatoriCrea, insertSecondiAzioneCrea, insertSecondiKickCrea;
	JTextField insertNomeEntra, insertPinEntra;
	JButton btnVite1, btnVite2, btnVite3, btnVite4, btnVite5;

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

			// Configurazione pannello sinistro con layout nullo per usare setBounds
			leftPanel = new JPanel();
			leftPanel.setLayout(null);
			leftPanel.setBackground(Color.decode("#1C6634"));

			// --- NUOVO: NOME GIOCATORE (SINISTRA) ---
			JLabel labelNomeSx = new JLabel("Nome Giocatore:");
			labelNomeSx.setFont(new Font("Arial", Font.PLAIN, 18));
			labelNomeSx.setForeground(Color.BLACK);
			labelNomeSx.setBounds(50, 65, 200, 30);
			leftPanel.add(labelNomeSx);

			insertNomeCrea = new JTextField();
			insertNomeCrea.setBounds(50, 95, 200, 30);
			leftPanel.add(insertNomeCrea);

			// Label Titolo principale (spostato più in basso per fare spazio al nome)
			JLabel labelCreaPartita = new JLabel("CREA UNA STANZA");
			labelCreaPartita.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 40));
			labelCreaPartita.setForeground(Color.BLACK);
			labelCreaPartita.setBounds(50, 10, 400, 50);
			leftPanel.add(labelCreaPartita);

			// --- 1. CREA PIN (coordinate Y aggiornate) ---
			JLabel labelCreaPin = new JLabel("Crea PIN:");
			labelCreaPin.setFont(new Font("Arial", Font.PLAIN, 18));
			labelCreaPin.setForeground(Color.BLACK);
			labelCreaPin.setBounds(50, 160, 200, 30);
			leftPanel.add(labelCreaPin);

			insertPinCrea = new JTextField();
			insertPinCrea.setBounds(50, 195, 200, 30);
			leftPanel.add(insertPinCrea);

			// --- 2. SELEZIONA NUMERO VITE (coordinate Y aggiornate) ---
			JLabel labelVite = new JLabel("Seleziona numero vite per giocatore:");
			labelVite.setFont(new Font("Arial", Font.PLAIN, 18));
			labelVite.setForeground(Color.BLACK);
			labelVite.setBounds(50, 250, 400, 30);
			leftPanel.add(labelVite);

			int xInizialeBf = 50;
			int larghezzaBottone = 50;
			int spazioTraBottoni = 10;
			int step = larghezzaBottone + spazioTraBottoni; // 60 pixel di incremento per ogni bottone

			btnVite1 = new JButton("1");
			btnVite1.setBounds(xInizialeBf + 0 * step, 285, larghezzaBottone, 40);
			btnVite1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					viteSelezionate = 1;
					btnVite1.setBackground(Color.ORANGE);
					setBottoniBianchi(1);
				}
			});
			leftPanel.add(btnVite1);

			btnVite2 = new JButton("2");
			btnVite2.setBounds(xInizialeBf + 1 * step, 285, larghezzaBottone, 40);
			btnVite2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					viteSelezionate = 2;
					btnVite2.setBackground(Color.ORANGE);
					setBottoniBianchi(2);
				}
			});
			leftPanel.add(btnVite2);

			btnVite3 = new JButton("3");
			btnVite3.setBounds(xInizialeBf + 2 * step, 285, larghezzaBottone, 40);
			btnVite3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					viteSelezionate = 3;
					btnVite3.setBackground(Color.ORANGE);
					setBottoniBianchi(3);
				}
			});
			leftPanel.add(btnVite3);

			btnVite4 = new JButton("4");
			btnVite4.setBounds(xInizialeBf + 3 * step, 285, larghezzaBottone, 40);
			btnVite4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					viteSelezionate = 4;
					btnVite4.setBackground(Color.ORANGE);
					setBottoniBianchi(4);
				}
			});
			leftPanel.add(btnVite4);

			btnVite5 = new JButton("5");
			btnVite5.setBounds(xInizialeBf + 4 * step, 285, larghezzaBottone, 40);
			btnVite5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					viteSelezionate = 5;
					btnVite5.setBackground(Color.ORANGE);
					setBottoniBianchi(5);
				}
			});
			leftPanel.add(btnVite5);

			// --- 3. MASSIMO NUMERO GIOCATORI (coordinate Y aggiornate) ---
			JLabel labelMaxGiocatori = new JLabel("Massimo numero giocatori:");
			labelMaxGiocatori.setFont(new Font("Arial", Font.PLAIN, 18));
			labelMaxGiocatori.setForeground(Color.BLACK);
			labelMaxGiocatori.setBounds(50, 350, 400, 30);
			leftPanel.add(labelMaxGiocatori);

			insertMaxGiocatoriCrea = new JTextField();
			insertMaxGiocatoriCrea.setBounds(50, 385, 200, 30);
			leftPanel.add(insertMaxGiocatoriCrea);

			// --- 4. SECONDI AZIONE E SECONDI KICK (coordinate Y aggiornate) ---
			JLabel labelSecondiAzione = new JLabel("Secondi azione:");
			labelSecondiAzione.setFont(new Font("Arial", Font.PLAIN, 18));
			labelSecondiAzione.setForeground(Color.BLACK);
			labelSecondiAzione.setBounds(50, 440, 180, 30);
			leftPanel.add(labelSecondiAzione);

			insertSecondiAzioneCrea = new JTextField();
			insertSecondiAzioneCrea.setBounds(50, 475, 160, 30);
			leftPanel.add(insertSecondiAzioneCrea);

			JLabel labelSecondiKick = new JLabel("Secondi kick:");
			labelSecondiKick.setFont(new Font("Arial", Font.PLAIN, 18));
			labelSecondiKick.setForeground(Color.BLACK);
			labelSecondiKick.setBounds(240, 440, 180, 30);
			leftPanel.add(labelSecondiKick);

			insertSecondiKickCrea = new JTextField();
			insertSecondiKickCrea.setBounds(240, 475, 160, 30);
			leftPanel.add(insertSecondiKickCrea);

			JButton inviaCreaStanza = new JButton("CREA LA STANZA");
			inviaCreaStanza.setBounds(50, 530, 180, 40);
			inviaCreaStanza.setBackground(Color.WHITE);
			inviaCreaStanza.addActionListener(e -> {
				String nome = insertNomeCrea.getText().trim();
				String pin = insertPinCrea.getText().trim();
				String maxGiocatori = insertMaxGiocatoriCrea.getText().trim();
				String secondiAzione = insertSecondiAzioneCrea.getText().trim();
				String secondiKick = insertSecondiKickCrea.getText().trim();

				if (nome.isEmpty() || pin.isEmpty() || maxGiocatori.isEmpty() || secondiAzione.isEmpty() || secondiKick.isEmpty()) {
					JOptionPane.showMessageDialog(finestra, "Compila tutti i campi");
					return;
				}
				if (viteSelezionate == 0) {
					JOptionPane.showMessageDialog(finestra, "Scegli il numero di vite");
					return;
				}
				if (nome.contains("-")) {
					JOptionPane.showMessageDialog(finestra, "Il nome non può contenere '-'");
					return;
				}
				if (!isNum(pin) || !isNum(maxGiocatori) || !isNum(secondiAzione) || !isNum(secondiKick)) {
					JOptionPane.showMessageDialog(finestra, "PIN, giocatori e secondi devono essere numeri");
					return;
				}
				int maxG = Integer.parseInt(maxGiocatori);
				if (maxG < 2 || maxG > 30) {
					JOptionPane.showMessageDialog(finestra, "I giocatori devono essere da 2 a 30");
					return;
				}
				// inviaAlServer(...)
			});
			leftPanel.add(inviaCreaStanza);


			// --- CONFIGURAZIONE PANNELLO DESTRO (ENTRA IN UNA STANZA) ---
			rightPanel = new JPanel();
			rightPanel.setLayout(null); // Impostato a null per usare setBounds come a sinistra
			rightPanel.setBackground(Color.decode("#1C6634"));

			// Titolo principale Destro
			JLabel labelEntraPartita = new JLabel("ENTRA IN UNA STANZA");
			labelEntraPartita.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 40));
			labelEntraPartita.setForeground(Color.BLACK);
			labelEntraPartita.setBounds(50, 90, 450, 50);
			rightPanel.add(labelEntraPartita);

			// Nome Giocatore (Destra)
			JLabel labelNomeDx = new JLabel("Nome Giocatore:");
			labelNomeDx.setFont(new Font("Arial", Font.PLAIN, 18));
			labelNomeDx.setForeground(Color.BLACK);
			labelNomeDx.setBounds(50, 160, 200, 30);
			rightPanel.add(labelNomeDx);

			insertNomeEntra = new JTextField(); // Assicurati che sia dichiarata come variabile di classe
			insertNomeEntra.setBounds(50, 195, 200, 30);
			rightPanel.add(insertNomeEntra);

			// PIN (Destra)
			JLabel labelPinDx = new JLabel("Inserisci PIN Stanza:");
			labelPinDx.setFont(new Font("Arial", Font.PLAIN, 18));
			labelPinDx.setForeground(Color.BLACK);
			labelPinDx.setBounds(50, 250, 200, 30);
			rightPanel.add(labelPinDx);

			insertPinEntra = new JTextField(); // Assicurati che sia dichiarata come variabile di classe
			insertPinEntra.setBounds(50, 285, 200, 30);
			rightPanel.add(insertPinEntra);

			// Bottone Entra nella Stanza
			JButton inviaEntraStanza = new JButton("ENTRA NELLA STANZA");
			inviaEntraStanza.setBounds(50, 350, 200, 40);
			inviaEntraStanza.setBackground(Color.WHITE);
			inviaEntraStanza.addActionListener(e -> {
				String nome = insertNomeEntra.getText().trim();
				String pin = insertPinEntra.getText().trim();

				if (nome.isEmpty() || pin.isEmpty()) {
					JOptionPane.showMessageDialog(finestra, "Compila tutti i campi");
					return;
				}
				if (nome.contains("-")) {
					JOptionPane.showMessageDialog(finestra, "Il nome non può contenere '-'");
					return;
				}
				if (!isNum(pin)) {
					JOptionPane.showMessageDialog(finestra, "Il PIN deve essere un numero");
					return;
				}
				inviaAlServer("ENTRA-" + insertNomeEntra + "-" + insertPinEntra );
			});
			rightPanel.add(inviaEntraStanza);


			// Creazione del JSplitPane diviso orizzontalmente
			splitPane = new JSplitPane(
					JSplitPane.HORIZONTAL_SPLIT,
					leftPanel,
					rightPanel);
			splitPane.setDividerLocation(500); 
			splitPane.setDividerSize(3); 

			setBottoniBianchi(0); 
			finestra.add(splitPane);
			finestra.setVisible(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Errore durante la comunicazione col server");
			System.exit(1);
		}
	}

	// ###### METODI AUSILIO MESSAGGIO SOCKET ######

    // inviare i dati al server
	public void inviaAlServer(String testo){

	}

	// ###### METODI DI AUSILIO #######

	// vedere se è un numero accettabile
	public boolean isNum(String s) {
		if (s.length() == 0 || s.length() > 6) {
			return false; // vuota o troppo lunga per un int
		}
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c < '0' || c > '9') {
				return false; // trovato un carattere che non è una cifra
			}
		}
		return true;

	}

	// mettere bianchi i bottoni
	public void setBottoniBianchi(int numeroBottoneOrange) {
		if (numeroBottoneOrange != 1)
			btnVite1.setBackground(Color.WHITE);

		if (numeroBottoneOrange != 2)
			btnVite2.setBackground(Color.WHITE);

		if (numeroBottoneOrange != 3)
			btnVite3.setBackground(Color.WHITE);

		if (numeroBottoneOrange != 4)
			btnVite4.setBackground(Color.WHITE);

		if (numeroBottoneOrange != 5)
			btnVite5.setBackground(Color.WHITE);
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
