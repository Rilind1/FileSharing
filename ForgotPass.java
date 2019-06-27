package com.example.filesharing_up.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.filesharing_up.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass extends AppCompatActivity {
    EditText email;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_forgot_password);

        email = findViewById(R.id.email);
    }

    public void forgotPass(View view) {
        String emailTxt = email.getText().toString();
        if (!emailTxt.isEmpty())
            FirebaseAuth.getInstance().sendPasswordResetEmail(emailTxt)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPass.this, "Email për ndryshim të passwordit u dërgua", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ForgotPass.this, "Kjo email nuk ekziston", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }
}
