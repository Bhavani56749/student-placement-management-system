package service;

import dao.SkillDAO;
import model.Skill;

import java.util.List;

public class SkillService {

    private SkillDAO skillDAO;

    public SkillService() {
        skillDAO = new SkillDAO();
    }

    public List<Skill> getAllSkills() {
        return skillDAO.getAllSkills();
    }

    public Skill getSkillById(int skillId) {
        return skillDAO.getSkillById(skillId);
    }

    public boolean addSkill(Skill skill) {
        return skillDAO.addSkill(skill);
    }

    public boolean updateSkill(Skill skill) {
        return skillDAO.updateSkill(skill);
    }

    public boolean deleteSkill(int skillId) {
        return skillDAO.deleteSkill(skillId);
    }
}