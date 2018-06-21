package com.andev.tudor.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.andev.tudor.bakingapp.fragments.RecipeDetailsFragment;
import com.andev.tudor.bakingapp.fragments.StepDetailsFragment;

public class StepDetailsActivity extends AppCompatActivity {

    private final String STEP_EXTRA = "step_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Intent i = getIntent();

        if (i.hasExtra(STEP_EXTRA)) {
            try {
                Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show();
                StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
                stepDetailsFragment.setArguments(i.getExtras());
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.add(R.id.step_details_fl, stepDetailsFragment).commit();

            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }

        }

    }
}
