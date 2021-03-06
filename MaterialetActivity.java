package com.example.filesharing_up.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.filesharing_up.R;
import com.example.filesharing_up.adapters.MaterialetAdapter;
import com.example.filesharing_up.models.Afatet;


public class MaterialetActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MaterialetAdapter materialetAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialet);

        Afatet afatet = (Afatet) getIntent().getSerializableExtra("MATERIAL");

        recyclerView = findViewById(R.id.recycler_view);
        materialetAdapter = new MaterialetAdapter();
        materialetAdapter.addMaterials(afatet.getMaterials());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(materialetAdapter);

    }



}
