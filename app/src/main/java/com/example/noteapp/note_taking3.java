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

public class note_taking3 extends AppCompatActivity implements View.OnClickListener {
    private EditText noteText3, titleText3;
    private Button saveButton3;
    private DatabaseReference userNote;
    private FirebaseUser user;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taking3);

        noteText3 = (EditText) findViewById(R.id.noteText3);
        titleText3 = (EditText) findViewById(R.id.titleOfNote3);

        saveButton3 = (Button) findViewById(R.id.saveButton3);
        saveButton3.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userNote = FirebaseDatabase.getInstance().getReference("Notes");
        userID = user.getUid();

        userNote.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Note note = snapshot.getValue(Note.class);
                String text_note = note.getNote3();
                String title_note = note.getTitle3();
                noteText3.setText(text_note);
                titleText3.setText(title_note);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveButton3:
                saveText();
                startActivity(new Intent(note_taking3.this,ProfileActivity.class));
                break;
        }
    }

    private void saveText(){
        userNote.child(userID).child("title3").setValue(titleText3.getText().toString().trim());
        userNote.child(userID).child("note3").setValue(noteText3.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(note_taking3.this,"Note Added",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(note_taking3.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
            }
        });
    }
}