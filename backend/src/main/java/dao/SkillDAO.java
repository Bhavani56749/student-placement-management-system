package dao;

import model.Skill;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO {

    // GET ALL
    public List<Skill> getAllSkills() {

        List<Skill> skills = new ArrayList<>();

        String sql = "SELECT * FROM skills";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Skill skill = new Skill();

                skill.setSkillId(resultSet.getInt("skill_id"));
                skill.setSkillName(resultSet.getString("skill_name"));

                skills.add(skill);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return skills;
    }


    // GET BY ID
    public Skill getSkillById(int skillId) {

        String sql = "SELECT * FROM skills WHERE skill_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, skillId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Skill skill = new Skill();

                skill.setSkillId(resultSet.getInt("skill_id"));
                skill.setSkillName(resultSet.getString("skill_name"));

                return skill;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    // ADD
    public boolean addSkill(Skill skill) {

        String sql = "INSERT INTO skills (skill_name) VALUES (?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, skill.getSkillName());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // UPDATE
    public boolean updateSkill(Skill skill) {

        String sql = "UPDATE skills SET skill_name = ? WHERE skill_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, skill.getSkillName());
            statement.setInt(2, skill.getSkillId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // DELETE
    public boolean deleteSkill(int skillId) {

        String sql = "DELETE FROM skills WHERE skill_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, skillId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}