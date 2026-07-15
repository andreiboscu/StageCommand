# StageCommand

StageCommand is a Java Swing desktop application designed to simulate communication between a stage manager and the technical departments involved in a live production.

## Features

- Central manager console for sending instructions
- Individual stations for curtain, lighting, and sound teams
- Targeted messages to one department or broadcasts to all stations
- Real-time delivery to active station windows
- Global command log and separate history for each department
- Dark-themed graphical user interface built entirely with Java Swing

## Technologies

- Java
- Java Swing
- AWT
- Eclipse project structure

## Project structure

```text
stagecommand/
├── src/stagecommand/
│   ├── Main.java
│   ├── HomeWindow.java
│   ├── ManagerWindow.java
│   ├── CurtainWindow.java
│   ├── LightsWindow.java
│   ├── SoundWindow.java
│   ├── MessageBoard.java
│   └── MessageListener.java
├── .classpath
└── .project
```

## How to run

### Eclipse

1. Clone or download the repository.
2. In Eclipse, select **File > Import > Existing Projects into Workspace**.
3. Select the project directory.
4. Run `src/stagecommand/Main.java` as a Java application.

### Command line

From the project root, compile and run the application with:

```bash
mkdir -p out
javac -d out src/stagecommand/*.java
java -cp out stagecommand.Main
```

Java 17 or newer is recommended.

## How it works

The application uses an in-memory message board shared by all windows. The manager selects a destination and sends an instruction. Registered department windows receive matching messages through the `MessageListener` interface, while `MessageBoard` maintains the global and department-specific histories.

## Notes

- Messages are stored only while the application is running.
- The application does not require external libraries or services.
