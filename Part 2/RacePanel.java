import javax.swing.*;
import java.awt.*;

public class RacePanel extends JPanel {
    private HorseGUI[] horses;
    private String trackType;
    private int trackLength = 500;

    public RacePanel(HorseGUI[] horses, String trackType) {
        this.horses = horses;
        this.trackType = trackType;
        setBackground(Color.WHITE);
    }

    public void setTrackType(String trackType) {
        this.trackType = trackType;
    }

    public String getTrackType() {
        return trackType;
    }

    public void setTrackLength(int trackLength) {
        this.trackLength = trackLength;
    }

    public int getTrackLength() {
        return trackLength;
    }

    public int getOvalCenterX() {
        return getWidth() / 2;
    }

    public int getOvalCenterY() {
        return getHeight() / 2;
    }

    public int getOvalWidth() {
        return getWidth() - 200;
    }

    public int getOvalHeight() {
        return getHeight() - 200;
    }

    public int getEightCenterX1() {
        return getWidth() / 3;
    }

    public int getEightCenterX2() {
        return 2 * getWidth() / 3;
    }

    public int getEightCenterY() {
        return getHeight() / 2;
    }

    public int getEightWidth() {
        return getWidth() / 3;
    }

    public int getEightHeight() {
        return getHeight() / 2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);

        if (trackType.equals("Straight")) {
            for (int i = 0; i < horses.length; i++) {
                int y = 100 + i * 100;
                g2d.drawLine(100, y, 100 + trackLength, y);
            }
        } else if (trackType.equals("Oval")) {
            int centerX = getOvalCenterX();
            int centerY = getOvalCenterY();
            int width = getOvalWidth();
            int height = getOvalHeight();
            g2d.drawOval(centerX - width / 2, centerY - height / 2, width, height);
        } else if (trackType.equals("Figure Eight")) {
            int centerX1 = getEightCenterX1();
            int centerX2 = getEightCenterX2();
            int centerY = getEightCenterY();
            int width = getEightWidth();
            int height = getEightHeight();
            g2d.drawOval(centerX1 - width / 2, centerY - height / 2, width, height);
            g2d.drawOval(centerX2 - width / 2, centerY - height / 2, width, height);
        }

        g2d.setFont(new Font("Serif", Font.PLAIN, 24));
        for (HorseGUI horse : horses) {
            if (!horse.hasFallen()) {
                g2d.drawString(String.valueOf(horse.getSymbol()), horse.getX(), horse.getY());
            } else {
                g2d.drawString("X", horse.getX(), horse.getY());
            }
        }
    }
}
