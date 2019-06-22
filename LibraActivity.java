package com.example.filesharing_up.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.filesharing_up.R;
import com.example.filesharing_up.adapters.MaterialetAdapter;
import com.example.filesharing_up.models.Material;

import java.util.ArrayList;

public class LibraActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MaterialetAdapter materialetAdapter;
    private ArrayList<Material> librat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libra);

        librat = (ArrayList<Material>) getIntent().getSerializableExtra("LIBRA");

        recyclerView = findViewById(R.id.libra_recycler_view);
        materialetAdapter = new MaterialetAdapter();
        materialetAdapter.addMaterials(librat);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(materialetAdapter);

    }
}
