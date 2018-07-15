package com.andev.tudor.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.andev.tudor.bakingapp.data.Step;
import com.andev.tudor.bakingapp.fragments.RecipeDetailsFragment;
import com.andev.tudor.bakingapp.fragments.StepDetailsFragment;
import com.andev.tudor.bakingapp.utils.Constants;

import java.util.ArrayList;

public class StepDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Intent i = getIntent();

        if (i.hasExtra(Constants.STEPS_DATA_TAG)) {
            try {



//                if (i.getExtras().getBundle(Constants.STEPS_DATA_TAG) == null) {
//                    Toast.makeText(this, "NO EXTRA", Toast.LENGTH_SHORT).show();
//                } else {
//                    ArrayList<Step> steps = i.getExtras().getBundle(Constants.STEPS_DATA_TAG).getParcelableArrayList(Constants.STEPS_ARRAY_LIST_TAG);
//                    Step step = steps.get(1);
//                    Toast.makeText(this, step.getShortDescription(), Toast.LENGTH_SHORT).show();
//                }

                Bundle stepsData = i.getExtras().getBundle(Constants.STEPS_DATA_TAG);

                StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
                stepDetailsFragment.setArguments(stepsData);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.add(R.id.step_details_fl, stepDetailsFragment).commit();



            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }

        }

    }
}
