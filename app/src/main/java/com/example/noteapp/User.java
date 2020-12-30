package com.example.noteapp;

public class User {
    public String name, email;
    public int numberOfNotes;

    public User(){

    }

    public User(String name, String email, int numberOfNotes) {
        this.name = name;
        this.email = email;
        this.numberOfNotes = numberOfNotes;
    }

    public int getNumberOfNotes() {
        return numberOfNotes;
    }
}
