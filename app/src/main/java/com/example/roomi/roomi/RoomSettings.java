package com.example.roomi.roomi;


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


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RoomSettings extends AppCompatActivity {

    private TextView nameView;
    private EditText temperatureInput;
    private EditText brightnessInput;
    private Button submitButton;
    private Bundle extras;
    private String nameVal;

    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    RoomDatastructure data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_settings);

        extras = getIntent().getExtras();
        nameVal = extras.getString("name");

        setTitle(nameVal);
        findViews();
        getDatabase();

        nameView.setText("Update Settings");
        temperatureInput.setHint("Current: " + extras.getInt("temperature") + getString(R.string.degrees_cel));
        brightnessInput.setHint("Current: " + extras.getInt("brightness") + getString(R.string.percent));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_delete);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    int temperature = Integer.parseInt(temperatureInput.getText().toString());
                    int brightness = Integer.parseInt(brightnessInput.getText().toString());
                    dbRef.child("temperature").setValue(temperature);
                    dbRef.child("brightness").setValue(brightness);
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
                dbRef.removeValue();
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
        temperatureInput = findViewById(R.id.update_temperature_input);
        brightnessInput = findViewById(R.id.update_brightness_input);
        nameView = findViewById(R.id.room_name);
        submitButton = findViewById(R.id.update_room_button);
    }

    private boolean validateData() {
        int temperature = 0;
        int brightness = 0;

        try {
            temperature = Integer.parseInt(temperatureInput.getText().toString());
            brightness = Integer.parseInt(brightnessInput.getText().toString());
        } catch (Exception e) {
            Log.d("IntParse", e.toString());
        }

        if (temperature < 15 || temperature > 30) return false;
        if (brightness < 0 || brightness > 100) return false;
        return true;
    }
}