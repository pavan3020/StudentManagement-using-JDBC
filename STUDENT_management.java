import java.sql.*;
import java.util.Scanner;

public class STUDENT_management {

    static final String DB_URL = "jdbc:sqlite:mycollege_records.db";
    static Connection conn = null;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found.");
            return;
        }

        connectDatabase();
        createTable();
        int choice;
        do {
            showMenu();
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudent();
                case 4 -> updateMarks();
                case 5 -> deleteStudent();
                case 6 -> System.out.println("Exiting program. Goodbye!");
                default -> System.out.println("Invalid choice, try again!");
            }
        } while (choice != 6);

        closeConnection();
    }

    // Connect to SQLite database
    static void connectDatabase() {
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to database successfully!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            System.exit(0);
        }
    }

    // Close the database connection
    static void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Create students table if it does not exist
    static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "name TEXT NOT NULL," +
                     "marks1 INTEGER," +
                     "marks2 INTEGER," +
                     "marks3 INTEGER" +
                     ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
    }

    // Display menu
    static void showMenu() {
        System.out.println("\n=== Smart Student Manager ===");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Marks");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    // Add a new student
    static void addStudent() {
        try {
            System.out.print("Enter student name: ");
            String name = sc.nextLine();
            System.out.print("Enter marks for Subject 1: ");
            int m1 = Integer.parseInt(sc.nextLine());
            System.out.print("Enter marks for Subject 2: ");
            int m2 = Integer.parseInt(sc.nextLine());
            System.out.print("Enter marks for Subject 3: ");
            int m3 = Integer.parseInt(sc.nextLine());

            String sql = "INSERT INTO students(name, marks1, marks2, marks3) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, m1);
            pstmt.setInt(3, m2);
            pstmt.setInt(4, m3);
            pstmt.executeUpdate();

            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    // View all students
    static void viewStudents() {
        String sql = "SELECT * FROM students";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nID\tName\tM1\tM2\tM3\tResult");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int m1 = rs.getInt("marks1");
                int m2 = rs.getInt("marks2");
                int m3 = rs.getInt("marks3");
                String result = (m1 >= 35 && m2 >= 35 && m3 >= 35) ? "Pass" : "Fail";
                System.out.printf("%d\t%s\t%d\t%d\t%d\t%s%n", id, name, m1, m2, m3, result);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing students: " + e.getMessage());
        }
    }

    // Search for a student by name
    static void searchStudent() {
        try {
            System.out.print("Enter student name to search: ");
            String nameSearch = sc.nextLine();
            String sql = "SELECT * FROM students WHERE name LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + nameSearch + "%");
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int m1 = rs.getInt("marks1");
                int m2 = rs.getInt("marks2");
                int m3 = rs.getInt("marks3");
                String result = (m1 >= 35 && m2 >= 35 && m3 >= 35) ? "Pass" : "Fail";
                System.out.printf("ID: %d, Name: %s, Marks: %d,%d,%d, Result: %s%n", id, name, m1, m2, m3, result);
            }
            if (!found) System.out.println("No student found with that name.");
        } catch (SQLException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
    }

    // Update student marks
    static void updateMarks() {
        try {
            System.out.print("Enter student ID to update: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Enter new marks for Subject 1: ");
            int m1 = Integer.parseInt(sc.nextLine());
            System.out.print("Enter new marks for Subject 2: ");
            int m2 = Integer.parseInt(sc.nextLine());
            System.out.print("Enter new marks for Subject 3: ");
            int m3 = Integer.parseInt(sc.nextLine());

            String sql = "UPDATE students SET marks1 = ?, marks2 = ?, marks3 = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, m1);
            pstmt.setInt(2, m2);
            pstmt.setInt(3, m3);
            pstmt.setInt(4, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Marks updated successfully!");
            else System.out.println("Student ID not found.");
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    // Delete a student
    static void deleteStudent() {
        try {
            System.out.print("Enter student ID to delete: ");
            int id = Integer.parseInt(sc.nextLine());
            String sql = "DELETE FROM students WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Student deleted successfully!");
            else System.out.println("Student ID not found.");
        } catch (SQLException e) {
            System.out.println("Deletion failed: " + e.getMessage());
        }
    }
}
