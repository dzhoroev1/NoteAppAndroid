package com.example.noteapp;

public class Note {
    private String note1,note2,note3,note4,note5;
    private String title1,title2,title3,title4,title5;
    public Note(){

    }

    public Note(String note1,String title1,String note2,String title2,String note3,String title3,String note4,String title4,String note5,String title5) {
        this.note1 = note1;
        this.title1 = title1;
        this.note2= note2;
        this.title2 = title2;
        this.note3 = note3;
        this.title3 = title3;
        this.note4= note4;
        this.title4 = title4;
        this.note5 = note5;
        this.title5 = title5;
    }

    public String getNote1(){
        return note1;
    }
    public String getTitle1(){
        return title1;
    }

    public String getNote2() {return note2; }
    public String getTitle2(){
        return title2;
    }

    public String getNote3(){
        return note3;
    }
    public String getTitle3(){
        return title3;
    }

    public String getNote4(){
        return note4;
    }
    public String getTitle4(){
        return title4;
    }

    public String getNote5() {return note5; }
    public String getTitle5(){
        return title5;
    }



}
