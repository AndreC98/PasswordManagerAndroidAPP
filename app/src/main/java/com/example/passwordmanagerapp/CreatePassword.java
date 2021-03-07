package com.example.passwordmanagerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreatePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_password);

        Button createPass = (Button) findViewById(R.id.createBttn);
        createPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePassword();
            }
        });
    }

    public void savePassword() {

    }
}
