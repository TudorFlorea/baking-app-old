package com.andev.tudor.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.andev.tudor.bakingapp.R;
import com.andev.tudor.bakingapp.data.Ingredient;
import com.andev.tudor.bakingapp.data.Recipe;
import com.andev.tudor.bakingapp.data.RecipeFactory;
import com.andev.tudor.bakingapp.utils.Constants;
import com.andev.tudor.bakingapp.utils.SharedPreferencesUtils;

import java.util.ArrayList;

public class IngredientsWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private ArrayList<Ingredient> mIngredients;

    public IngredientsWidgetRemoteViewsFactory (Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        ArrayList<Recipe> recipes = RecipeFactory.getAllRecipes();
        int recipeIndex = SharedPreferencesUtils.getIntFromSharedPrefsForKey(Constants.SHARED_PREF_CURRENT_RECIPE_KEY, mContext);
        Recipe currentRecipe = recipes.get(recipeIndex);
        mIngredients = currentRecipe.getIngredients();
    }

    @Override
    public void onDestroy() {
        if (mIngredients != null) {
            mIngredients = null;
        }
    }

    @Override
    public int getCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (mIngredients == null) return null;

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.single_ingredient_widget);

        remoteViews.setTextViewText(R.id.ingredient_name_widget, mIngredients.get(position).getName());

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
