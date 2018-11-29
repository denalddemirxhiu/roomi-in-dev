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

public class PersonnelSettings extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private EditText nameInput;
    private EditText accessLevelInput;
    private EditText avatarColourInput;

    private Bundle extras;
    private String nameVal;
    private Button submitButton;

    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    RoomDatastructure data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_settings);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        extras = getIntent().getExtras();
        nameVal = extras.getString("name");

//        TODO Hint in text fields not working
//        nameInput.setHint("Current: " + nameVal);
//        accessLevelInput.setHint("Current: " + extras.getInt("accessLevel"));
//        avatarColourInput.setHint("Current: " + extras.getInt("avatarColour"));

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
                    String avatarColour = avatarColourInput.getText().toString();
                    int accessLevel = Integer.parseInt(accessLevelInput.getText().toString());
                    dbRef.child("name").setValue(name);
                    dbRef.child("accessLevel").setValue(accessLevel);
                    dbRef.child("avatarColour").setValue(avatarColour);
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
        dbRef = database.getReference("personnel/" + nameVal);
    }

    private void findViews() {
        accessLevelInput = findViewById(R.id.update_access_level_input);
        nameInput = findViewById(R.id.update_security_name_input);
        avatarColourInput = findViewById(R.id.update_avatar_colour_input);
        submitButton = findViewById(R.id.update_personnel_button);
    }

    private boolean validateData() {
        int accessLevel = 0;
        String name = nameInput.getText().toString();
        String avatarColour = avatarColourInput.getText().toString();


        try {
            accessLevel = Integer.parseInt(accessLevelInput.getText().toString());
        } catch (Exception e) {
            Log.d("IntParse", e.toString());
        }

        if (name.length() < 0 || name.length() > 25) return false;

        //TODO Validate avatarColour more than length???
        if (avatarColour.length() < 0 || name.length() > 25) return false;
        if (accessLevel < 0 || accessLevel > 5) return false;
        return true;
    }
}