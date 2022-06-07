package com.example.wfhapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wfhapplication.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    EditText edtName, edtEmail, edtPw, edtContact, edtLoc;
    Button btnSignup, btnSignIn;

    FirebaseAuth auth;
    FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        edtEmail = findViewById(R.id.edtUserEmail);
        edtName = findViewById(R.id.edtUserName);
        edtPw = findViewById(R.id.edtPw);
        edtContact = findViewById(R.id.edtContact);
        edtLoc = findViewById(R.id.edtLoc);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignup = findViewById(R.id.btnSignUp);



        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              createUser();
            }

        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }

        });
    }

    private void createUser() {
        String userEmail = edtEmail.getText().toString();
        String userName = edtName.getText().toString();
        String userPassword = edtPw.getText().toString();
        String userContact = edtContact.getText().toString();
        String userLoc= edtLoc.getText().toString();

        if(TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Email is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Username is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Password is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length()<6) {
            Toast.makeText(this, "Password Length must be greater than 6 letter.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userContact)) {
            Toast.makeText(this, "Contact is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userLoc)) {
            Toast.makeText(this, "Living Location is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Create user
        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            UserModel userModel = new UserModel(userEmail,userName,userPassword,userContact,userLoc);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);

                            Toast.makeText(Signup.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Signup.this,"Error:" +task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });







    }
}