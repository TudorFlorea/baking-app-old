package com.andev.tudor.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.RenderProcessGoneDetail;
import android.widget.TextView;
import android.widget.Toast;

import com.andev.tudor.bakingapp.R;
import com.andev.tudor.bakingapp.RecipeDetailsActivity;
import com.andev.tudor.bakingapp.data.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.CardViewHolder> {

    private Context mContext;
    private ArrayList<Recipe> mRecipes;


    public RecipeCardAdapter(Context context, ArrayList<Recipe> recipes) {
        mContext = context;
        mRecipes = recipes;
    }


    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_card_item, parent, false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.setData(mRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipes != null ? mRecipes.size() : 0;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.recipe_name_tv) TextView mRecipeName;
        private Recipe mRecipe;

        public CardViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Recipe recipe) {
            mRecipe = recipe;
            mRecipeName.setText(recipe.getName());
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(mContext, RecipeDetailsActivity.class);
            i.putExtra("recipe_extra", mRecipe);
            mContext.startActivity(i);
            Toast.makeText(mContext, mRecipe.getName(), Toast.LENGTH_LONG).show();
        }
    }
}
