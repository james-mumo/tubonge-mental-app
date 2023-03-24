package com.jamesmumo.tubonge.model;

public class ModelProfessional {
    private String name;
    private String experience;
    private int photo;
    private String school;
    private String speciality;
    private int stars;



    public ModelProfessional(String name, String experience, int photo, String school, String speciality, int stars) {
        this.name = name;
        this.experience = experience;
        this.photo = photo;
        this.school = school;
        this.speciality = speciality;
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
