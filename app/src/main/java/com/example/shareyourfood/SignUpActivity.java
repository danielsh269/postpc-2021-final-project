package com.example.shareyourfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    ShareYourFoodApp app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        app = ShareYourFoodApp.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        EditText fullName = findViewById(R.id.fullNameText);
        EditText phone = findViewById(R.id.editTextPhone);
        EditText password = findViewById(R.id.editTextTextPassword);

        if (savedInstanceState != null)
        {
            fullName.setText(savedInstanceState.getString("name"));
            phone.setText(savedInstanceState.getString("phone"));
            password.setText(savedInstanceState.getString("password"));
        }

        TextView missingFieldsView = findViewById(R.id.missingFieldsView);

        Button signUpButton = findViewById(R.id.SignUpButton);

        TextView login = findViewById(R.id.signUpView);

        Intent loginIntent = new Intent(this, LoginActivity.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(loginIntent);
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullName.getText().toString();
                String telephone = phone.getText().toString();
                String pass = password.getText().toString();


                if (name.equals("") || telephone.equals("") || pass.equals(""))
                {
                    missingFieldsView.setVisibility(View.VISIBLE);
                }
                else
                {
                    User user = new User(name, telephone, pass);
                    db.collection("users").document(user.getPhone()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "successfully signed up", Toast.LENGTH_LONG).show();
                            startActivity(loginIntent);
                            finish();
                        }
                    });


                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        EditText fullName = findViewById(R.id.fullNameText);
        EditText phone = findViewById(R.id.editTextPhone);
        EditText password = findViewById(R.id.editTextTextPassword);
        outState.putString("name", fullName.getText().toString());
        outState.putString("phone", phone.getText().toString());
        outState.putString("password", password.getText().toString());
    }
}
