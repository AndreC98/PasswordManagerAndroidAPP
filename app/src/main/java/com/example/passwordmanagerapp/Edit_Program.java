package com.example.passwordmanagerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Edit_Program extends AppCompatActivity {

    private FileOutputStream fileOut;
    private ObjectOutputStream out;
    private FileInputStream fileIn;
    private ObjectInputStream in;
    private ArrayList<Credentials> allObjects = new ArrayList<>();
    private Credentials object;
    private Credentials editedObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);;
    }
    public void onStart() {
        load();
        editView();
        super.onStart();
    }
    private void editView() {
        Intent i = getIntent();
        object = (Credentials) i.getSerializableExtra("Object");
        editedObject = object;
        setContentView(R.layout.edit);
        EditText editApp = (EditText) findViewById(R.id.editApplication);
        editApp.setText(object.getWebsite());
        EditText editUsr = (EditText)findViewById(R.id.editUsername);
        editUsr.setText(object.getUsername());
        EditText editPass = (EditText) findViewById(R.id.editPassword);
        editPass.setText(object.getPassword());

        FloatingActionButton backBttn2 = (FloatingActionButton) findViewById(R.id.backHomeButtonEdit);
        backBttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void save(ArrayList<Credentials> allObjects) {
        Context context = this;
        try{
            fileOut = context.openFileOutput("saved.ser", Context.MODE_PRIVATE);
            out = new ObjectOutputStream(fileOut);
            for(int i = 0; i < allObjects.size(); i++){
                this.out.writeObject(allObjects.get(i));
            }
        } catch(Exception e) {
            Toast.makeText(this, "Error saving", Toast.LENGTH_LONG).show();
        }
    }

    public void saveEdit(View v) {
        View application = findViewById(R.id.editApplication);
        TextView appView = (TextView) application;
        String updatedApp = appView.getText().toString();
        View username = findViewById(R.id.editUsername);
        TextView usrView = (TextView) username;
        String updatedUsr = usrView.getText().toString();
        View password = findViewById(R.id.editPassword);
        TextView passView = (TextView) password;
        String updatedPass = passView.getText().toString();

        if(updatedApp.length() > 0 && updatedUsr.length() > 0 && updatedPass.length() > 0) {
            object.setWebsite(updatedApp);
            object.setUsername(updatedUsr);
            object.setPassword(updatedPass);
            for (int i = 0; i < allObjects.size(); i ++) {
                if(allObjects.get(i).getUsername().equals(editedObject.getUsername()) ||
                        allObjects.get(i).getWebsite().equals(editedObject.getWebsite()) ||
                            allObjects.get(i).getPassword().equals(editedObject.getPassword())) {

                    allObjects.remove(i);
                    allObjects.add(i,object);
                }
            }
            save(allObjects);
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_LONG).show();
        }
    }

    public void delete(View v) {
        allObjects.remove(object);
        save(allObjects);
    }

    private ArrayList<Credentials> load(){
        allObjects.clear();
        boolean loop = true;
        Context context = this;
        try{
            fileIn =  context.openFileInput("saved.ser");
            in = new ObjectInputStream(fileIn);
            while(loop) {
                object = (Credentials) in.readObject();
                if(object != null) {
                    allObjects.add(object);
                } else {
                    loop = false;
                }
            }
        } catch(IOException | ClassNotFoundException e){}
        return allObjects;
    }
}
