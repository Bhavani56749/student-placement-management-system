package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            System.getenv("PLACEMENT_DB_URL");

    private static final String USER =
            System.getenv("PLACEMENT_DB_USER");

    private static final String PASSWORD =
            System.getenv("PLACEMENT_DB_PASSWORD");

    public static Connection getConnection() throws SQLException {

        if (URL == null || USER == null || PASSWORD == null) {

            throw new SQLException(
                    "Database environment variables are not configured."
            );
        }

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }

    public static void main(String[] args) {

        try {

            Connection connection =
                    getConnection();

            System.out.println(
                    "Database connected successfully!"
            );

            connection.close();

        } catch (SQLException e) {

            System.out.println(
                    "Database connection failed!"
            );

            e.printStackTrace();
        }
    }
}