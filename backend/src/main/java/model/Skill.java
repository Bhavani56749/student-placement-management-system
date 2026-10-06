package model;

public class Skill {

    private int skillId;
    private String skillName;
    private String proficiencyLevel;

    public Skill() {
    }

    public Skill(
            int skillId,
            String skillName,
            String proficiencyLevel) {

        this.skillId = skillId;
        this.skillName = skillName;
        this.proficiencyLevel = proficiencyLevel;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(
            String proficiencyLevel) {

        this.proficiencyLevel = proficiencyLevel;
    }
}