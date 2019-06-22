package com.example.filesharing_up.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.filesharing_up.R;
import com.example.filesharing_up.adapters.AfatetAdapter;
import com.example.filesharing_up.models.Afatet;

import java.util.ArrayList;

public class AfateActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AfatetAdapter afatetAdapter;
    private ArrayList<Afatet> afatets;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afate);

        recyclerView = findViewById(R.id.afate_recycler_view);

        afatets = (ArrayList<Afatet>) getIntent().getSerializableExtra("AFATE");

        afatetAdapter = new AfatetAdapter();
        recyclerView.setAdapter(afatetAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        afatetAdapter.addAfats(afatets);

    }
}
