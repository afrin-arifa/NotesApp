package com.afrin.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText signUpEmail, signUpPassword;
    private RelativeLayout signUp;
    private TextView goToLogin;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        signUpEmail = findViewById(R.id.signupEmail);
        signUpPassword = findViewById(R.id.signupPassword);
        signUp = findViewById(R.id.signup);
        goToLogin = findViewById(R.id.goToLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = signUpEmail.getText().toString().trim();
                String password = signUpPassword.getText().toString().trim();

                if (mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All Fields are Required", Toast.LENGTH_SHORT).show();
                }else if (password.length()<5){
                    Toast.makeText(getApplicationContext(), "Password Should Greater than 5 Digits", Toast.LENGTH_SHORT).show();
                }else {

                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Failed to Register", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });

    }

    private void sendEmailVerification() {

       firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser !=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Verification Email is Sent, Verify and Log In Again", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "Failed to Send Verification Email", Toast.LENGTH_SHORT).show();
        }


    }
}