import javax.swing.*;
import java.awt.*;

public class SplitPaneEsempio {
    public static void main(String[] args) {
        // Creazione del JFrame principale
        JFrame frame = new JFrame("Esempio JSplitPane");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        // Creazione dei due JPanel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.add(new JLabel("Pannello Sinistro"));

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(new JLabel("Pannello Destro"));

        // Creazione del JSplitPane diviso orizzontalmente
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT, 
            leftPanel, 
            rightPanel
        );
        splitPane.setDividerLocation(400); // Posizione iniziale del divisore in pixel

        // Aggiunta al frame
        frame.add(splitPane);
        frame.setVisible(true);
    }
}
