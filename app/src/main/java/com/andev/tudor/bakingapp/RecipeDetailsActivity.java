package com.andev.tudor.bakingapp;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.andev.tudor.bakingapp.adapters.RecipeStepAdapter;
import com.andev.tudor.bakingapp.data.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    private RecipeStepAdapter mRecipeStepAdapter;
    private final String RECIPE_EXTRA = "recipe_extra";

    @BindView(R.id.recipe_steps_rv) RecyclerView mRecipeStepsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);

        Intent i = getIntent();

        if (i.hasExtra(RECIPE_EXTRA)) {
            try {
                Recipe recipe = i.getExtras().getParcelable(RECIPE_EXTRA);
                mRecipeStepsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
                mRecipeStepAdapter = new RecipeStepAdapter(this, recipe.getSteps());
                mRecipeStepsRV.setAdapter(mRecipeStepAdapter);
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }

        }





    }
}
