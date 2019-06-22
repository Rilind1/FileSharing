package com.example.filesharing_up.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.filesharing_up.R;
import com.example.filesharing_up.models.Afatet;
import com.example.filesharing_up.models.Material;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseStudentat;
    private DatabaseReference mDatabaseAfatet;
    private DatabaseReference mDatabaseLibra;

    private ArrayList<Afatet> afatets;
    private ArrayList<Material> librat;

    LinearLayout afateLayout, libraLayout;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseStudentat = mFirebaseDatabase.getReference();
        mDatabaseAfatet = mFirebaseDatabase.getReference();
        mDatabaseLibra = mFirebaseDatabase.getReference();

        afatets = new ArrayList<>();
        librat = new ArrayList<>();

        afateLayout = findViewById(R.id.afate);
        libraLayout = findViewById(R.id.libra);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Ju lutem beni sabër...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mDatabaseStudentat
                .child("Studentat")
                .child(mAuth.getCurrentUser().getUid())
                .child("drejtimId")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String drejtimId = dataSnapshot.getValue(String.class);

                        mDatabaseAfatet
                                .child("Afatet")
                                .child(drejtimId)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot afatDataSnapshot : dataSnapshot.getChildren()) {

                                            Afatet afat = new Afatet();
                                            afat.setId(afatDataSnapshot.child("afatId").getValue(String.class));
                                            afat.setTitle(afatDataSnapshot.child("afatTitle").getValue(String.class));
                                            ArrayList<Material> materials = new ArrayList<>();

                                            for (DataSnapshot materialDatasnapshot : afatDataSnapshot.child("Materialet").getChildren()) {
                                                String id = materialDatasnapshot.child("id").getValue(String.class);
                                                String title = materialDatasnapshot.child("title").getValue(String.class);
                                                String type = materialDatasnapshot.child("type").getValue(String.class);
                                                String url = materialDatasnapshot.child("url").getValue(String.class);
                                                materials.add(new Material(id, title, type, url));
                                            }

                                            afat.setMaterials(materials);
                                            afatets.add(afat);

                                            afateLayout.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(MainActivity.this, AfateActivity.class);
                                                    intent.putExtra("AFATE", afatets);
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                        mDatabaseLibra
                                .child("Libra")
                                .child(drejtimId)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot libraDataSnapshot : dataSnapshot.getChildren()) {
                                            String id = libraDataSnapshot.child("id").getValue(String.class);
                                            String title = libraDataSnapshot.child("title").getValue(String.class);
                                            String type = libraDataSnapshot.child("type").getValue(String.class);
                                            String url = libraDataSnapshot.child("url").getValue(String.class);
                                            librat.add(new Material(id, title, type, url));
                                        }

                                        libraLayout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(MainActivity.this, LibraActivity.class);
                                                intent.putExtra("LIBRA", librat);
                                                startActivity(intent);
                                            }
                                        });

                                        progressDialog.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                ActivityCompat.finishAffinity(this);
                return true;
        }
        return false;
    }

}
