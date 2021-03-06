package com.example.passwordmanagerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Credentials> allObjects = new ArrayList<>();
    private Credentials object;
    private FileOutputStream fileOut;
    private ObjectOutputStream out;
    private ObjectOutputStream buffer;
    private FileInputStream fileIn;
    private ObjectInputStream in;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allObjects = load();
        listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<Credentials> adapter = new ArrayAdapter<Credentials>(MainActivity.this, android.R.layout.simple_list_item_1,allObjects);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                object = allObjects.get(position);
                editView(object);
            }
        });
    }


    public void addView(View v) {
        setContentView(R.layout.add);
    }

    public void addCredentials(View v) {
        View application = findViewById(R.id.addApplication);
            TextView appView = (TextView) application;
                String app = appView.getText().toString();
        View username = findViewById(R.id.addUsername);
            TextView usrView = (TextView) username;
                String usr = usrView.getText().toString();
        View password = findViewById(R.id.addPassword);
            TextView passView = (TextView) password;
                String pass = passView.getText().toString();

        if(app.length() > 0 || usr.length() > 0 || pass.length() > 0) {
            allObjects = load();
            Credentials cred = new Credentials(app, usr, pass);
            allObjects.add(cred);
            save(allObjects);
            Toast.makeText(this, "Credentials added successfully", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_LONG).show();
        }
    }

    public void goBack(View v) {
        this.recreate();
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

    private void editView(Credentials edit) {
        setContentView(R.layout.edit);
        EditText editApp = (EditText) findViewById(R.id.editApplication);
            editApp.setText(edit.getWebsite());
        EditText editUsr = (EditText)findViewById(R.id.editUsername);
            editUsr.setText(edit.getUsername());
        EditText editPass = (EditText) findViewById(R.id.editPassword);
            editPass.setText(edit.getPassword());
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
            save(allObjects);
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_LONG).show();
        }
    }

    public void delete(View v) {
        allObjects.remove(object);
        save(allObjects);
        this.recreate();
    }
}