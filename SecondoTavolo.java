import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class SecondoTavolo {
    // ###### ATTRIBUTI PANNELLO + JFRAME ######
	JFrame finestra;
	JPanel leftPanel; // tavolo
	JPanel rightPanel; // cronaca
	JPanel playerPanel; // la mia carta e i bottoni (dentro al leftPanel)
	JSplitPane splitPane;

    

    public static void main(String[] args) {
		TavoloGioco tavolo = new TavoloGioco();
		tavolo.creaTavolo();
	}
}
