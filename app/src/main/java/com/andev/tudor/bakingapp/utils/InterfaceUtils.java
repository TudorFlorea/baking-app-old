package com.andev.tudor.bakingapp.utils;

import com.andev.tudor.bakingapp.data.Step;

public class InterfaceUtils {
    public interface RecipeStepListener {
        public void onStepClick (Step step);
    }
}
