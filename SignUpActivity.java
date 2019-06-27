package com.example.filesharing_up.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.filesharing_up.R;
import com.example.filesharing_up.models.Fakultet;
import com.example.filesharing_up.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText emri_1;
    private EditText username_1;
    private EditText email_1;
    private EditText pass_1;
    private EditText passcon1;

    private Spinner fakultetet;
    private Spinner drejtimet;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;

    private List<String> fakultetId;
    private List<String> fakultetName;

    private List<String> drejtimetId;
    private List<String> drejtimetName;

    private ArrayAdapter<String> fakultetetAdapter;
    private ArrayAdapter<String> drejtimetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Regjistrimi");

        initView();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference();

        fakultetName = new ArrayList<>();
        fakultetId = new ArrayList<>();

        drejtimetId = new ArrayList<>();
        drejtimetName = new ArrayList<>();

        mDatabaseRef.child("Fakultetet").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    fakultetId.add(ds.child("id").getValue().toString());
                    fakultetName.add(ds.child("name").getValue().toString());
                }
                fakultetetAdapter = new ArrayAdapter<>(SignUpActivity.this, android.R.layout.simple_spinner_item, fakultetName);
                fakultetet.setAdapter(fakultetetAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fakultetet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                mDatabaseRef.child("Drejtimet").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        drejtimetId.clear();
                        drejtimetName.clear();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String fId = ds.child("f_id").getValue().toString();


                            if (fId.equals(fakultetId.get(position))) {
                                drejtimetId.add(ds.child("id").getValue().toString());
                                drejtimetName.add(ds.child("name").getValue().toString());
                            }

                        }

                        drejtimetAdapter = new ArrayAdapter<>(SignUpActivity.this, android.R.layout.simple_spinner_item, drejtimetName);
                        drejtimet.setAdapter(drejtimetAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initView() {
        email_1 = findViewById(R.id.email);
        passcon1 = findViewById(R.id.passcon);
        emri_1 = findViewById(R.id.emri);
        username_1 = findViewById(R.id.username);
        pass_1 = findViewById(R.id.pass);
        fakultetet = findViewById(R.id.fakultet);
        drejtimet = findViewById(R.id.drejtimet);
    }

    public boolean validate() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emaila = email_1.getText().toString();
        Matcher matcher = Pattern.compile(emailPattern).matcher(emaila);

        String passwordi = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
        String passwordi1 = pass_1.getText().toString();
        Matcher matcher1 = Pattern.compile(passwordi).matcher(passwordi1);
        String emri1 = emri_1.getText().toString();
        String username1 = username_1.getText().toString();
        String passwordi2 = passcon1.getText().toString();

        if (emri1.matches("")) {
            Toast.makeText(SignUpActivity.this, "Jepni emrin tuaj", Toast.LENGTH_SHORT).show();
            return false;
        } else if (username1.matches("")) {
            Toast.makeText(SignUpActivity.this, "Jepni username-in tuaj", Toast.LENGTH_SHORT).show();
            return false;
        } else if (emaila.matches("")) {
            Toast.makeText(SignUpActivity.this, "Jepni emailin tuaj", Toast.LENGTH_SHORT).show();
            return false;
          } else if (passwordi1.matches("")) {
            Toast.makeText(SignUpActivity.this, "Jepni fjalëkalimin tuaj", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!matcher1.matches()) {
            Toast.makeText(getApplicationContext(), "Jepni fjalëkalim valid me 8 karaktere(1 simbol dhe 1 UC)", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordi2.matches("")) {
            Toast.makeText(SignUpActivity.this, "Konfirmoni fjalëkalimin", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!passwordi1.matches(passwordi2)) {
            Toast.makeText(SignUpActivity.this, "Fjalëkalime të ndryshme!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void kenillogari(View view) {
        startActivity(new Intent(view.getContext(), LoginActivity.class));
    }

    public void regjistrohu(View view) {
        if (validate()) {
            final String email = email_1.getText().toString();
            String password = pass_1.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userId = task.getResult().getUser().getUid();
                                String name = emri_1.getText().toString();
                                String username = username_1.getText().toString();

                                User user = new User(name, username, email, userId, fakultetId.get(fakultetet.getSelectedItemPosition()), drejtimetId.get(drejtimet.getSelectedItemPosition()));

                                mDatabaseRef.child("Studentat").child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                });

                            } else {
                                Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}