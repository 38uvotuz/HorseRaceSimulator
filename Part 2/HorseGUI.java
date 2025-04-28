import java.util.ArrayList;
import java.util.HashMap;

public class HorseGUI {
    private String name;
    private double confidence;
    private String breed;
    private String coatColor;
    private char symbol;
    private String saddle;
    private String horseshoes;
    private int x;
    private int y;
    private boolean hasFallen;
    private int lapsCompleted;
    private double angle;
    private int laneNumber;
    private double speedMultiplier = 1.0;
    private double fallMultiplier = 1.0;

    // Statistics fields
    private ArrayList<Double> pastFinishingTimes = new ArrayList<>();
    private ArrayList<Double> pastSpeeds = new ArrayList<>();
    private ArrayList<Double> pastConfidenceLevels = new ArrayList<>();
    private int racesParticipated = 0;
    private int racesWon = 0;
    private HashMap<String, Double> bestTimesPerTrackType = new HashMap<>();

    // === Constructor (now includes all customization fields) ===
    public HorseGUI(String name, double confidence, String breed, String coatColor, char symbol, String saddle, String horseshoes, int laneNumber) {
        this.name = name;
        this.confidence = confidence;
        this.breed = breed;
        this.coatColor = coatColor;
        this.symbol = symbol;
        this.saddle = saddle;
        this.horseshoes = horseshoes;
        this.laneNumber = laneNumber;
        this.x = 100;
        this.y = 100 + laneNumber * 80;
        this.hasFallen = false;
        this.lapsCompleted = 0;
        this.angle = 0;
    }

    // === Getters ===
    public String getName() { return name; }
    public double getConfidence() { return confidence; }
    public String getBreed() { return breed; }
    public String getCoatColor() { return coatColor; }
    public char getSymbol() { return symbol; }
    public String getSaddle() { return saddle; }
    public String getHorseshoes() { return horseshoes; }
    public int getX() { return x; }
    public int getY() { return y; }
    public double getAngle() { return angle; }
    public boolean hasFallen() { return hasFallen; }
    public int getLapsCompleted() { return lapsCompleted; }

    public void setSpeedMultiplier(double multiplier) {
        this.speedMultiplier = multiplier;
    }

    public void setFallMultiplier(double multiplier) {
        this.fallMultiplier = multiplier;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public void reset(String trackType, RacePanel racePanel) {
        angle = 0;
        lapsCompleted = 0;
        hasFallen = false;

        if (trackType.equals("Figure Eight")) {
            x = (racePanel.getEightCenterX1() + racePanel.getEightCenterX2()) / 2;
            y = racePanel.getEightCenterY() + laneNumber * 10;
        } else if (trackType.equals("Oval")) {
            x = racePanel.getOvalCenterX();
            y = racePanel.getOvalCenterY() + racePanel.getOvalHeight() / 2 + laneNumber * 10;
            angle = 90;
        } else {
            x = 100;
            y = 100 + laneNumber * 80;
            angle = 0;
        }
    }

    public void move(String trackType, RacePanel racePanel, double speedMultiplier) {
        if (hasFallen) return;

        if (Math.random() < confidence) {
            double angleIncrement = 1.5 * this.speedMultiplier;
            angle += angleIncrement;

            if (trackType.equals("Straight")) {
                x += (int)(5 * this.speedMultiplier);
            } else if (trackType.equals("Oval")) {
                int centerX = racePanel.getOvalCenterX();
                int centerY = racePanel.getOvalCenterY();
                int width = racePanel.getOvalWidth();
                int height = racePanel.getOvalHeight();
                int laneOffset = laneNumber * 15;

                x = (int)(centerX + (width / 2 - laneOffset) * Math.cos(Math.toRadians(angle)));
                y = (int)(centerY + (height / 2 - laneOffset) * Math.sin(Math.toRadians(angle)));
            } else if (trackType.equals("Figure Eight")) {
                int centerX1 = racePanel.getEightCenterX1();
                int centerX2 = racePanel.getEightCenterX2();
                int centerY = racePanel.getEightCenterY();
                int radiusX = racePanel.getEightWidth() / 2;
                int radiusY = racePanel.getEightHeight() / 2;
                int laneOffset = laneNumber * 10;

                // Ліва частина вісімки
                if (angle < 360) {
                    double localAngle = angle;
                    x = (int)(centerX1 + (radiusX - laneOffset) * Math.cos(Math.toRadians(localAngle)));
                    y = (int)(centerY + (radiusY - laneOffset) * Math.sin(Math.toRadians(localAngle)));
                }
                // Перехід через середину вісімки
                else if (angle >= 360 && angle < 370) {
                    x = (centerX1 + centerX2) / 2;
                    y = centerY;
                }
                // Права частина вісімки
                else {
                    double localAngle = angle - 370;
                    x = (int)(centerX2 + (radiusX - laneOffset) * Math.cos(Math.toRadians(180 - localAngle)));
                    y = (int)(centerY + (radiusY - laneOffset) * Math.sin(Math.toRadians(180 - localAngle)));
                }
            }
        }

        if (Math.random() < (0.005 * confidence * confidence * fallMultiplier)) {
            hasFallen = true;
        }

        if (trackType.equals("Straight") && x > racePanel.getWidth() - 100) {
            lapsCompleted++;
        } else if (trackType.equals("Oval") && angle >= 360) {
            angle = 0;
            lapsCompleted++;
        } else if (trackType.equals("Figure Eight") && angle >= 730) {
            angle = 0;
            lapsCompleted++;
        }
    }

    // === New Statistics and Tracking methods ===
    public void addRaceResult(double finishingTime, boolean won, String trackType, double raceDistance) {
        racesParticipated++;
        if (won) racesWon++;

        pastFinishingTimes.add(finishingTime);
        pastSpeeds.add(raceDistance / finishingTime);
        pastConfidenceLevels.add(confidence);

        if (!bestTimesPerTrackType.containsKey(trackType) || finishingTime < bestTimesPerTrackType.get(trackType)) {
            bestTimesPerTrackType.put(trackType, finishingTime);
        }
    }

    public void updateConfidenceAfterRace() {
        double change = (Math.random() - 0.5) * 0.02;
        confidence += change;
        if (confidence > 1.0) confidence = 1.0;
        if (confidence < 0.5) confidence = 0.5;
    }

    public double getWinRatio() {
        if (racesParticipated == 0) return 0;
        return (double) racesWon / racesParticipated;
    }

    public double getAverageSpeed() {
        if (pastSpeeds.isEmpty()) return 0;
        double total = 0;
        for (double s : pastSpeeds) {
            total += s;
        }
        return total / pastSpeeds.size();
    }

    public double getBestTime(String trackType) {
        return bestTimesPerTrackType.getOrDefault(trackType, Double.MAX_VALUE);
    }

    public ArrayList<Double> getConfidenceHistory() {
        return pastConfidenceLevels;
    }

    // === New method for stats display ===
    public String getRaceStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("Name: ").append(name).append("\n");
        stats.append("Win Ratio: ").append(racesWon).append("/").append(racesParticipated).append("\n");
        stats.append("Average Speed: ").append(String.format("%.2f", getAverageSpeed())).append(" m/s\n");
        stats.append("Best Times:\n");
        stats.append("  Straight: ").append(bestTimesPerTrackType.getOrDefault("Straight", Double.MAX_VALUE)).append("s\n");
        stats.append("  Oval: ").append(bestTimesPerTrackType.getOrDefault("Oval", Double.MAX_VALUE)).append("s\n");
        stats.append("  Figure Eight: ").append(bestTimesPerTrackType.getOrDefault("Figure Eight", Double.MAX_VALUE)).append("s\n");
        stats.append("Confidence History: ").append(pastConfidenceLevels).append("\n");
        stats.append("-----------------------------\n");
        return stats.toString();
    }

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }
}
