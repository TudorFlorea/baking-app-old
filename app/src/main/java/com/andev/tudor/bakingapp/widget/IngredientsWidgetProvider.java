package com.andev.tudor.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.andev.tudor.bakingapp.R;

public class IngredientsWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {

            RemoteViews views = new RemoteViews(
                    context.getPackageName(),
                    R.layout.ingredients_widget
            );

            Intent intent = new Intent(context, IngredientsRemoteViewsService.class);
            views.setRemoteAdapter(R.id.ingredients_list_widget, intent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context,
                IngredientsWidgetProvider.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context,
                    IngredientsWidgetProvider.class);
            manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(componentName),
                    R.id.ingredients_list_widget);
        }

        super.onReceive(context, intent);
    }
}
