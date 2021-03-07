package com.example.passwordmanagerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class Login extends AppCompatActivity {
    private FileInputStream fileIn;
    private ObjectInputStream in;
    private String password;
    private String guess = "";
    private TextView guessedPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void onStart() {
        Context context = this;
        try {
            fileIn = context.openFileInput("AccountManagerPassword.ser");
            in = new ObjectInputStream(fileIn);
            password = (String) in.readObject();
        } catch(IOException | ClassNotFoundException e){}
        Button createButton = (Button) findViewById(R.id.createBttn);
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(password != null && !(password.isEmpty())) {
                        errorMsg();
                    } else {
                        openCreateActivity();
                    }
                }
            });
        Button submitButton = (Button) findViewById(R.id.loginBttn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        super.onStart();
    }

    public void onResume() {
        super.onResume();
    }

    public void openCreateActivity() {
        Intent intent = new Intent(this, CreatePassword.class);
        startActivity(intent);
    }

    public void signIn() {
        if(guess.equals(password)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Incorrect password", Toast.LENGTH_LONG).show();
        }
    }

    public void guessedNum(View v){
        guess += ((Button) v).getText().toString();
        setText();
    }

    public void setText() {
        guessedPass = (TextView) findViewById(R.id.guessedPass);
        guessedPass.setText(guess);
    }

    public void backSpace(View v) {
        if(guess.length() > 0) {
            guess = guess.substring(0, guess.length() - 1);
            setText();
        } else {
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_LONG).show();
        }
    }

    public void errorMsg() {
        Toast.makeText(this, "Password already set", Toast.LENGTH_LONG).show();
    }
}
