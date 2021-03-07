package com.example.passwordmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void onStart() {
        Button createButton = (Button) findViewById(R.id.createBttn);
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        openCreateActivity();
                }
            });
        super.onStart();
    }

    public void openCreateActivity() {
        Intent intent = new Intent(this, CreatePassword.class);
        startActivity(intent);
    }
}
