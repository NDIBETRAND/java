package com.example.gym;

import java.time.LocalDate;

public class Member {
    private String customerID;
    private String name;
    private String phone;
    private String gender;
    private int age;
    private String address;
    private String domain;
    private LocalDate start_date;
    private LocalDate end_date;
    private String status;

    public Member(String customerID, String name, String phone, String gender, int age, String address, String domain, LocalDate start_date, LocalDate end_date, String status) {
        this.customerID = customerID;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.domain = domain;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
    }

    // Getters and Setters
    public String getCustomerID() { return customerID; }
    public void setCustomerID(String customerID) { this.customerID = customerID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }

    public LocalDate getStartDate() { return start_date; }
    public void setStartDate(LocalDate start_date) { this.start_date = start_date; }

    public LocalDate getEndDate() { return end_date; }
    public void setEndDate(LocalDate end_date) { this.end_date = end_date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
