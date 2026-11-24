import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class TavoloGioco {

	// ###### ATTRIBUTI PANNELLO + JFRAME ######
	JFrame finestra;
	JPanel leftPanel; // tavolo
	JPanel rightPanel; // cronaca
	JPanel playerPanel; // la mia carta e i bottoni (dentro al leftPanel)
	JSplitPane splitPane;
	JButton btnTieni, btnScambio;
	JTextArea cronaca;

	// ###### ATTRIBUTI GIOCATORE ######
	int viteMax = 3;

	public void creaTavolo() {
		finestra = new JFrame("Zompa Montani gioco - Marco");
		finestra.setSize(1000, 800);
		finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ###### PANNELLO SINISTRO (TAVOLO) ######
		leftPanel = new JPanel();
		leftPanel.setLayout(null);
		leftPanel.setBackground(Color.decode("#1C6634"));

		// --- RIGA IN ALTO: INFO PARTITA ---
		JLabel labelStanza = new JLabel("Stanza: PIN 4821");
		labelStanza.setFont(new Font("Arial", Font.BOLD, 16));
		labelStanza.setForeground(Color.BLACK);
		labelStanza.setBounds(20, 10, 150, 30);
		leftPanel.add(labelStanza);

		JLabel labelGiocatori = new JLabel("Giocatori: 7 in gioco / 8");
		labelGiocatori.setFont(new Font("Arial", Font.PLAIN, 16));
		labelGiocatori.setForeground(Color.BLACK);
		labelGiocatori.setBounds(180, 10, 200, 30);
		leftPanel.add(labelGiocatori);

		JLabel labelMano = new JLabel("Mano n. 4");
		labelMano.setFont(new Font("Arial", Font.PLAIN, 16));
		labelMano.setForeground(Color.BLACK);
		labelMano.setBounds(390, 10, 100, 30);
		leftPanel.add(labelMano);

		JLabel labelMazziere = new JLabel("Mazziere: Luca");
		labelMazziere.setFont(new Font("Arial", Font.PLAIN, 16));
		labelMazziere.setForeground(Color.BLACK);
		labelMazziere.setBounds(500, 10, 150, 30);
		leftPanel.add(labelMazziere);

		JLabel labelTempo = new JLabel("Tempo: 0:12");
		labelTempo.setFont(new Font("Arial", Font.PLAIN, 16));
		labelTempo.setForeground(Color.BLACK);
		labelTempo.setBounds(640, 10, 100, 30);
		leftPanel.add(labelTempo);

		// barra del tempo per l'azione (12 secondi su 30)
		JProgressBar barraTempo = new JProgressBar(0, 30);
		barraTempo.setValue(12);
		barraTempo.setForeground(Color.ORANGE);
		barraTempo.setBounds(20, 45, 700, 15);
		leftPanel.add(barraTempo);

		// --- MAZZO E SCARTI ---
		JLabel cartaMazzo = new JLabel(dorso(70, 100));
		cartaMazzo.setBounds(20, 75, 70, 100);
		leftPanel.add(cartaMazzo);

		JLabel labelMazzo = new JLabel("Mazzo: 32 carte rimaste");
		labelMazzo.setFont(new Font("Arial", Font.BOLD, 18));
		labelMazzo.setForeground(Color.BLACK);
		labelMazzo.setBounds(105, 90, 400, 30);
		leftPanel.add(labelMazzo);

		JLabel labelScarti = new JLabel("Scarti: tornano in fondo al mazzo a fine mano");
		labelScarti.setFont(new Font("Arial", Font.PLAIN, 16));
		labelScarti.setForeground(Color.BLACK);
		labelScarti.setBounds(105, 125, 500, 30);
		leftPanel.add(labelScarti);

		// --- ORDINE DI SCAMBIO ---
		JLabel labelOrdine = new JLabel("Ordine di scambio: Sara > Elena > Paolo > Giulia > Marco > Anna > Luca");
		labelOrdine.setFont(new Font("Arial", Font.ITALIC, 16));
		labelOrdine.setForeground(Color.BLACK);
		labelOrdine.setBounds(20, 190, 700, 30);
		leftPanel.add(labelOrdine);

		// --- I POSTI DEI GIOCATORI (2 righe da 4) ---
		// parametri: nome, cartaScoperta ("" = dorso), vite, stato, x, y, turno, eliminato
		creaPosto("Sara", "", 3, "Ha chiesto a Elena: respinto", 20, 235, false, false);
		creaPosto("Elena", "Re<br>di Denari", 3, "Re scoperto: ha respinto lo scambio", 200, 235, false, false);
		creaPosto("Paolo", "Cavallo<br>di Bastoni", 2, "Cavallo scoperto: salta al successivo", 380, 235, false, false);
		creaPosto("Giulia", "", 1, "Ha scambiato con Marco", 560, 235, false, false);

		creaPosto("Marco (TU)", "", 3, "Sta decidendo...", 20, 415, true, false);
		creaPosto("Davide (FUORI)", "", 0, "Eliminato alla mano 3", 200, 415, false, true);
		creaPosto("Anna", "", 2, "In attesa", 380, 415, false, false);
		creaPosto("Luca (MAZZIERE)", "", 2, "Gioca per ultimo, puo' andare al mazzo", 560, 415, false, false);

		// --- PANNELLO IN BASSO: LA MIA CARTA E I BOTTONI ---
		playerPanel = new JPanel();
		playerPanel.setLayout(null);
		playerPanel.setBackground(Color.decode("#12402A"));
		playerPanel.setBounds(20, 600, 720, 150);
		leftPanel.add(playerPanel);

		JLabel miaCarta = new JLabel("<html><center><font size=7>3</font><br>di Coppe</center></html>");
		miaCarta.setHorizontalAlignment(SwingConstants.CENTER);
		miaCarta.setOpaque(true);
		miaCarta.setBackground(Color.decode("#F7F0E4"));
		miaCarta.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
		miaCarta.setBounds(15, 10, 100, 130);
		playerPanel.add(miaCarta);

		JLabel labelTurno = new JLabel("E' il tuo turno, Marco");
		labelTurno.setFont(new Font("Arial", Font.BOLD, 24));
		labelTurno.setForeground(Color.WHITE);
		labelTurno.setBounds(135, 10, 400, 35);
		playerPanel.add(labelTurno);

		JLabel labelDescrizione = new JLabel("Puoi tenere la carta o chiedere lo scambio ad Anna (Davide e' eliminato).");
		labelDescrizione.setFont(new Font("Arial", Font.PLAIN, 13));
		labelDescrizione.setForeground(Color.WHITE);
		labelDescrizione.setBounds(135, 50, 570, 25);
		playerPanel.add(labelDescrizione);

		JLabel labelMieVite = new JLabel("Le tue vite: " + scriviVite(3));
		labelMieVite.setFont(new Font("Arial", Font.BOLD, 16));
		labelMieVite.setForeground(Color.ORANGE);
		labelMieVite.setBounds(135, 75, 300, 25);
		playerPanel.add(labelMieVite);

		btnTieni = new JButton("Tieni la carta");
		btnTieni.setBounds(135, 105, 150, 35);
		btnTieni.setBackground(Color.WHITE);
		btnTieni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// qui poi mandi il messaggio al server
				scriviCronaca("Marco tiene la carta.");
			}
		});
		playerPanel.add(btnTieni);

		btnScambio = new JButton("Chiedi lo scambio ad Anna");
		btnScambio.setBounds(300, 105, 240, 35);
		btnScambio.setBackground(Color.ORANGE);
		btnScambio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// qui poi mandi il messaggio al server
				scriviCronaca("Marco chiede lo scambio ad Anna.");
			}
		});
		playerPanel.add(btnScambio);

		// ###### PANNELLO DESTRO (CRONACA) ######
		rightPanel = new JPanel();
		rightPanel.setLayout(null);
		rightPanel.setBackground(Color.decode("#12402A"));

		JLabel labelCronaca = new JLabel("Cronaca della mano");
		labelCronaca.setFont(new Font("Arial", Font.BOLD, 20));
		labelCronaca.setForeground(Color.WHITE);
		labelCronaca.setBounds(10, 10, 220, 30);
		rightPanel.add(labelCronaca);

		cronaca = new JTextArea();
		cronaca.setEditable(false);
		cronaca.setLineWrap(true);
		cronaca.setWrapStyleWord(true);
		cronaca.setFont(new Font("Arial", Font.PLAIN, 14));
		cronaca.setBackground(Color.decode("#12402A"));
		cronaca.setForeground(Color.WHITE);
		scriviCronaca("Mano 4 - Luca mescola e distribuisce.");
		scriviCronaca("Sara chiede lo scambio a Elena. Elena scopre il Re di Denari: scambio respinto.");
		scriviCronaca("Elena chiede lo scambio a Paolo. Paolo scopre il Cavallo di Bastoni: Elena scambia con Giulia.");
		scriviCronaca("Paolo tiene la carta.");
		scriviCronaca("Giulia chiede lo scambio a Marco: scambio fatto.");
		scriviCronaca("Tocca a Marco.");

		JScrollPane scroll = new JScrollPane(cronaca);
		scroll.setBounds(10, 50, 215, 650);
		rightPanel.add(scroll);

		// ###### SPLIT PANE: TAVOLO A SINISTRA, CRONACA A DESTRA ######
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setDividerLocation(750);
		splitPane.setDividerSize(3);

		finestra.add(splitPane);
		finestra.setVisible(true);
	}

	// ###### METODI DI AUSILIO ######

	// crea un posto (pannello) di un giocatore e lo aggiunge al tavolo
	public void creaPosto(String nome, String cartaScoperta, int vite, String stato, int x, int y, boolean turno,
			boolean eliminato) {
		JPanel posto = new JPanel();
		posto.setLayout(null);
		posto.setBackground(Color.decode("#12402A"));
		posto.setBounds(x, y, 170, 170);
		if (turno) {
			posto.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3)); // bordo arancione = e' il suo turno
		} else {
			posto.setBorder(BorderFactory.createLineBorder(Color.decode("#3C6450"), 1));
		}

		JLabel labelNome = new JLabel(nome);
		labelNome.setFont(new Font("Arial", Font.BOLD, 15));
		labelNome.setForeground(eliminato ? Color.GRAY : Color.WHITE);
		labelNome.setBounds(10, 5, 155, 25);
		posto.add(labelNome);

		// carta: se la stringa e' vuota mostro il dorso, altrimenti la carta scoperta
		JLabel carta = new JLabel();
		carta.setBounds(10, 35, 64, 85);
		if (cartaScoperta.equals("")) {
			carta.setIcon(dorso(64, 85));
		} else {
			carta.setText("<html><center>" + cartaScoperta + "</center></html>");
			carta.setFont(new Font("Arial", Font.BOLD, 11));
			carta.setForeground(Color.BLACK);
			carta.setHorizontalAlignment(SwingConstants.CENTER);
			carta.setOpaque(true);
			carta.setBackground(Color.decode("#F7F0E4"));
		}
		posto.add(carta);

		JLabel labelVite = new JLabel("Vite " + scriviVite(vite));
		labelVite.setFont(new Font("Arial", Font.BOLD, 13));
		labelVite.setForeground(Color.ORANGE);
		labelVite.setBounds(82, 35, 85, 25);
		posto.add(labelVite);

		JTextArea labelStato = new JTextArea(stato);
		labelStato.setEditable(false);
		labelStato.setLineWrap(true);
		labelStato.setWrapStyleWord(true);
		labelStato.setFont(new Font("Arial", Font.PLAIN, 12));
		labelStato.setBackground(Color.decode("#12402A"));
		labelStato.setForeground(eliminato ? Color.GRAY : Color.WHITE);
		labelStato.setBounds(82, 65, 82, 100);
		posto.add(labelStato);

		leftPanel.add(posto);
	}

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

	// carica il dorso da image/dorso.png e lo ridimensiona
	public ImageIcon dorso(int larghezza, int altezza) {
		ImageIcon icona = new ImageIcon("image/dorso.png");
		Image img = icona.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}

	// aggiunge una riga alla cronaca
	public void scriviCronaca(String riga) {
		cronaca.append(riga + "\n\n");
	}

	public static void main(String[] args) {
		TavoloGioco tavolo = new TavoloGioco();
		tavolo.creaTavolo();
	}
}