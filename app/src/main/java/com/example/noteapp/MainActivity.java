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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button signUp,signIn;
    private EditText logInEmail, logInPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = (Button) findViewById(R.id.registrationButton);
        signUp.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signInButton);
        signIn.setOnClickListener(this);

        logInEmail = (EditText) findViewById(R.id.loginID);
        logInPassword = (EditText) findViewById(R.id.loginPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.registrationButton:
                startActivity(new Intent(this,RegisterUser.class));
                break;
            case R.id.signInButton:
                userLogin();
                break;
        }
    }

    private void userLogin(){
        String email = logInEmail.getText().toString().trim();
        String password = logInPassword.getText().toString().trim();

        if(email.isEmpty()){
            logInEmail.setError("Email is required!");
            logInEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            logInEmail.setError("Please enter a valid email!");
            logInEmail.requestFocus();
            return;
        }
        if (password.length() < 6){
            logInPassword.setError("Password should be longer than 6");
            logInPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to user activity
                    startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                }else{
                    Toast.makeText(MainActivity.this,"Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();;
                }
            }
        });
    }
}