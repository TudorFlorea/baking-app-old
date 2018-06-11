package com.andev.tudor.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andev.tudor.bakingapp.R;
import com.andev.tudor.bakingapp.data.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.StepViewHolder> {

    private Context mContext;
    private ArrayList<Step> mSteps;

    public RecipeStepAdapter(Context context, ArrayList<Step> steps) {
        mContext = context;
        mSteps = steps;
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

    public class StepViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_step_tv) TextView mRecipeStepTV;
        private Step mStep;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Step step) {
            mStep = step;
            mRecipeStepTV.setText(step.getShortDescription());
        }
    }

}
