package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Weather_App.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    ImageView start;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        start = findViewById(R.id.start_btn);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user;
                user = mAuth.getCurrentUser();

                if (user != null){
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }else {
                    startActivity(new Intent(WelcomeActivity.this, SignInActivity.class));
                }
            }
        });
    }
}