package com.example.noteapp;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class note_taking extends AppCompatActivity implements View.OnClickListener {
    private EditText noteText;
    private Button saveButton;
    private FirebaseFirestore db;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taking);

        noteText = (EditText) findViewById(R.id.noteText);
        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

       String notesId =  db.collection("notes").getId();

        dbRef.child(notesId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Note note = (Note) snapshot.getValue();
                String text;
                if (null != note.getNote()){
                    text = note.getNote().toString().trim();
                }else{
                    text = "Please write note here ...";
                }
                noteText.setText(text);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveButton:
                saveText();
                break;
        }
    }

    private void saveText(){

        this.db = FirebaseFirestore.getInstance();
        String noteString = noteText.getText().toString().trim();
        Note note = new Note(noteString);
        db.collection("notes").add(note)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(note_taking.this,"Note Added",Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(note_taking.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                                                }
                                            });
    }
}