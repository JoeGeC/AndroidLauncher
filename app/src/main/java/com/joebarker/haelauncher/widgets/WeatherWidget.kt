package com.joebarker.haelauncher.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.joebarker.haelauncher.R

class WeatherWidget : AppWidgetProvider() {

//    val viewHolder = WeatherWidgetViewHolder()

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds)
            updateAppWidget(context, appWidgetManager, appWidgetId)
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.weather_widget)
//        viewHolder.isLoading.observe(ProcessLifecycleOwner.get()){
//            if(it) views.setViewVisibility(R.id.loading_spinner, View.VISIBLE)
//            else views.setViewVisibility(R.id.loading_spinner, View.GONE)
//        }
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

}
