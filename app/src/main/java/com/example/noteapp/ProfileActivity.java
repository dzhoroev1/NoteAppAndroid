package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button logOut, myNotes, addNote;
    private TextView userText;
    private FirebaseUser user;
    private DatabaseReference userData,noteData;
    private String userID;
    private LinearLayout myLayout;
    private int numberOfNotesInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userText = (TextView) findViewById(R.id.userInfo);

        logOut = (Button) findViewById(R.id.signOutButton);
        myNotes= (Button) findViewById(R.id.notesButton);
        addNote = (Button) findViewById(R.id.addNote);

        myLayout = (LinearLayout) findViewById(R.id.addNoteBox);

        myNotes.setOnClickListener(this);
        logOut.setOnClickListener(this);
        addNote.setOnClickListener(this);

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
                userText.setText("Welcome, " + name+" "+email + "\n numOFNotes: "+numberOfNotesInt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        noteData.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Note note = snapshot.getValue(Note.class);
                String title = note.getTitle();
                myNotes.setText("Note: " + title);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        createNotesButtons();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signOutButton:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.notesButton:
                startActivity(new Intent(this,note_taking.class));
                break;
            case R.id.addNote:
                addNoteButton();
                break;
        }

    }
    private void addNoteButton() {

        Button newNoteButton = new Button(getApplicationContext());
        newNoteButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        newNoteButton.setText("hi im new");
        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, note_taking.class));
            }
        });
        myLayout.addView(newNoteButton);

    }
    private void createNotesButtons(){
        int count = 0;
        while(count <= numberOfNotesInt){
            Button newNoteButton = new Button(getApplicationContext());
            newNoteButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            newNoteButton.setText("note #" + (count+1));
            newNoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ProfileActivity.this, note_taking.class));
                }
            });
            myLayout.addView(newNoteButton);
            count++;
        }
    }
}