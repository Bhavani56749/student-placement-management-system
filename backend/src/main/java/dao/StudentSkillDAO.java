package dao;

import model.Skill;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentSkillDAO {

    // GET SKILLS FOR A PARTICULAR STUDENT
    public List<Skill> getSkillsByStudentId(int studentId) {

        List<Skill> skills = new ArrayList<>();

        String sql =
                "SELECT s.skill_id, s.skill_name, " +
                "ss.proficiency_level " +
                "FROM student_skills ss " +
                "JOIN skills s " +
                "ON ss.skill_id = s.skill_id " +
                "WHERE ss.student_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, studentId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                Skill skill = new Skill();

                skill.setSkillId(
                        resultSet.getInt("skill_id")
                );

                skill.setSkillName(
                        resultSet.getString("skill_name")
                );

                skill.setProficiencyLevel(
                        resultSet.getString(
                                "proficiency_level"
                        )
                );

                skills.add(skill);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return skills;
    }
}