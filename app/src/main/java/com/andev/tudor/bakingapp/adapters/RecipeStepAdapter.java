package com.andev.tudor.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andev.tudor.bakingapp.R;
import com.andev.tudor.bakingapp.data.Step;
import com.andev.tudor.bakingapp.utils.Constants;
import com.andev.tudor.bakingapp.utils.InterfaceUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.andev.tudor.bakingapp.utils.Constants.STEPS_ARRAY_LIST_TAG;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.StepViewHolder> {

    private Context mContext;
    private ArrayList<Step> mSteps;
    private InterfaceUtils.RecipeStepListener mListener;

    public RecipeStepAdapter(Context context, ArrayList<Step> steps, InterfaceUtils.RecipeStepListener listener) {
        mContext = context;
        mSteps = steps;
        mListener = listener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_step_item, parent, false);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.setData(mSteps.get(position));
    }

    @Override
    public int getItemCount() {
        return mSteps != null ? mSteps.size() : 0;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_step_tv) TextView mRecipeStepTV;
        private Step mStep;

        public StepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Step step) {
            mStep = step;
            mRecipeStepTV.setText(step.getShortDescription());
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {

                Bundle stepsData = new Bundle();

                stepsData.putParcelableArrayList(Constants.STEPS_ARRAY_LIST_TAG, mSteps);
                stepsData.putInt(Constants.CURRENT_STEP_INDEX_TAG, mSteps.indexOf(mStep));

                mListener.onStepClick(stepsData);
            }

            Toast.makeText(mContext, "test", Toast.LENGTH_SHORT).show();
        }
    }

}
