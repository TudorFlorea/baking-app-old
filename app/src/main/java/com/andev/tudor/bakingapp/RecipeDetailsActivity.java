package com.andev.tudor.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.andev.tudor.bakingapp.adapters.RecipeStepAdapter;
import com.andev.tudor.bakingapp.data.Recipe;
import com.andev.tudor.bakingapp.data.Step;
import com.andev.tudor.bakingapp.fragments.RecipeDetailsFragment;
import com.andev.tudor.bakingapp.fragments.StepDetailsFragment;
import com.andev.tudor.bakingapp.utils.InterfaceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements InterfaceUtils.RecipeStepListener {

    private RecipeStepAdapter mRecipeStepAdapter;
    private final String RECIPE_EXTRA = "recipe_extra";
    private final String STEP_TAG = "step";

    @BindView(R.id.recipe_details_fl) FrameLayout mFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);

        Intent i = getIntent();

        if (i.hasExtra(RECIPE_EXTRA)) {
            Log.v("DetailsActivity", "Intent has extra");
            try {

                RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();
                detailsFragment.setArguments(i.getExtras());
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.add(R.id.recipe_details_fl, detailsFragment).commit();

            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }

        }

    }

    @Override
    public void onStepClick(Step step) {

        Bundle args = new Bundle();
        args.putParcelable(STEP_TAG, step);

        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        stepDetailsFragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.recipe_details_fl, stepDetailsFragment).commit();

    }
}
