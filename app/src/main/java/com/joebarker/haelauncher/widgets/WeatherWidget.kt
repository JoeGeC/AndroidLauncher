package com.joebarker.haelauncher.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.view.View
import android.widget.RemoteViews
import androidx.lifecycle.ProcessLifecycleOwner
import com.joebarker.config.Config
import com.joebarker.domain.usecases.WeatherUseCaseImpl
import com.joebarker.haelauncher.R
import com.joebarker.haelauncher.viewmodels.WeatherWidgetViewModel

class WeatherWidget : AppWidgetProvider() {

    private val viewModel = WeatherWidgetViewModel(Config().weatherUseCase)

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds)
            updateAppWidget(context, appWidgetManager, appWidgetId)
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.weather_widget)
        views.setTextViewText(R.id.city, "Hello")
        viewModel.isLoading.observe(ProcessLifecycleOwner.get()){
            if(it) views.setViewVisibility(R.id.loading_spinner, View.VISIBLE)
            else views.setViewVisibility(R.id.loading_spinner, View.GONE)
        }
        viewModel.retrieveWeatherInfoFor("beijing")
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

}
