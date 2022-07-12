package com.joebarker.haelauncher.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
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
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
        setWeatherInfoObserver(views) { appWidgetManager.updateAppWidget(appWidgetId, views) }
        viewModel.retrieveWeatherInfoFor(cities[currentCityNumber])
        views.setOnClickPendingIntent(R.id.page_number, getPendingSelfIntent(context))
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun setWeatherInfoObserver(views: RemoteViews, update: () -> Unit) {
        viewModel.weatherInfo.observeForever {
            views.setTextViewText(R.id.city, it.city)
            views.setTextViewText(R.id.country, it.country)
            views.setTextViewText(R.id.description, it.description)
            views.setTextViewText(R.id.temperature, it.temperature.toString())
            views.setTextViewText(R.id.page_number, "${currentCityNumber + 1}/${cities.count()}")
            update()
        }
    }

    private fun getPendingSelfIntent(context: Context): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = nextCityAction
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if(intent.action == nextCityAction) showNextCity(context)
    }

    private fun showNextCity(context: Context) {
        incrementCurrentCity()
        viewModel.retrieveWeatherInfoFor(cities[currentCityNumber])
        val views = RemoteViews(context.packageName, R.layout.weather_widget)
        setWeatherInfoObserver(views) {
            AppWidgetManager.getInstance(context).updateAppWidget(
                ComponentName(context, WeatherWidget::class.java), views)
        }
    }

    private fun incrementCurrentCity() {
        currentCityNumber++
        if(currentCityNumber >= cities.size)
            currentCityNumber = 0
    }

    companion object{
        private val cities = listOf("beijing", "berlin", "cardiff", "edinburgh", "london", "nottingham")
        private const val nextCityAction = "NextCity"
        private var currentCityNumber = 0
    }

}
