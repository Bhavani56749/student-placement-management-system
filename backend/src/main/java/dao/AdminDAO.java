package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {

    // ==============================
    // ADMIN LOGIN
    // ==============================

    public boolean validateAdmin(String email, String password) {

        String sql =
                "SELECT admin_id " +
                "FROM admins " +
                "WHERE email = ? AND password = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                return resultSet.next();
            }

        } catch (Exception e) {

            System.out.println(
                    "Admin login database error:"
            );

            e.printStackTrace();

            return false;
        }
    }
}