# Student Management System in Java

This project demonstrates strong hands-on experience in building a **Student Management System** using **Java** and **SQLite**, emphasizing practical knowledge of **JDBC**, **SQL queries**, and **object-oriented programming**.  

---

## Motivation / Purpose

This project was developed to deepen my understanding of **Java-JDBC integration** and to practice building **real-world database-driven applications** that are modular, secure, and maintainable. It reflects my keen interest in designing systems that interact efficiently with relational databases.  

---

## Key Features

- Demonstrates connecting Java applications to **SQLite database** using **JDBC driver**.  
- Implements **CRUD operations** (Create, Read, Update, Delete) for managing student records.  
- Utilizes **PreparedStatements** to prevent SQL injection and ensure secure database interactions.  
- Automatically creates the database table if it does not exist.  
- Provides a **menu-driven command-line interface** for intuitive interaction.  

---

## Technical Highlights

- **Java SE** for core application logic and input handling.  
- **JDBC (Java Database Connectivity)** for database interaction.  
- **SQLite** as a lightweight, embedded relational database.  
- Proper **exception handling** to manage database and runtime errors.  
- Modular and clean code structure demonstrating best coding practices.  

---

## Skills Demonstrated

- Efficient use of **JDBC API** to establish database connections, execute queries, and handle result sets.  
- Strong understanding of **SQL operations** including table creation, insertion, updates, deletions, and querying data.  
- Integration of **Java and database management** in a single project environment.  
- Implementation of **secure and robust database interactions** using PreparedStatements.  
- Experience in designing **menu-driven command-line applications** for practical use.  

---

## How to Run

1. Ensure **Java JDK** is installed.  
2. Download the **SQLite JDBC driver** and place it in the project directory.  
3. Compile the Java file with the driver:

```bash
javac -cp ".;sqlite-jdbc-<version>.jar" ST.java
