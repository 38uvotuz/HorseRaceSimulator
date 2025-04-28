
public class Horse {
    private final String name;
    private char symbol;
    private int distanceTravelled;
    private boolean fallen;
    private double confidence;

    public Horse(char horseSymbol, String horseName, double horseConfidence) {
        symbol = horseSymbol;
        name = horseName;
        confidence = horseConfidence;
        distanceTravelled = 0;
        fallen = false;
    }

    public void fall() {
        fallen = true;
    }

    public double getConfidence() {
        return confidence;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void goBackToStart() {
        distanceTravelled = 0;
        fallen = false;
    }

    public boolean hasFallen() {
        return fallen;
    }

    public void moveForward() {
        distanceTravelled += 1;
    }

    public void setConfidence(double newConfidence) {
        if (newConfidence < 0) {
            confidence = 0;
        } else if (newConfidence > 1) {
            confidence = 1;
        } else {
            confidence = newConfidence;
        }
    }

    public void setSymbol(char newSymbol) {
        symbol = newSymbol;
    }
}