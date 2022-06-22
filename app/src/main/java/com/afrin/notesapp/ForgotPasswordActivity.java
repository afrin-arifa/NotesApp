package com.afrin.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText mForgotPassword;
    private Button mPasswordRecoverButton;
    private TextView mGoBackToLogin;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().hide();


        mForgotPassword = findViewById(R.id.forgotPassword);
        mPasswordRecoverButton = findViewById(R.id.passwordRecoverButton);
        mGoBackToLogin = findViewById(R.id.goBackToLogin);

        firebaseAuth = FirebaseAuth.getInstance();


        mGoBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this,MainActivity.class));
            }
        });

        mPasswordRecoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mForgotPassword.getText().toString().trim();

                if (mail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Your mail first", Toast.LENGTH_SHORT).show();
                }else {

                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Mail Sent, You can Recover your Password using Mail", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPasswordActivity.this,MainActivity.class));
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Mail is Wrong or Account Not Exist ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });


    }
}