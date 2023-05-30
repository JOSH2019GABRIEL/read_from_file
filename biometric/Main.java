package biometric;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileReader;
import java.sql.PreparedStatement;

public class Main {
    //public class CsvToPostgres {
    public static void main(String[] args) {
        // Set up database connection
        String url = "jdbc:postgresql://localhost:5432/lamisplus";
        String user = "postgres";
        String password = "R1se@jhp";
        Connection conn = null;
        PreparedStatement pstmt = null;

        // Check if CSV file exists
        String csvFile = "C:\\fileUse.csv";
        File file = new File(csvFile);
        if (!file.exists()) {
            System.out.println("CSV file does not exist.");
            return;
        }

        try {
            // Establish database connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");

            // Set up SQL statement for inserting data
            String sql = "INSERT INTO testing (id, name, hospital_num, facility, lga) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            // Read data from CSV file
            String line = "";
            String csvDelimiter = ",";
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvDelimiter);

                // Set values for each column in prepared statement
                pstmt.setInt(1, Integer.parseInt(data[0]));
                pstmt.setString(2, data[1]);
                pstmt.setString(3, data[2]);
                pstmt.setString(4, data[3]);
                pstmt.setString(5, data[4]);

                System.out.println("Processing Data Into the table " +data[0] +" " +data[1]+ " "+data[2] );
                // Execute the SQL statement
                pstmt.executeUpdate();
            }

            System.out.println("Data inserted successfully!");
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error occurred while closing resources: " + e.getMessage());
            }
        }
    }
}


