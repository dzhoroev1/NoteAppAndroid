package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button logOut, myNotes;
    private TextView userText;
    private FirebaseUser user;
    private DatabaseReference userData,noteData;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logOut = (Button) findViewById(R.id.signOutButton);
        userText = (TextView) findViewById(R.id.userInfo);
        myNotes= (Button) findViewById(R.id.notesButton);

        myNotes.setOnClickListener(this);
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

                userText.setText("Welcome, " + name+" "+email);
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
                myNotes.setText(title);
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
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.notesButton:
                startActivity(new Intent(this,note_taking.class));
                break;
        }

    }
}