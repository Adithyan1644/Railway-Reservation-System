# ☕ Railway Reservation System

![Java](https://img.shields.io/badge/Java-17-orange?logo=java&logoColor=white)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A console‑based, thread‑safe Railway Reservation application built with **Core Java** and **Advanced Java** features...

# 🚂 Railway Reservation System

A console‑based, thread‑safe Railway Reservation application built with **Core Java** and **Advanced Java** features.  
It demonstrates real‑world concepts like train search, user registration, ticket booking & cancellation, and past booking retrieval – all in a multi‑threaded environment **without any external libraries or build tools**.

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Advanced Java Concepts Used](#-advanced-java-concepts-used)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Running from Eclipse](#running-from-eclipse)
  - [Running from Command Line](#running-from-command-line)
- [How to Use](#-how-to-use)
- [Sample Output](#-sample-output)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🚀 Overview

This project simulates a **Railway Reservation System** where users can:
- Search for trains between any two cities.
- Register with a unique user ID.
- Book tickets (with seat allocation and PNR generation).
- Cancel tickets (seats are returned to the train).
- View all tickets of a user.
- View **past bookings** (travel date < today).

All data is stored **in‑memory** using thread‑safe collections, making the application ready for concurrent users.

---

## ✨ Features

| Feature               | Description                                                                 |
|-----------------------|-----------------------------------------------------------------------------|
| **Train Search**      | Search trains by source, destination and available seats.                   |
| **User Registration** | Register with user ID, name, email, phone.                                  |
| **Ticket Booking**    | Atomic seat booking with lock protection, PNR generation, seat allocation.  |
| **Ticket Cancellation** | Cancel a ticket by PNR – seats become available again.                    |
| **View Tickets**      | Retrieve a ticket by PNR or list all tickets for a user.                    |
| **Past Bookings**     | Filter a user’s bookings where travel date < today (using `LocalDate`).     |
| **Thread‑safe Data**  | `ConcurrentHashMap`, `AtomicInteger`, `ReentrantLock` ensure data integrity. |

---

## 🧠 Advanced Java Concepts Used

| Concept                 | Implementation                                                                 |
|-------------------------|--------------------------------------------------------------------------------|
| **ConcurrentHashMap**   | In‑memory storage for trains, users, tickets.                                 |
| **AtomicInteger**       | Train seat counter – thread‑safe without locks.                               |
| **AtomicLong**          | PNR generator – unique, lock‑free ID generation.                              |
| **ReentrantLock**       | Critical section protection in `bookTicket()`.                                |
| **Streams & Lambdas**   | Filtering trains, tickets, past bookings.                                     |
| **Optional<T>**         | Avoids `null` in `findTrainByNumber()`, `getTicketByPNR()`.                   |
| **java.time API**       | `LocalDate` for travel dates and `LocalTime` for train timings.               |
| **Custom Exceptions**   | `BookingException`, `TrainNotFoundException` for clear error handling.       |
| **Singleton Pattern**   | `DataStore` – single point of truth for all data.                             |
| **Builder Pattern**     | Ticket construction via service (immutable except status).                    |
| **Factory (utility)**   | `PNRGenerator` and `SeatAllocator` provide reusable creation logic.          |
| **Functional Interface**| `Predicate<Train>` used in `TrainService.filterTrains()`.                     |

---

## 📁 Project Structure
```
RailwayReservationSystem/
└── src/
└── com/
└── railway/
├── app/
│ └── MainApp.java # Console menu & entry point
├── data/
│ └── DataStore.java # Singleton + ConcurrentHashMap storage
├── exception/
│ ├── BookingException.java
│ └── TrainNotFoundException.java
├── model/
│ ├── BookingStatus.java
│ ├── Ticket.java
│ ├── Train.java
│ ├── TravelClass.java
│ └── User.java
├── service/
│ ├── BookingService.java
│ ├── TrainService.java
│ └── UserService.java
└── util/
├── PNRGenerator.java
└── SeatAllocator.java
```

text

---

## 🛠 Getting Started

### Prerequisites

- **Java 11** or higher (for `java.time`, `var` not required)
- **Eclipse IDE** (or any Java IDE / command line)
- No external libraries – pure Java

### Running from Eclipse

1. Clone or download the project.
2. Open Eclipse → **File** → **Import** → **Existing Projects into Workspace**.
3. Select the project folder and click **Finish**.
4. Right‑click `MainApp.java` → **Run As** → **Java Application**.

### Running from Command Line

```bash
# Compile all Java files
javac -d out src/com/railway/**/*.java

# Run the application
java -cp out com.railway.app.MainApp
```


## 🎮 How to Use
After launching, you will see a menu:
```
===== Railway Reservation System =====
1. Search Trains
2. Register User
3. Book Ticket
4. Cancel Ticket
5. View Ticket by PNR
6. View My Tickets (All)
7. View Past Bookings
8. Exit
```

Typical Workflow


Search trains – enter source & destination, system shows available trains.

Register a user – provide user ID, name, email, phone.

Book a ticket – choose a train, user ID, number of seats, and travel date.

View your tickets – list all bookings for that user.

View past bookings – automatically filters tickets with travel date before today.

Cancel a ticket – using the PNR shown after booking.


## 📸 Sample Output
```
text
Sample trains loaded.

1. Search Trains
Enter Source: Delhi
Enter Destination: Mumbai
12345 | Shatabdi Exp | Delhi → Mumbai | Dep: 06:00 | Arr: 14:00 | ₹1250.00 | Seats: 100/100

2. Register User
User ID: U1001
Name: Rajesh Kumar
Email: rajesh@example.com
Phone: 9876543210
User registered: User[U1001] Rajesh Kumar (rajesh@example.com / 9876543210)

3. Book Ticket
Enter Train Number: 12345
Enter User ID: U1001
Number of seats: 2
Travel Date (yyyy-mm-dd): 2026-05-15
Booking successful!
PNR: PNR1734567890 | Train: 12345 | Rajesh Kumar | Date: 2026-05-15 | Seats: [S1, S2] | ₹2500.00 | CONFIRMED
```

## 🔮 Future Enhancements
Persist data using JDBC (MySQL / PostgreSQL).

Add web interface using Spring Boot + Thymeleaf.

Implement user authentication (login / logout).

Async email confirmation using ExecutorService.

Improve seat allocation – real seat numbers per train.

Add upgrade/downgrade of travel class (Sleeper, AC, General).

REST API for integration with mobile apps.

## 🤝 Contributing

Contributions are welcome!
Feel free to fork this repository, create a feature branch, and submit a pull request.

## MIT License

Copyright (c) 2026 Adithyan Vijayan K

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
