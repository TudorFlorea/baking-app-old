package com.andev.tudor.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.andev.tudor.bakingapp.R;
import com.andev.tudor.bakingapp.StepDetailsActivity;
import com.andev.tudor.bakingapp.adapters.RecipeStepAdapter;
import com.andev.tudor.bakingapp.data.Recipe;
import com.andev.tudor.bakingapp.data.Step;
import com.andev.tudor.bakingapp.utils.Constants;
import com.andev.tudor.bakingapp.utils.InterfaceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment implements InterfaceUtils.RecipeStepListener {

    private RecipeStepAdapter mRecipeStepAdapter;
    private final String RECIPE_EXTRA = "recipe_extra";
    private final String STEP_EXTRA = "step_extra";

    @BindView(R.id.recipe_steps_rv) RecyclerView mRecipeStepsRV;

    public RecipeDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v("Fragment", "View Created");
        try {
            Recipe recipe = getArguments().getParcelable(RECIPE_EXTRA);
                mRecipeStepsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
                mRecipeStepAdapter = new RecipeStepAdapter(getActivity(), recipe.getSteps(), this);
                mRecipeStepsRV.setAdapter(mRecipeStepAdapter);

                //Intent intent = new Intent(IngredientsWidget.ACTION_INGREDIENTS_SENT);
                //intent.putExtra("ingredients_extra", recipe.getIngredients());
                //getActivity().sendBroadcast(intent);

        }
        catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    @Override
    public void onStepClick(Bundle stepsData) {
        Intent i = new Intent(getActivity(), StepDetailsActivity.class);
        if (stepsData.getParcelableArrayList(Constants.STEPS_ARRAY_LIST_TAG) == null) {
            Toast.makeText(getActivity(), "ArrayList is null", Toast.LENGTH_SHORT).show();
        } else {
            Step st =(Step) stepsData.getParcelableArrayList(Constants.STEPS_ARRAY_LIST_TAG).get(1);
            Toast.makeText(getActivity(),st.getDescription() , Toast.LENGTH_SHORT).show();
        }
        i.putExtra(Constants.STEPS_DATA_TAG, stepsData);
        getActivity().startActivity(i);
    }
}
