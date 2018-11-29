package com.example.roomi.roomi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private EditText firstName, lastName, emailBox, passwordBox, rePassBox, username;
    private Button submitButton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setTitle("Register");
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();

        // Obtain views in the activity
        findViews();

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String email = emailBox.getText().toString().trim();
                final String password = passwordBox.getText().toString();
                String rePass = rePassBox.getText().toString();

                Utilities.hideKeyboard(Registration.this);

                if (firstName.getText().toString().isEmpty()) {
                    firstName.setError("First name is required!");
                    return;
                }

                if (lastName.getText().toString().isEmpty()) {
                    lastName.setError("Last name is required!");
                }

                if (email.isEmpty()) {
                    emailBox.setError("Email is required!");
                    return;
                }

                if (password.isEmpty()) {
                    passwordBox.setError("Password is required!");
                    return;
                }

                if (password.length() < 6) {
                    passwordBox.setError("Password must be longer than 6 characters");
                    return;
                }

                if (rePass.isEmpty()) {
                    rePassBox.setError("Password needs to be re-entered!");
                    return;
                }

                if (!password.equals(rePass)) {
                    Toast.makeText(getApplicationContext(), "Passwords must match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidUserException invalidEmail) {
                                        // User entered invalid email
                                        emailBox.setError("Enter a valid email!");
                                        Toast.makeText(getApplicationContext(), "Enter a valid email!", Toast.LENGTH_SHORT).show();
                                        return;

                                    } catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
                                        // User entered weak password
                                        passwordBox.setError("Password must contain at least 6 characters, numbers, and special characters!");
                                        Toast.makeText(getApplicationContext(), "Password must contain at least 6 characters, numbers, and special characters!", Toast.LENGTH_SHORT).show();
                                        return;
                                    } catch (FirebaseAuthUserCollisionException existEmail) {
                                        // Email used by another account
                                        emailBox.setError("Email used by another account!");
                                        Toast.makeText(getApplicationContext(), "Email used by another account!", Toast.LENGTH_SHORT).show();
                                        return;

                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } else {
                                    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                    mAuth.signInWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {

                                                    if (!task.isSuccessful()) {
                                                        Toast.makeText(Registration.this, "Failed to authenticate!", Toast.LENGTH_SHORT).show();
                                                    } else {

                                                        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
                                                        String userId = mAuth.getCurrentUser().getUid();

                                                        // Adding the information of the user
                                                        mReference.child("users").child(userId).child("firstName").setValue(firstName.getText().toString());
                                                        mReference.child("users").child(userId).child("lastName").setValue(lastName.getText().toString());
                                                        mReference.child("users").child(userId).child("email").setValue(email);
                                                        mReference.child("users").child(userId).child("username").setValue(username.getText().toString());

                                                        Toast.makeText(Registration.this, "Account successfully created!", Toast.LENGTH_SHORT).show();

                                                        Intent home = new Intent(Registration.this, HomeActivity.class);
                                                        startActivity(home);
                                                        finish();
                                                        
                                                    }
                                                }
                                            });


                                }
                            }


                        });




            }
        });

    }

    public void findViews() {
        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        emailBox = findViewById(R.id.emailEditText);
        passwordBox = findViewById(R.id.passwordEditText);
        rePassBox = findViewById(R.id.passwordAgainEditText);
        username = findViewById(R.id.usernameEditText);
        submitButton = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progressBar);
    }
}
