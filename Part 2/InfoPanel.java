import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    private HorseGUI[] horses;

    public InfoPanel(HorseGUI[] horses) {
        this.horses = horses;
        setPreferredSize(new Dimension(250, 400));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.PLAIN, 16));

        int y = 50;
        for (HorseGUI horse : horses) {
            g.drawString("Name: " + horse.getName(), 10, y);
            g.drawString("Confidence: " + String.format("%.2f", horse.getConfidence()), 10, y + 20);
            g.drawString("Status: " + (horse.hasFallen() ? "Fallen" : "Running"), 10, y + 40);
            y += 80; // Відступ між конями
        }
    }
}
