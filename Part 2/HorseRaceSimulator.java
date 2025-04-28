import javax.swing.*;

public class HorseRaceSimulator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                int numHorses = Integer.parseInt(JOptionPane.showInputDialog("Enter number of horses (1-8):"));
                if (numHorses < 1 || numHorses > 8) {
                    JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 8.");
                    return;
                }

                HorseGUI[] horses = new HorseGUI[numHorses];
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                HorseSetupDialog setupDialog = new HorseSetupDialog(frame, horses);
                setupDialog.setVisible(true);

                RaceGUI raceGUI = new RaceGUI(horses);
                raceGUI.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Closing program.");
                e.printStackTrace();
            }
        });
    }
}
