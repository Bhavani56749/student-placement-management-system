package model;

public class Company {

    private int companyId;
    private String companyName;
    private String industry;
    private String location;
    private String website;
    private String contactPerson;
    private String contactEmail;

    public Company() {
    }

    public Company(int companyId, String companyName, String industry,
                   String location, String website,
                   String contactPerson, String contactEmail) {

        this.companyId = companyId;
        this.companyName = companyName;
        this.industry = industry;
        this.location = location;
        this.website = website;
        this.contactPerson = contactPerson;
        this.contactEmail = contactEmail;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}