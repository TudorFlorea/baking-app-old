package com.andev.tudor.bakingapp.data;

import com.andev.tudor.bakingapp.utils.JsonUtils;
import com.andev.tudor.bakingapp.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeFactory {

    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_INGREDIENTS = "ingredients";
    private static final String RECIPE_STEPS = "steps";
    private static final String RECIPE_SERVINGS = "servings";
    private static final String RECIPE_IMAGE = "image";
    private static final String INGREDIENT_QUANTITY = "quantity";
    private static final String INGREDIENT_MEASURE = "measure";
    private static final String INGREDIENT_NAME = "ingredient";
    private static final String STEP_ID = "id";
    private static final String STEP_SHORT_DESCRIPTION = "shortDescription";
    private static final String STEP_DESCRIPTION = "description";
    private static final String STEP_VIDEO_URL = "videoURL";
    private static final String STEP_THUMBNAIL_URL = "thumbnailURL";

    public static ArrayList<Recipe> getAllRecipes() {

        JSONArray recipes = JsonUtils.jsonArrayFromString(NetworkUtils.getRawJsonData());

        return extractRecipesFromJsonArray(recipes);
    }

    private static ArrayList<Recipe> extractRecipesFromJsonArray(JSONArray recipesArray) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        JSONObject recipeObj;

        try {
            for (int i = 0; i < recipesArray.length(); i++) {
                recipeObj = recipesArray.optJSONObject(i);
                recipes.add(extractRecipeFromJsonObject(recipeObj));
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            return null;
        }

        return recipes;
    }

    private static Recipe extractRecipeFromJsonObject(JSONObject recipeJson) {

        int id = recipeJson.optInt(RECIPE_ID);
        String name = recipeJson.optString(RECIPE_NAME, "");
        ArrayList<Ingredient> ingredients = extractIngredientsFromJsonArray(recipeJson.optJSONArray(RECIPE_INGREDIENTS));
        ArrayList<Step> steps = extractStepsFromJsonArray(recipeJson.optJSONArray(RECIPE_STEPS));
        int servings = recipeJson.optInt(RECIPE_SERVINGS, 0);
        String image = recipeJson.optString(RECIPE_IMAGE, "");

        return new Recipe(id, name, ingredients, steps, servings, image);

    }

    private static Ingredient extractIngredientFromJsonObject(JSONObject ingredientJson) {

        double quantity = ingredientJson.optDouble(INGREDIENT_QUANTITY, 0);
        String measure = ingredientJson.optString(INGREDIENT_MEASURE, "");
        String name = ingredientJson.optString(INGREDIENT_NAME, "");

        return new Ingredient(quantity, measure, name);

    }

    private static ArrayList<Ingredient> extractIngredientsFromJsonArray(JSONArray ingredientsArray) {

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        JSONObject ingredientJsonObj;

        for (int i = 0; i < ingredientsArray.length(); i++) {
            ingredientJsonObj = ingredientsArray.optJSONObject(i);
            ingredients.add(extractIngredientFromJsonObject(ingredientJsonObj));
        }

        return ingredients;
    }

    private static Step extractStepFromJsonObject(JSONObject stepJson) {

        int id = stepJson.optInt(STEP_ID);
        String shortDescription = stepJson.optString(STEP_SHORT_DESCRIPTION, "");
        String description = stepJson.optString(STEP_DESCRIPTION, "");
        String videoUrl = stepJson.optString(STEP_VIDEO_URL, "");
        String thumbnailUrl = stepJson.optString(STEP_THUMBNAIL_URL, "");

        return new Step(id, shortDescription, description, videoUrl, thumbnailUrl);

    }

    private static ArrayList<Step> extractStepsFromJsonArray(JSONArray stepsArray) {

        ArrayList<Step> steps = new ArrayList<>();
        JSONObject stepJson;

        for (int i = 0; i < stepsArray.length(); i++) {
            stepJson = stepsArray.optJSONObject(i);
            steps.add(extractStepFromJsonObject(stepJson));
        }

        return steps;

    }

}
