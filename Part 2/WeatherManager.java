public class WeatherManager {
    private String currentWeather;

    public WeatherManager(String weather) {
        this.currentWeather = weather;
    }

    public double getSpeedMultiplier() {
        switch (currentWeather) {
            case "Muddy":
                return 0.8; // У багнюці коні бігають повільніше
            case "Icy":
                return 0.7; // На льоду ще повільніше
            default: // Clear
                return 1.0; // На сухій доріжці нормальна швидкість
        }
    }

    public double getFallRiskMultiplier() {
        switch (currentWeather) {
            case "Muddy":
                return 1.3; // У багнюці більше шансів впасти
            case "Icy":
                return 1.5; // На льоду ще більший ризик падіння
            default:
                return 1.0; // Нормальний ризик падіння
        }
    }

    public double getConfidenceModifier() {
        switch (currentWeather) {
            case "Muddy":
                return -0.05; // У багнюці впевненість трохи падає
            case "Icy":
                return -0.08; // На льоду впевненість падає ще більше
            default:
                return 0; // Без змін
        }
    }

    public String getCurrentWeather() {
        return currentWeather;
    }
}
