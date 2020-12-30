package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private Button registerButton,homeButton;
    private FirebaseAuth mAuth;
    private EditText fullName, registerEmail, registerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);

        fullName = (EditText) findViewById(R.id.registrationName);
        registerEmail = (EditText) findViewById(R.id.registrationEmail);
        registerPassword = (EditText) findViewById(R.id.registrationPassword);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.homeButton):
                startActivity(new Intent(this,MainActivity.class));
                break;
            case (R.id.registerButton):
                registerUser();
                break;
        }
    }
    private void registerUser(){
        String email = registerEmail.getText().toString().trim();
        String name = fullName.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();
        String noteDefault = "Write your note here";
        String titleDefault = "Write your title here";

        if (email.isEmpty()){
            registerEmail.setError("Email is required!");
            registerEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            registerEmail.setError("Please provide valid email!");
            registerEmail.requestFocus();
            return;
        }
        if(name.isEmpty()){
            fullName.setError("Full Name is required!");
            fullName.requestFocus();
            return;
        }
        if (password.isEmpty()){
            registerPassword.setError("Password is required!");
            registerPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            registerPassword.setError("Too short!");
            registerPassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name,email,1);
                            Note note = new Note(noteDefault,titleDefault);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this,"User has been registered successfully!",Toast.LENGTH_LONG).show();;
                                    }else
                                        Toast.makeText(RegisterUser.this, "Failed to register!",Toast.LENGTH_LONG).show();
                                }
                            });
                            FirebaseDatabase.getInstance().getReference("Notes")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }else{
                            Toast.makeText(RegisterUser.this, "Failed to register!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}