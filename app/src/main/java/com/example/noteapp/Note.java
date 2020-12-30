package com.example.noteapp;

public class Note {
    private String note,title;
    int numOfNote;
    public Note(){

    }

    public Note(String note,String title) {
        this.note = note;
        this.title = title;
    }

    public String getNote(){
        return note;
    }
    public String getTitle(){
        return title;
    }
}
