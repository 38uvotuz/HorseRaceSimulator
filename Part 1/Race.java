// Cookiesfiles
// Illia Dutyi
// Student ID: 240871428
// --------------------------------------------------
import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.util.Scanner;

public class Race {
    private final int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    public Race(int distance) {
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
    }

    public void addHorse(Horse theHorse, int laneNumber) {
        if (laneNumber == 1) {
            lane1Horse = theHorse;
        } else if (laneNumber == 2) {
            lane2Horse = theHorse;
        } else if (laneNumber == 3) {
            lane3Horse = theHorse;
        } else {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane.");
        }
    }

    public void startRace() {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            boolean finished = false;

            if (lane1Horse != null) lane1Horse.goBackToStart();
            if (lane2Horse != null) lane2Horse.goBackToStart();
            if (lane3Horse != null) lane3Horse.goBackToStart();

            while (!finished) {
                moveHorse(lane1Horse);
                moveHorse(lane2Horse);
                moveHorse(lane3Horse);

                printRace();

                if (raceWonBy(lane1Horse) || raceWonBy(lane2Horse) || raceWonBy(lane3Horse)) {
                    finished = true;
                }

                if (!finished &&
                        ( (lane1Horse == null || lane1Horse.hasFallen()) &&
                                (lane2Horse == null || lane2Horse.hasFallen()) &&
                                (lane3Horse == null || lane3Horse.hasFallen()) )) {
                    finished = true;
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (Exception e) {
                    // Do nothing
                }
            }

            printWinners();

            System.out.print("Do you want to play again? (yes/no): ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (!answer.equals("yes")) {
                playAgain = false;
                System.out.println("Thank you for playing!");
            }
        }

        scanner.close();
    }

    private void moveHorse(Horse theHorse) {
        if (theHorse != null && !theHorse.hasFallen()) {
            if (Math.random() < theHorse.getConfidence()) {
                theHorse.moveForward();
            }

            if (Math.random() < (0.02 * theHorse.getConfidence() * theHorse.getConfidence())) {
                theHorse.fall();
            }
        }
    }

    private boolean raceWonBy(Horse theHorse) {
        return theHorse != null && theHorse.getDistanceTravelled() >= raceLength;
    }

    private void printRace() {
        System.out.print('\u000C');

        multiplePrint('=', raceLength + 3);
        System.out.println();

        printLane(lane1Horse);
        System.out.println();

        printLane(lane2Horse);
        System.out.println();

        printLane(lane3Horse);
        System.out.println();

        multiplePrint('=', raceLength + 3);
        System.out.println();
    }

    private void printLane(Horse theHorse) {
        System.out.print('|');

        if (theHorse != null) {
            int spacesBefore = theHorse.getDistanceTravelled();
            int spacesAfter = raceLength - theHorse.getDistanceTravelled();

            multiplePrint(' ', spacesBefore);

            if (theHorse.hasFallen()) {
                System.out.print('X');
            } else {
                System.out.print(theHorse.getSymbol());
            }

            multiplePrint(' ', spacesAfter);

            System.out.print("| " + theHorse.getName() + " (Current confidence " + theHorse.getConfidence() + ")");
        } else {
            multiplePrint(' ', raceLength);
            System.out.print('|');
        }
    }

    private void multiplePrint(char aChar, int times) {
        for (int i = 0; i < times; i++) {
            System.out.print(aChar);
        }
    }

    private void printWinners() {
        boolean anyWinner = false;

        if (raceWonBy(lane1Horse)) {
            System.out.println("The winner is: " + lane1Horse.getName());
            anyWinner = true;
        }
        if (raceWonBy(lane2Horse)) {
            System.out.println("The winner is: " + lane2Horse.getName());
            anyWinner = true;
        }
        if (raceWonBy(lane3Horse)) {
            System.out.println("The winner is: " + lane3Horse.getName());
            anyWinner = true;
        }

        if (!anyWinner) {
            System.out.println("None of the horses made it to the finish line.");
        }
    }
}
