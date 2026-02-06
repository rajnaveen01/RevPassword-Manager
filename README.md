# RevPassword Manager 🔐

**RevPassword Manager** is a secure, console-based Java application designed to help users safely store, manage, and retrieve credentials for multiple online accounts. It eliminates the need for insecure storage methods (like spreadsheets or sticky notes) by providing an encrypted vault protected by a Master Password.

The project is built using a **Modular Layered Architecture** (UI, Service, DAO, Utility) to ensure scalability, maintainability, and clean code separation.

## 🚀 Key Features

* **Secure Authentication:**
    * User login via a Master Password.
    * **SHA-256 Hashing** ensures the Master Password is never stored in plain text.
* **Encrypted Vault (CRUD):**
    * **Add:** Store credentials with **AES-128 Encryption**.
    * **View:** Retrieve and decrypt passwords only after re-verifying the Master Password (Zero-Knowledge proof).
    * **Update/Delete:** Modify credentials or "Soft Delete" entries to prevent accidental data loss.
    * **Search:** Find accounts quickly by name.
* **Advanced Security:**
    * **OTP Verification:** Sensitive operations (like Profile Updates) require a time-sensitive One-Time Password.
    * **Password Generator:** Create cryptographically strong passwords with custom parameters (Length, Upper/Lower case, Digits, Special chars).
* **Account Recovery:**
    * Forgot Password flow using **Hashed Security Questions**.
    * Prevents lockout without compromising security.

## 🛠️ Technology Stack

* **Language:** Java 17 (Core Java)
* **Database:** Oracle Database 11g/19c
* **Connectivity:** JDBC (Java Database Connectivity)
* **Security:**
    * `javax.crypto` (AES Encryption)
    * `java.security` (SHA-256 Hashing, SecureRandom)
* **Architecture:** Layered Architecture (Presentation -> Service -> DAO -> Database)
* **Tools:** Maven, JUnit (Testing), Log4j (Logging)

## 🏗️ System Architecture

The application follows a strict separation of concerns:

1.  **Presentation Layer (`com.revpm.ui`):** Handles user input/output via the Console. Uses `InputUtil` for crash-proof data entry.
2.  **Business Logic Layer (`com.revpm.service`):** Orchestrates data flow, validates inputs (Email/Password strength), and handles Encryption/Hashing logic.
3.  **Data Access Layer (`com.revpm.dao`):** Executes optimized SQL queries and manages Database transactions.
4.  **Utility Layer (`com.revpm.util`):** Cross-cutting concerns like Database Connection Pooling, Validation Regex, and Cryptographic helpers.

## 💾 Database Schema (ER Diagram Overview)

The system uses a relational schema with **One-to-Many** relationships:
* **Users:** Stores user profile and Master Password Hash.
* **Password_Entries:** Stores encrypted account credentials.
* **Security_Questions:** Stores hashed answers for recovery.
* **Verification_Codes:** Stores temporary OTPs for sensitive actions.

## ⚙️ Setup & Installation

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/YourUsername/RevPasswordManager.git](https://github.com/rajnaveen01/RevPassword-Manager)
    ```
2.  **Configure Database:**
    * Ensure Oracle Database is running.
    * Update `src/com/revpm/util/DBConnection.java` with your DB credentials (URL, Username, Password).
3.  **Build the Project:**
    * If using Maven: `mvn clean install`
    * Or import into Eclipse/IntelliJ as a Java Project.
4.  **Run:**
    * Execute the `MainApp.java` file in the `com.revpm.ui` package.

## 📸 Usage Workflow

1.  **Register:** Create an account with a strong Master Password.
2.  **Login:** Access your vault.
3.  **Add Password:** Save a new login (e.g., "Facebook").
4.  **Generate:** Create a secure password for a new service.
5.  **View:** Select "Facebook", re-enter Master Password, and view the decrypted credential.

---
*Developed by NaveenRaj as a Portfolio Project showcasing Secure Software Development and Java Full Stack capabilities.*
