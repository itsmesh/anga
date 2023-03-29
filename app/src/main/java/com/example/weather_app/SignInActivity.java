package com.example.weather_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;


public class SignInActivity extends AppCompatActivity {

    TextView signUp, emailUp, passwordUp;
    FirebaseAuth mAuth;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signUp = findViewById(R.id.signUp);
        emailUp = findViewById(R.id.email_in);
        passwordUp = findViewById(R.id.pass_in);
        login = findViewById(R.id.login_btn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSignUp = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(goToSignUp);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(view -> {
            sign_in_fb();
        });

    }

    private void sign_in_fb() {

        String email = emailUp.getText().toString();
        String password = passwordUp.getText().toString();

        if(TextUtils.isEmpty(email)){
            emailUp.setError("Email cannot be empty");
            emailUp.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            passwordUp.setError("Password cannot be empty");
            passwordUp.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignInActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this, MpesaStkActivity.class));
                    }else{
                        Toast.makeText(SignInActivity.this, "Sign In Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
