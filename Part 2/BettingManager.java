import java.util.ArrayList;

public class BettingManager {
    private HorseGUI[] horses;
    private ArrayList<String> bettingHistory = new ArrayList<>();
    private double virtualCurrency = 1000.0; // Стартовий баланс

    public BettingManager(HorseGUI[] horses) {
        this.horses = horses;
    }

    public void placeBet(HorseGUI selectedHorse, double amount) {
        if (selectedHorse == null || amount <= 0 || amount > virtualCurrency) {
            return;
        }
        virtualCurrency -= amount;
        bettingHistory.add("Placed $" + amount + " on " + selectedHorse.getName());
    }

    public void calculateWinnings(HorseGUI winner, HorseGUI selectedHorse, double betAmount) {
        if (winner != null && selectedHorse != null && selectedHorse == winner) {
            double winnings = betAmount * 2.0; // Просто множимо на 2 для виграшу
            virtualCurrency += winnings;
            bettingHistory.add("Won $" + winnings + " on " + selectedHorse.getName());
        } else if (selectedHorse != null) {
            bettingHistory.add("Lost $" + betAmount + " on " + selectedHorse.getName());
        }
    }

    public ArrayList<String> getBettingHistory() {
        return bettingHistory;
    }

    public double getVirtualCurrency() {
        return virtualCurrency;
    }
}
