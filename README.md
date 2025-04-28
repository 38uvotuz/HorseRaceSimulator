The Horse Race Simulator is a Java project giving two gameplay modes, being these:
A text-based console race has been shown in Part1.
- It is a graphical user interface version, the GUI Part2.
It uses solely standard Java SE libraries such as Swing and requires absolutely no external dependencies, thus it is simple to compile and run on any system where Java is installed.

---

- Installation of Java JDK 8 or of a more recent version.
Your Java installation must be verified.
```bash
java -version
```

Download Java from off the [official website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) if it is not installed on.

---

1. To your local machine, make a download of or clone from the project repository.
2. A terminal or a command prompt should be opened up now.
3. Go to the main folder for the project:
```bash
cd HorseRaceSimulator
```

Libraries, frameworks, also build tools, for example, Maven or Gradle, are not at all additionally required.

---

1. Navigate into the indicated Part1 directory.
```bash
cd Part1
```

2. All Java files must be compiled together.
```bash
javac *.java
```

3. Run the program:
```bash
java RaceTest
```

The horse race starts in the terminal window when you launch the program because the `RaceTest.java` file will automatically invoke in it the `startRace()` method in the `Race` class.

---

1. The Part2 directory should be navigated within:
```bash
cd ../Part2
```

2. All of the Java files should have to be compiled.
```bash
javac *.java
```

3. The GUI application is executed.
```bash
java HorseRaceSimulator
```

A graphical window will open upon the program's launch where you can.
Horses have the ability to be customized in regard to their name, their breed, their color, their symbol, and their equipment. The customization features are of a varied nature.
- The type of race track that must be selected (Straight, Oval, Figure Eight)
- Set distance for track (several 100–5000 meters)
- Weather conditions that are chosen include Dry, Muddy, along with Icy.
Several virtual currency bets are placed.
- View a selection of real-time animations and a record of betting history and start each race.

Internal race logic is what manages movements of horses, and it determines just who the winner is when the "Start Race" button is pressed.

---

- In `Part1`, being that it is the text-based version:
Promptly upon starting of the program, `startRace()` is explicitly called inside `RaceTest.java`.
- `Part2` is within the GUI version.
- When the user clicks on the Start Race button in the graphical interface, the race starts.

The user is not at all required for them to call methods manually any further beyond what is normal interaction.

---

- Zero special IDE project settings are needed for this project, this project is IDE-independent.
Just simple Java tools (`javac`, `java`) are needed since it remains pure Java SE.
- Works just as smoothly on Windows, macOS, and also Linux.
- For example, any of the lightweight Java-supporting IDE or the terminal that you can use:
- Single Community Edition IntelliJ IDEA.
- Eclipse
- Several Java extensions with Visual Studio Code

It is functional in its entirety from inside the terminal, but you are able to run and debug it by making use of an IDE in the event that you prefer.

---

```
HorseRaceSimulator/
│
# Text-based of a console version ├── Part1/.
│ ├── Horse.java
│ ├── Race.java
│ ├── RaceTest.java
│
GUI version is Part2.
│ ├── BettingManager.java
│ ├── HorseGUI.java
│ ├── HorseRaceSimulator.java
│ ├── HorseSetupDialog.java
│ ├── InfoPanel.java
│ ├── RaceGUI.java
│ ├── RacePanel.java
│ ├── WeatherManager.java
│
├── README.md
```