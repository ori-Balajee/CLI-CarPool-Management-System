🚗 CLI CarPool Management System :

A Command Line Interface (CLI) based CarPool Management System built using Java and JDBC for database connectivity. This application allows users to manage carpools, passengers, drivers, and bookings through a simple terminal-based interface.

🛠️ Tech Stack
Language: Java
Database: MySQL
Database Connectivity: JDBC
Interface: Command Line Interface (CLI)

⚙️ Prerequisites
Before running the project, make sure you have:
Java JDK 8 or later
MySQL Server
MySQL JDBC Driver (Connector/J)
IDE such as IntelliJ IDEA, Eclipse, or VS Code

🗄️ Database Setup
Create a MySQL database:
CREATE DATABASE carpool_management;
Import the provided SQL file (if available).
Update your database credentials in the connection file:
String url = "jdbc:mysql://localhost:3306/carpool_management";
String username = "root";
String password = "your_password";

▶️ Running the Application
Clone the repository:
git clone https://github.com/your-username/CLI-CarPool-Management-System.git
Navigate to the project directory:
cd CLI-CarPool-Management-System
Compile the project:
javac *.java
Run the application:
java Main

🎯 Learning Objectives

This project demonstrates:
JDBC database connectivity
CRUD operations using SQL
Exception handling
Building a database-driven CLI application

🔮 Future Improvements
Admin dashboard
Ride ratings and reviews
Email notifications
Payment integration
Route optimization

Developed as a learning project to explore Java, JDBC, and database-driven application development.
