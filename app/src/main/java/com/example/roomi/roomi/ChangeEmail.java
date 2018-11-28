package com.example.roomi.roomi;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeEmail extends AppCompatActivity {

    private EditText emailEditText, reEnterEmailEditText;
    private Button submitButton;
    private FirebaseUser user;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_change_email);
        setTitle("Change Email");

        findViews();
        user = FirebaseAuth.getInstance().getCurrentUser();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.hideKeyboard(ChangeEmail.this);
                final String email = emailEditText.getText().toString().trim();
                String confirmEmail = reEnterEmailEditText.getText().toString().trim();

                if (email.isEmpty()) {
                    emailEditText.setError("Enter an email");
                    return;
                }

                if (confirmEmail.isEmpty()) {
                    reEnterEmailEditText.setError("Please confirm your email");
                    return;
                }

                if (!email.equals(confirmEmail)) {
                    Toast.makeText(getApplicationContext(), "Emails must match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                user.updateEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    updateEmail(email);
                                    Toast.makeText(getApplicationContext(), "Email address was updated.", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed to update email!", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        });


            }
        });
    }

    private void updateEmail(String email) {
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
        String userId = user.getUid();
        mReference.child("users").child(userId).child("email").setValue(email);

    }


    private void findViews() {
        emailEditText = findViewById(R.id.emailEditText);
        reEnterEmailEditText = findViewById(R.id.reEnterEmailEditText);
        submitButton = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progressBar);
    }
}
