# Y1S2-Group_Project
IT25101874 Reshan Athalsha
# 💍 WeddingBliss Management System
**SE1020 - Year 1 Semester 2 Project**

A comprehensive, file-based Wedding Planning and Vendor Booking System built with Java and Spring Boot. This application strictly adheres to the SE1020 module constraints, utilizing core Object-Oriented Programming (OOP) principles and native File I/O operations in place of a traditional database.

## 👥 Team Members & Components
This project was collaboratively built by our team, with each member taking ownership of a specific vertical slice of the application:

1. **User Management:** Reshan
2. **Vendor Management:** Chathum
3. **Booking & Payment Management:** Ayanadee
4. **Wedding Planning Tools (Itinerary/Guests):** Vibuda
5. **Reviews and Rating System:** Lakna
6. **Admin Dashboard & Analytics:** Dinuja

---

## 🏗️ Architecture & OOP Implementation
To satisfy the grading rubric, this project heavily demonstrates the four pillars of Object-Oriented Programming:

* **Inheritance:** The system features a robust class hierarchy. `Client`, `Vendor`, and `Admin` classes all successfully `extend` the abstract parent `User` class, utilizing `super(...)` constructors.
* **Polymorphism:** Child classes override abstract methods from the `User` class (e.g., `displayRoleDetails()` and `toFileString()`) to provide role-specific behaviors.
* **Encapsulation:** All model classes utilize strict data hiding. Properties are kept `private` and are only accessible via standard public getters and setters.
* **Composition & Aggregation:** * *Composition:* A `Payment` object securely encapsulates a `bookingId`, proving a strict "has-a" relationship.
  * *Aggregation:* The `Review` class links independent `User` and `Vendor` objects, while the `GuestList` dynamically links to an `Itinerary`.

---

## 💾 Data Persistence (File Handling)
**Strict Constraint Met: NO DATABASES WERE USED.**

All system data is persistently stored using standard Java File I/O. 
* We engineered a centralized `FileHandler.java` utility class to manage all read/write operations securely using `try-catch` blocks.
* Data is stored in the `data/` directory using pipe-delimited (`|`) `.txt` files (e.g., `users.txt`, `vendors.txt`, `bookings.txt`).
* Collections (`ArrayList`) are utilized extensively in the controllers to read file data into memory, modify object states, and cleanly overwrite the text files for update/delete operations.
* **Graceful Execution:** The system automatically creates the `data/` directory and required text files upon first launch, preventing `FileNotFoundException` crashes.

---

## 💻 Tech Stack
* **Backend:** Java 17+, Spring Boot 3.x
* **Frontend:** Thymeleaf, HTML5, CSS3, Bootstrap 5 (No external stylesheets required)
* **Build Tool:** Apache Maven

---

## 🚀 How to Run (For Grading Assessment)

This application is designed to run locally on any machine with Java installed.

1. **Clone the Repository:**
   Download the `.zip` or clone the repository to your local machine.
   https://github.com/Reshan-Athalsha/Y1S2-Group_Project 

   Open in IDE:
Open the project folder in IntelliJ IDEA (or Eclipse/VS Code). Allow Maven a few moments to resolve and download the Spring Boot dependencies from the pom.xml.

Compile the Project:
Ensure the project is cleanly built:

Bash
mvn clean compile
Start the Server:
Navigate to src/main/java/com/wedding/weddingplanner/ and run the WeddingplannerApplication.java main method.

Access the Application:
Once Tomcat starts successfully on port 8080, open your web browser and navigate to the application's central hub:
👉 http://localhost:8080/
