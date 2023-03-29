package com.example.weather_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class SignUpActivity extends AppCompatActivity {

    EditText full_name, signUp_email, signUp_password, phone_number;
    TextView submit;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        full_name = findViewById(R.id.fullname);
        signUp_email = findViewById(R.id.emailUp);
        signUp_password = findViewById(R.id.passwordUp);
        phone_number = findViewById(R.id.number);
        submit = findViewById(R.id.submit_btn);

        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(view -> {
            createUser();
        });
    }

    private void createUser() {
       String email = signUp_email.getText().toString();
       String password = signUp_password.getText().toString();

       if(TextUtils.isEmpty(email)){
           signUp_email.setError("Email cannot be empty");
           signUp_email.requestFocus();
        }else if(TextUtils.isEmpty(password)){
           signUp_password.setError("Password cannot be empty");
           signUp_password.requestFocus();
       }else{
           mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful()){
                       Toast.makeText(SignUpActivity.this, "User Account Created Successfully", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                   }else{
                       Toast.makeText(SignUpActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
               }
           });
       }
    }
}