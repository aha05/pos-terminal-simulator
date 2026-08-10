# POS Terminal Simulator

A JavaFX desktop application for simulating POS terminals and testing POS management APIs, including terminal heartbeat and device status.

## Tech Stack

* Java 17
* JavaFX
* Maven
* REST/JSON
* Jackson

## Prerequisites

* JDK 17+
* Maven 3.8+

## Run

Clone the repository:

```bash
git clone https://github.com/aha05/pos-terminal-simulator
cd pos-terminal-simulator
```

Run the application:

```bash
mvn clean javafx:run
```

Configure the backend API URL in `PosApiClient.java` before running.
