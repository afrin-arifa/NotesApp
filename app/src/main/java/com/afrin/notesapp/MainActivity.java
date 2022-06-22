package com.afrin.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private RelativeLayout login, goToSignup;
    private TextView goToForgotPassword;

    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        loginEmail = findViewById(R.id.loginemail);
        loginPassword = findViewById(R.id.loginpassword);
        login = findViewById(R.id.login);
        goToSignup = findViewById(R.id.gotosignup);
        goToForgotPassword = findViewById(R.id.gotoforgotpassword);
        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this, NotesActivity.class));
        }



        goToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
        goToForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                if (mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All Field Are Required", Toast.LENGTH_SHORT).show();
                }else {

                    progressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()){
                                checkmailverfication();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Account Doesn't Exist", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                        }
                    });

                    
                }

            }
        });



    }

    private void checkmailverfication() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser.isEmailVerified()==true)
        {
            Toast.makeText(getApplicationContext(), "Logged In Successful", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this,NotesActivity.class));
        }
        else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Verify your mail first", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }


    }
}