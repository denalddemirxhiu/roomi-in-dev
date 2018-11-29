package com.example.roomi.roomi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SecurityRoomSettings extends AppCompatActivity {

    private EditText nameInput;
    private EditText accessLevelInput;
    private Bundle extras;
    private String nameVal;
    private Button submitButton;

    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    RoomDatastructure data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_room_settings);

        extras = getIntent().getExtras();
        nameVal = extras.getString("name");

        //        TODO Hint in text field
//        nameInput.setHint("Current: " + nameVal);
//        accessLevelInput.setHint("Current: " + extras.getInt("accessLevel"));

        setTitle(extras.getString("name"));
        findViews();
        getDatabase();


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    String name = nameInput.getText().toString();
                    int accessLevel = Integer.parseInt(accessLevelInput.getText().toString());
                    dbRef.child("name").setValue(name);
                    dbRef.child("accessLevel").setValue(accessLevel);
                    Toast toast = Toast.makeText(getApplicationContext(), "Updated " + nameVal, Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void getDatabase() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("rooms/" + nameVal);
    }

    private void findViews() {
        accessLevelInput = findViewById(R.id.update_access_level_input);
        nameInput = findViewById(R.id.update_security_name_input);
        submitButton = findViewById(R.id.update_security_room_button);
    }

    private boolean validateData() {
        int accessLevel = 0;
        String name = nameInput.getText().toString();

        try {
            accessLevel = Integer.parseInt(accessLevelInput.getText().toString());
        } catch (Exception e) {
            Log.d("IntParse", e.toString());
        }

        if (name.length() < 0 || name.length() > 25) return false;
        if (accessLevel < 0 || accessLevel > 5) return false;
        return true;
    }
}