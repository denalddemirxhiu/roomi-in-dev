package com.example.roomi.roomi;

import android.content.Intent;
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
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteAccount extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button deleteButton, cancelButton;
    private ProgressBar progressBar;
    private FirebaseUser user;
    private AuthCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_delete_account);

        findViews();
        user = FirebaseAuth.getInstance().getCurrentUser();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();

                if (email.isEmpty()) {
                    emailEditText.setError("Enter your email");
                    return;
                }

                if (password.isEmpty()) {
                    passwordEditText.setError("Enter your password!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                credential = EmailAuthProvider.getCredential(email, password);

                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()) {
                            final String uid = user.getUid();
                            final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("users/" + uid);

                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseAuth.getInstance().signOut();

                                        dbref.removeValue();

                                        Toast.makeText(getApplicationContext(),
                                                "Account successfully deactivated!",
                                                Toast.LENGTH_SHORT).show();

                                        Intent login = new Intent(DeleteAccount.this, MainActivity.class);

                                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                                        startActivity(login);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "Credentials don't match the database!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        deleteButton = findViewById(R.id.yes);
        cancelButton = findViewById(R.id.no);
        progressBar = findViewById(R.id.progressBar);
    }
}
