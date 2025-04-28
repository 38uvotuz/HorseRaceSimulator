import java.util.Scanner;

/**
 * A simple program to test the Horse Race Simulator with user input.
 */
public class RaceTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name for Horse 1: ");
        String name1 = scanner.nextLine();

        System.out.print("Enter the name for Horse 2: ");
        String name2 = scanner.nextLine();

        System.out.print("Enter the name for Horse 3: ");
        String name3 = scanner.nextLine();

        // Create horses with user-given names
        Horse horse1 = new Horse('A', name1, 0.8);
        Horse horse2 = new Horse('B', name2, 0.6);
        Horse horse3 = new Horse('C', name3, 0.7);

        Race race = new Race(30);

        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        race.startRace();
    }
}
