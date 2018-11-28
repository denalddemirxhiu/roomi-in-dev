package com.example.roomi.roomi;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRoom extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    private EditText nameInput;
    private EditText temperatureInput;
    private EditText brightnessInput;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        setTitle(R.string.add_a_room);
        getDatabase();
        getElements();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    String name = nameInput.getText().toString();
                    int temperature = Integer.parseInt(temperatureInput.getText().toString());
                    int brightness = Integer.parseInt(brightnessInput.getText().toString());
                    dbRef.child(name).setValue(new RoomDatastructure(name, temperature, brightness, false, true));
                    Toast toast = Toast.makeText(getApplicationContext(), name + " created!", Toast.LENGTH_LONG);
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

    private void getElements() {
        nameInput = findViewById(R.id.add_room_name_input);
        temperatureInput = findViewById(R.id.add_room_temperature_input);
        brightnessInput = findViewById(R.id.add_room_brightness_input);
        submitButton = findViewById(R.id.add_room_button);
    }

    private void getDatabase() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("rooms");
    }

    private boolean validateData() {
        String name = nameInput.getText().toString();
        int temperature = 0;
        int brightness = 0;

        try {
            temperature = Integer.parseInt(temperatureInput.getText().toString());
            brightness = Integer.parseInt(brightnessInput.getText().toString());
        } catch (Exception e) {
            Log.d("IntParse", e.toString());
        }

        if (name.length() < 3 || name.length() > 25) return false;
        if (temperature < 15 || temperature > 30) return false;
        if (brightness < 0 || brightness > 100) return false;
        return true;
    }
}
