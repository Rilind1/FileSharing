package com.example.filesharing_up.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.filesharing_up.R;
import com.example.filesharing_up.adapters.MaterialetAdapter;
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
    private DatabaseReference mDatabaseMaterialet;
    private RecyclerView recyclerView;
    private MaterialetAdapter materialetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseStudentat = mFirebaseDatabase.getReference();
        mDatabaseMaterialet = mFirebaseDatabase.getReference();

        recyclerView = findViewById(R.id.recycler_view);
        materialetAdapter = new MaterialetAdapter();
        recyclerView.setAdapter(materialetAdapter);

        final ArrayList<Material> materials = new ArrayList<>();

        mDatabaseStudentat
                .child("Studentat")
                .child(mAuth.getCurrentUser().getUid())
                .child("drejtimId")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mDatabaseMaterialet
                                .child("Materialet")
                                .child(dataSnapshot.getValue(String.class))
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                                            String id = d.child("id").getValue(String.class);
                                            String title = d.child("title").getValue(String.class);
                                            String type = d.child("type").getValue(String.class);
                                            String url = d.child("url").getValue(String.class);

                                            materialetAdapter.addMaterial(new Material(id, title, type, url));
                                        }
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

}
