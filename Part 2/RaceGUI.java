import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RaceGUI extends JFrame {
    private RacePanel racePanel;
    private InfoPanel infoPanel;
    private HorseGUI[] horses;
    private Timer timer;
    private JTextField distanceField;
    private JComboBox<String> trackSelector;
    private JComboBox<String> weatherSelector;
    private BettingManager bettingManager;
    private double speedMultiplier = 1.0;
    private double fallMultiplier = 1.0;
    private int raceDistance = 500;
    private HorseGUI selectedHorse;
    private double betAmount = 0;

    public RaceGUI(HorseGUI[] horses) {
        this.horses = horses;
        this.bettingManager = new BettingManager(horses);

        setTitle("Horse Race Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JButton startButton = new JButton("Start Race");

        trackSelector = new JComboBox<>(new String[]{"Straight", "Oval", "Figure Eight"});
        distanceField = new JTextField("500", 5);
        weatherSelector = new JComboBox<>(new String[]{"Dry", "Muddy", "Icy"});

        JButton betButton = new JButton("Place Bet");
        JButton viewStatsButton = new JButton("View Horse Stats");
        JButton bettingHistoryButton = new JButton("Betting Analysis");

        topPanel.add(new JLabel("Track:"));
        topPanel.add(trackSelector);
        topPanel.add(new JLabel("Distance (100-5000 m):"));
        topPanel.add(distanceField);
        topPanel.add(new JLabel("Weather:"));
        topPanel.add(weatherSelector);
        topPanel.add(startButton);
        topPanel.add(betButton);
        topPanel.add(viewStatsButton);
        topPanel.add(bettingHistoryButton);

        add(topPanel, BorderLayout.NORTH);

        racePanel = new RacePanel(horses, "Straight");
        infoPanel = new InfoPanel(horses);

        add(racePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startRace();
            }
        });

        betButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBettingWindow();
            }
        });

        viewStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHorseStats();
            }
        });

        bettingHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBettingHistory();
            }
        });
    }

    private void startRace() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        String selectedTrack = (String) trackSelector.getSelectedItem();
        racePanel.setTrackType(selectedTrack);

        try {
            raceDistance = Integer.parseInt(distanceField.getText());
            if (raceDistance < 100 || raceDistance > 5000) {
                JOptionPane.showMessageDialog(this, "Distance must be between 100 and 5000 meters.");
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid distance input.");
            return;
        }
        racePanel.setTrackLength(raceDistance);

        String selectedWeather = (String) weatherSelector.getSelectedItem();
        if (selectedWeather.equals("Dry")) {
            fallMultiplier = 1.0;
        } else if (selectedWeather.equals("Muddy")) {
            fallMultiplier = 1.2;
        } else if (selectedWeather.equals("Icy")) {
            fallMultiplier = 1.5;
        }

        speedMultiplier = 500.0 / raceDistance;

        for (HorseGUI horse : horses) {
            horse.setSpeedMultiplier(speedMultiplier);
            horse.setFallMultiplier(fallMultiplier);
            horse.reset(racePanel.getTrackType(), racePanel);
        }

        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean raceFinished = false;
                for (HorseGUI horse : horses) {
                    horse.move(racePanel.getTrackType(), racePanel, horse.getSpeedMultiplier());
                    if (horse.getLapsCompleted() >= 1 && !horse.hasFallen()) {
                        raceFinished = true;
                    }
                }

                racePanel.repaint();
                infoPanel.repaint();

                if (raceFinished) {
                    timer.stop();
                    announceWinner();
                }
            }
        });
        timer.start();
    }

    private void announceWinner() {
        HorseGUI winner = null;
        for (HorseGUI horse : horses) {
            if (horse.getLapsCompleted() >= 1 && !horse.hasFallen()) {
                winner = horse;
                break;
            }
        }

        if (winner != null) {
            JOptionPane.showMessageDialog(this, "🏁 Winner: " + winner.getName());

            if (selectedHorse != null && selectedHorse == winner) {
                JOptionPane.showMessageDialog(this, "🎉 You won your bet on " + selectedHorse.getName() + "!");
            } else if (selectedHorse != null) {
                JOptionPane.showMessageDialog(this, "❌ You lost your bet on " + selectedHorse.getName() + ".");
            }
        }
    }

    private void openBettingWindow() {
        JDialog bettingDialog = new JDialog(this, "Place Your Bet", true);
        bettingDialog.setSize(400, 200);
        bettingDialog.setLayout(new GridLayout(3, 2));

        JComboBox<String> horseSelector = new JComboBox<>();
        for (HorseGUI horse : horses) {
            horseSelector.addItem(horse.getName());
        }

        JTextField betAmountField = new JTextField();

        JButton placeBetButton = new JButton("Place Bet");
        placeBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedHorseName = (String) horseSelector.getSelectedItem();
                selectedHorse = null;
                for (HorseGUI horse : horses) {
                    if (horse.getName().equals(selectedHorseName)) {
                        selectedHorse = horse;
                        break;
                    }
                }

                try {
                    betAmount = Double.parseDouble(betAmountField.getText());
                    if (betAmount > 0) {
                        bettingManager.placeBet(selectedHorse, betAmount);
                        JOptionPane.showMessageDialog(bettingDialog, "Bet placed successfully!");
                        bettingDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(bettingDialog, "Invalid bet amount.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(bettingDialog, "Please enter a valid number.");
                }
            }
        });

        bettingDialog.add(new JLabel("Select Horse:"));
        bettingDialog.add(horseSelector);
        bettingDialog.add(new JLabel("Bet Amount:"));
        bettingDialog.add(betAmountField);
        bettingDialog.add(placeBetButton);

        bettingDialog.setLocationRelativeTo(this);
        bettingDialog.setVisible(true);
    }

    private void showHorseStats() {
        JTextArea textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        StringBuilder stats = new StringBuilder();

        for (HorseGUI horse : horses) {
            stats.append(horse.getRaceStatistics()).append("\n");
        }

        textArea.setText(stats.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Horse Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showBettingHistory() {
        JTextArea textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        StringBuilder history = new StringBuilder();

        if (bettingManager != null) {
            for (String record : bettingManager.getBettingHistory()) {
                history.append(record).append("\n");
            }
            history.append("\nBalance: $").append(String.format("%.2f", bettingManager.getVirtualCurrency()));
        } else {
            history.append("No bets placed yet.");
        }

        textArea.setText(history.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Betting History", JOptionPane.INFORMATION_MESSAGE);
    }
}
