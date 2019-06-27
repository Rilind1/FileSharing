package com.example.filesharing_up.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;

import com.example.filesharing_up.R;

import hotchemi.android.rate.AppRate;

public class RateUs extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);
        AppRate.with(this)
                .setInstallDays(0)
                .setLaunchTimes(1)
                .setRemindInterval(0)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);
        AppRate.with(this).showRateDialog(this);
    }
    }
