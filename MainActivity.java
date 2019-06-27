package com.example.filesharing_up.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    //deklarimi i instacave
    private FirebaseAuth mAuth;//auth db
    private FirebaseDatabase mFirebaseDatabase;//realtime db
    private DatabaseReference mDatabaseStudentat;
    private DatabaseReference mDatabaseAfatet;
    private DatabaseReference mDatabaseLibra;

    private long backPressedTime = 0;
    private ArrayList<Afatet> afatets;
    private ArrayList<Material> librat;
    //inicializimi i layoutit
    LinearLayout afateLayout, libraLayout;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //merr instaca prej klases
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseStudentat = mFirebaseDatabase.getReference();
        mDatabaseAfatet = mFirebaseDatabase.getReference();
        mDatabaseLibra = mFirebaseDatabase.getReference();

        afatets = new ArrayList<>();
        librat = new ArrayList<>();

        afateLayout = findViewById(R.id.afate);
        libraLayout = findViewById(R.id.libra);

        progressDialog = new ProgressDialog(this);//dialog modal, nuk lejon veprime tjera
        progressDialog.setMessage("Ju lutem prisni");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mDatabaseStudentat
                .child("Studentat")
                .child(mAuth.getCurrentUser().getUid())
                .child("drejtimId")//ju ndihmon të zbuloni ndryshimin në të dhënat në një path të caktuar
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override //instance qe permban tdhana prej fb db lokacionit, cdo here kur lexon te dhena, merren tdhanat si Datasnapshot
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
            case R.id.fiek:
                String url = "https://fiek.uni-pr.edu/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
        }
        return false;

    }
    @Override
    public void onBackPressed() {        // to prevent irritating accidental logouts
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            startActivity(new Intent(this,RateUs.class));
            finish();
            Toast.makeText(this, "Shtypni edhe njëherë për të dalë nga aplikacioni",
                    Toast.LENGTH_SHORT).show();

        } else {    // this guy is serious
            // clean up
            super.onBackPressed();       // bye
        }
    }


}
