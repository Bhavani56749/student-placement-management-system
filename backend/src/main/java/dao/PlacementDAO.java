package dao;

import model.Placement;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlacementDAO {

    // GET ALL
    public List<Placement> getAllPlacements() {

        List<Placement> placements = new ArrayList<>();

        String sql = "SELECT * FROM placements";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Placement placement = new Placement();

                placement.setPlacementId(resultSet.getInt("placement_id"));
                placement.setStudentId(resultSet.getInt("student_id"));
                placement.setCompanyId(resultSet.getInt("company_id"));
                placement.setJobId(resultSet.getInt("job_id"));
                placement.setPlacementDate(
                        resultSet.getString("placement_date")
                );
                placement.setPackageAmount(
                        resultSet.getDouble("package")
                );
                placement.setPlacementStatus(
                        resultSet.getString("placement_status")
                );

                placements.add(placement);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return placements;
    }


    // GET BY ID
    public Placement getPlacementById(int placementId) {

        String sql = "SELECT * FROM placements WHERE placement_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, placementId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Placement placement = new Placement();

                placement.setPlacementId(
                        resultSet.getInt("placement_id")
                );
                placement.setStudentId(
                        resultSet.getInt("student_id")
                );
                placement.setCompanyId(
                        resultSet.getInt("company_id")
                );
                placement.setJobId(
                        resultSet.getInt("job_id")
                );
                placement.setPlacementDate(
                        resultSet.getString("placement_date")
                );
                placement.setPackageAmount(
                        resultSet.getDouble("package")
                );
                placement.setPlacementStatus(
                        resultSet.getString("placement_status")
                );

                return placement;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    // ADD
    public boolean addPlacement(Placement placement) {

        String sql = "INSERT INTO placements " +
                     "(student_id, company_id, job_id, placement_date, package, placement_status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, placement.getStudentId());
            statement.setInt(2, placement.getCompanyId());
            statement.setInt(3, placement.getJobId());
            statement.setString(4, placement.getPlacementDate());
            statement.setDouble(5, placement.getPackageAmount());
            statement.setString(6, placement.getPlacementStatus());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // UPDATE
    public boolean updatePlacement(Placement placement) {

        String sql = "UPDATE placements SET " +
                     "student_id = ?, " +
                     "company_id = ?, " +
                     "job_id = ?, " +
                     "placement_date = ?, " +
                     "package = ?, " +
                     "placement_status = ? " +
                     "WHERE placement_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, placement.getStudentId());
            statement.setInt(2, placement.getCompanyId());
            statement.setInt(3, placement.getJobId());
            statement.setString(4, placement.getPlacementDate());
            statement.setDouble(5, placement.getPackageAmount());
            statement.setString(6, placement.getPlacementStatus());
            statement.setInt(7, placement.getPlacementId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // DELETE
    public boolean deletePlacement(int placementId) {

        String sql = "DELETE FROM placements WHERE placement_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, placementId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}