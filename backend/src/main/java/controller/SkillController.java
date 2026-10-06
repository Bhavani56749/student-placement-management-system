package controller;

import model.Skill;
import service.SkillService;

import java.util.List;

public class SkillController {

    private SkillService skillService;

    public SkillController() {
        skillService = new SkillService();
    }

    // GET ALL SKILLS
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    // GET SKILL BY ID
    public Skill getSkillById(int skillId) {
        return skillService.getSkillById(skillId);
    }

    // ADD SKILL
    public boolean addSkill(Skill skill) {
        return skillService.addSkill(skill);
    }

    // UPDATE SKILL
    public boolean updateSkill(Skill skill) {
        return skillService.updateSkill(skill);
    }

    // DELETE SKILL
    public boolean deleteSkill(int skillId) {
        return skillService.deleteSkill(skillId);
    }
}