package com.andev.tudor.bakingapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andev.tudor.bakingapp.adapters.RecipeCardAdapter;
import com.andev.tudor.bakingapp.data.Recipe;
import com.andev.tudor.bakingapp.data.RecipeFactory;
import com.andev.tudor.bakingapp.fragments.RecipeCardFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>{

    private RecipeCardAdapter mRecipeCardAdapter;

    @BindView(R.id.recipe_cards_rv) RecyclerView mRecipeCardsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mRecipeCardsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        mRecipeCardAdapter = new RecipeCardAdapter(this, null);
        mRecipeCardsRV.setAdapter(mRecipeCardAdapter);

        getSupportLoaderManager().restartLoader(100, null, this);


    }


    @NonNull
    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<Recipe>>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Nullable
            @Override
            public ArrayList<Recipe> loadInBackground() {
                return RecipeFactory.getAllRecipes();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> recipes) {

        if(recipes != null) {
            mRecipeCardAdapter =  new RecipeCardAdapter(MainActivity.this, recipes);
            mRecipeCardsRV.setAdapter(mRecipeCardAdapter);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Recipe>> loader) {

    }
}
