package com.example.shareyourfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private ShareYourFoodApp app;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        app = ShareYourFoodApp.getInstance();
        db = app.getDb();

        EditText phone = findViewById(R.id.editTextPhone);
        EditText password = findViewById(R.id.editTextTextPassword);

        if (savedInstanceState != null)
        {
            phone.setText(savedInstanceState.getString("phone"));
            password.setText(savedInstanceState.getString("password"));
        }

        Button loginButton = findViewById(R.id.LoginButton);

        TextView signUpView = findViewById(R.id.signUpView);
        TextView wrongPassword = findViewById(R.id.incorrectPassword);
        TextView wrongPhone = findViewById(R.id.incorrectPhone);

        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        signUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signUpIntent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph = phone.getText().toString();
                db.collection("users").document(ph).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String realPhone = documentSnapshot.getString("phone");
                        String realName = documentSnapshot.getString("name");
                        String realPassword = documentSnapshot.getString("password");
                        String currPassword = password.getText().toString();
                        if (realPhone == null)
                        {
                            wrongPassword.setVisibility(View.GONE);
                            wrongPhone.setVisibility(View.VISIBLE);
                        }
                        else if (realPassword.equals(currPassword))
                        {
                            //TODO - open food list activity
                            Toast.makeText(getApplicationContext(), "successfully login", Toast.LENGTH_LONG).show();
                            wrongPassword.setVisibility(View.GONE);
                            wrongPhone.setVisibility(View.GONE);
                        }
                        else
                        {
                            wrongPassword.setVisibility(View.VISIBLE);
                            wrongPhone.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        EditText phone = findViewById(R.id.editTextPhone);
        EditText password = findViewById(R.id.editTextTextPassword);
        outState.putString("phone", phone.getText().toString());
        outState.putString("password", password.getText().toString());

    }
}
