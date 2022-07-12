package com.joebarker.haelauncher.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.view.View
import android.widget.RemoteViews
import androidx.lifecycle.ProcessLifecycleOwner
import com.joebarker.config.Config
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
        viewModel.isLoading.observeForever {
            if(it) views.setViewVisibility(R.id.loading_spinner, View.VISIBLE)
            else views.setViewVisibility(R.id.loading_spinner, View.GONE)
        }
        viewModel.weatherInfo.observeForever {
            views.setTextViewText(R.id.city, it.city)
            views.setTextViewText(R.id.country, it.country)
            views.setTextViewText(R.id.description, it.description)
            views.setTextViewText(R.id.temperature, it.temperature.toString())
        }
        viewModel.retrieveWeatherInfoFor("beijing")
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

}
