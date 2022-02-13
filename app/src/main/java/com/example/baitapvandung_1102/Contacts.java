package com.example.baitapvandung_1102;

public class Contacts {
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public Contacts(int id) {
        this.Id = id;
    }

    public Contacts(int id, String name, String phoneNumber, boolean status) {
        Id = id;
        Name = name;
        PhoneNumber = phoneNumber;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    private int Id;
    private String Name;
    private String PhoneNumber;

    public void setStatus(boolean status) {
        this.status = status;
    }

    private boolean status;
}
