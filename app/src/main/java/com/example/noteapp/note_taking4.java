package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class note_taking4 extends AppCompatActivity implements View.OnClickListener {
    private EditText noteText4, titleText4;
    private Button saveButton4;
    private DatabaseReference userNote;
    private FirebaseUser user;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taking4);

        noteText4 = (EditText) findViewById(R.id.noteText4);
        titleText4 = (EditText) findViewById(R.id.titleOfNote4);

        saveButton4 = (Button) findViewById(R.id.saveButton4);
        saveButton4.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userNote = FirebaseDatabase.getInstance().getReference("Notes");
        userID = user.getUid();

        userNote.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Note note = snapshot.getValue(Note.class);
                String text_note = note.getNote4();
                String title_note = note.getTitle4();
                noteText4.setText(text_note);
                titleText4.setText(title_note);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveButton4:
                saveText();
                startActivity(new Intent(note_taking4.this,ProfileActivity.class));
                break;
        }
    }

    private void saveText(){
        userNote.child(userID).child("title4").setValue(titleText4.getText().toString().trim());
        userNote.child(userID).child("note4").setValue(noteText4.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(note_taking4.this,"Note Added",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(note_taking4.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
            }
        });
    }
}