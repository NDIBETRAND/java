package com.example.gym;

public class Coach {
    private int coachID;
    private String name;
    private String phone;
    private String gender;
    private String domain;
    private String address;

    public Coach(String name, String phone, String gender, String domain,String address) {
        this.coachID = coachID;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.domain = domain;
        this.address=address;
    }

    // Getters and Setters

    public int getCoachID() { return coachID; }
    public void setCoachID(int coachID) { this.coachID = coachID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
