import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class TavoloGioco {
	// ###### ATTRIBUTI PANNELLO + JFRAME ######
	JFrame finestra;
	JPanel leftPanel; // tavolo
	JPanel rightPanel; // cronaca
	JPanel playerPanel; // la mia carta e i bottoni (in basso dentro al leftPanel)
	JSplitPane splitPane;

	// ###### DATI DELLA PARTITA (per ora fissi, poi arriveranno dal server) ######
	int pinStanza = 1234;
	int numGiocatori = 5;
	int maxGiocatori = 8;
	int numeroMano = 1;
	String nomeMazziere = "Luca";
	int viteMax = 3; // vite iniziali della stanza, serve per disegnare i pallini vuoti

	// ###### DATI DEL GIOCATORE (per ora fissi, poi arriveranno dal server) ######
	String giocatoreNome = "Marco";
	int giocatoreIndice = 1; // posizione nell'ordine di gioco
	int giocatoreVite = 3;
	boolean giocatoreEliminato = false;
	Carta giocatoreCarta = new Carta(3, "Coppe"); // da qui si prende anche il percorso dell'immagine

	// ###### COMPONENTI DA AGGIORNARE DURANTE LA PARTITA ######
	// attributi (non variabili locali) cosi' si potranno cambiare quando arrivano i
	// messaggi del server
	JLabel labelPin, labelGiocatori, labelMano, labelMazziere;
	JLabel labelCartaPlayer, labelNomePlayer, labelVitePlayer;
	JButton btnTieni, btnScambio;

	public void creaTavolo() {
		finestra = new JFrame("Zompa Montani gioco");
		finestra.setSize(1500, 750);
		finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ###### PANNELLO SINISTRO (TAVOLO) ######
		leftPanel = new JPanel();
		leftPanel.setLayout(null);
		leftPanel.setBackground(Color.decode("#1C6634"));

		// ###### PANNELLO DESTRO (CRONACA) ######
		rightPanel = new JPanel();
		rightPanel.setLayout(null);

		creaInfoPartita();
		creaGiocatori();
		creaParteGiocatore();

		// ###### SPLIT PANE: TAVOLO A SINISTRA, CRONACA A DESTRA ######
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setDividerLocation(1100); // tavolo 1100 pixel, cronaca il resto (circa 400)
		splitPane.setDividerSize(3);

		finestra.add(splitPane);
		finestra.setVisible(true);
	}

	// riga in alto nel tavolo: pin, giocatori, mano, mazziere
	public void creaInfoPartita() {
		labelPin = new JLabel("Stanza: PIN " + pinStanza);
		labelPin.setFont(new Font("Arial", Font.BOLD, 18));
		labelPin.setForeground(Color.BLACK);
		labelPin.setBounds(20, 10, 170, 30);
		leftPanel.add(labelPin);

		labelGiocatori = new JLabel("Giocatori: " + numGiocatori + " / " + maxGiocatori);
		labelGiocatori.setFont(new Font("Arial", Font.PLAIN, 18));
		labelGiocatori.setForeground(Color.BLACK);
		labelGiocatori.setBounds(200, 10, 170, 30);
		leftPanel.add(labelGiocatori);

		labelMano = new JLabel("Mano n. " + numeroMano);
		labelMano.setFont(new Font("Arial", Font.PLAIN, 18));
		labelMano.setForeground(Color.BLACK);
		labelMano.setBounds(380, 10, 120, 30);
		leftPanel.add(labelMano);

		labelMazziere = new JLabel("Mazziere: " + nomeMazziere);
		labelMazziere.setFont(new Font("Arial", Font.PLAIN, 18));
		labelMazziere.setForeground(Color.BLACK);
		labelMazziere.setBounds(510, 10, 220, 30);
		leftPanel.add(labelMazziere);
	}

	// due file da quattro rettangoli tra le scritte in alto e il pannello del giocatore
	public void creaGiocatori() {
		int larghezza = 255; // (1080 - 3 spazi da 20) / 4
		int altezza = 210;
		int spazio = 20; // spazio tra un rettangolo e l'altro
		int indiceGiocatoriPresenti = 0; // conta i rettangoli creati: 0..3 prima fila, 4..7 seconda fila

		for (int riga = 0; riga < 2; riga++) {
			for (int colonna = 0; colonna < 4; colonna++) {
				int x = 10 + colonna * (larghezza + spazio);
				int y = 60 + riga * (altezza + spazio);

				JPanel posto = new JPanel();
				posto.setLayout(null);
				posto.setBackground(Color.decode("#12402A")); // stesso verde del pannello del giocatore
				posto.setBounds(x, y, larghezza, altezza);
				leftPanel.add(posto);

				indiceGiocatoriPresenti++; // un rettangolo in piu' a ogni iterazione
			}
		}
	}

	// parte del giocatore: la mia carta, nome, vite e bottoni (playerPanel in basso nel leftPanel)
	public void creaParteGiocatore() {
		playerPanel = new JPanel();
		playerPanel.setLayout(null);
		playerPanel.setBackground(Color.decode("#12402A")); // verde piu' scuro del tavolo
		playerPanel.setBounds(10, 520, 1080, 180); // in basso, largo quasi quanto il tavolo
		leftPanel.add(playerPanel);

		// la carta: immagine presa dal percorso salvato nella Carta del giocatore
		labelCartaPlayer = new JLabel(caricaImmagine(giocatoreCarta.getPercorso_immagine(), 90, 152));
		labelCartaPlayer.setBounds(15, 14, 90, 152);
		playerPanel.add(labelCartaPlayer);

		labelNomePlayer = new JLabel(giocatoreNome);
		labelNomePlayer.setFont(new Font("Arial", Font.BOLD, 26));
		labelNomePlayer.setForeground(Color.WHITE);
		labelNomePlayer.setBounds(130, 15, 400, 35);
		playerPanel.add(labelNomePlayer);

		labelVitePlayer = new JLabel("Vite: " + scriviVite(giocatoreVite));
		labelVitePlayer.setFont(new Font("Arial", Font.BOLD, 20));
		labelVitePlayer.setForeground(Color.ORANGE);
		labelVitePlayer.setBounds(130, 55, 400, 30);
		playerPanel.add(labelVitePlayer);

		btnTieni = new JButton("Tieni la carta");
		btnTieni.setFont(new Font("Arial", Font.BOLD, 16));
		btnTieni.setBounds(130, 115, 180, 40);
		btnTieni.setBackground(Color.WHITE);
		btnTieni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// qui poi si manda il messaggio al server
				System.out.println("Tieni la carta");
			}
		});
		playerPanel.add(btnTieni);

		btnScambio = new JButton("Chiedi lo scambio");
		btnScambio.setFont(new Font("Arial", Font.BOLD, 16));
		btnScambio.setBounds(330, 115, 200, 40);
		btnScambio.setBackground(Color.ORANGE);
		btnScambio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// qui poi si manda il messaggio al server
				System.out.println("Chiedi lo scambio");
			}
		});
		playerPanel.add(btnScambio);
	}

	// ###### METODI DI AUSILIO ######

	// trasforma le vite in pallini: 2 vite su 3 -> "●●○"
	public String scriviVite(int vite) {
		String s = "";
		for (int i = 0; i < viteMax; i++) {
			if (i < vite) {
				s = s + "●";
			} else {
				s = s + "○";
			}
		}
		return s;
	}

	// carica un'immagine (es. image/Coppe03.png) e la ridimensiona
	public ImageIcon caricaImmagine(String percorso, int larghezza, int altezza) {
		ImageIcon icona = new ImageIcon(percorso);
		Image img = icona.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}

	public static void main(String[] args) {
		TavoloGioco tavolo = new TavoloGioco();
		tavolo.creaTavolo();
	}
}