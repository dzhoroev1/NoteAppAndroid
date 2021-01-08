package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button logOut, noteButton1,noteButton2,noteButton3,noteButton4,noteButton5;
    private TextView userText;
    private FirebaseUser user;
    private DatabaseReference userData,noteData;
    private String userID;
    private LinearLayout myLayout;
    private int numberOfNotesInt;

    private List<Note> listNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userText = (TextView) findViewById(R.id.userInfo);

        logOut = (Button) findViewById(R.id.signOutButton);
        noteButton1 = (Button) findViewById(R.id.note1Button);
        noteButton2 = (Button) findViewById(R.id.note2Button);
        noteButton3 = (Button) findViewById(R.id.note3Button);
        noteButton4 = (Button) findViewById(R.id.note4Button);
        noteButton5 = (Button) findViewById(R.id.note5Button);


        noteButton1.setOnClickListener(this);
        noteButton2.setOnClickListener(this);
        noteButton3.setOnClickListener(this);
        noteButton4.setOnClickListener(this);
        noteButton5.setOnClickListener(this);

        logOut.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userData = FirebaseDatabase.getInstance().getReference("Users");
        noteData = FirebaseDatabase.getInstance().getReference("Notes");
        userID = user.getUid();

        userData.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                String name = userProfile.name;
                String email = userProfile.email;
                numberOfNotesInt = userProfile.getNumberOfNotes();
                userText.setText("Welcome, \n" + name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        noteData.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Note note = snapshot.getValue(Note.class);
                String title1 = note.getTitle1();
                noteButton1.setText("Note: " + title1);
                String title2 = note.getTitle2();
                noteButton2.setText("Note: " + title2);
                String title3 = note.getTitle3();
                noteButton3.setText("Note: " + title3);
                String title4 = note.getTitle4();
                noteButton4.setText("Note: " + title4);
                String title5 = note.getTitle5();
                noteButton5.setText("Note: " + title5);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signOutButton:
                FirebaseAuth.getInstance().signOut();
                deleteSaveUser();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.note1Button:
                startActivity(new Intent(this,note_taking.class));
                break;
            case R.id.note2Button:
                startActivity(new Intent(this,note_taking2.class));
                break;
            case R.id.note3Button:
                startActivity(new Intent(this,note_taking3.class));
                break;
            case R.id.note4Button:
                startActivity(new Intent(this,note_taking4.class));
                break;
            case R.id.note5Button:
                startActivity(new Intent(this,note_taking5.class));
                break;
        }

    }


    private void deleteSaveUser(){
        SharedPreferences preferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", "nothing");
        editor.putString("password","nothing");
        editor.putString("saved","false");
        editor.apply();
    }
}