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
        val updateViews = { appWidgetManager.updateAppWidget(appWidgetId, views) }
        viewModel.retrieveWeatherInfoFor(cities[currentCityNumber])
        setObservers(views, updateViews)
        views.setOnClickPendingIntent(R.id.forward_button, getPendingSelfIntent(context, R.id.forward_button))
        views.setOnClickPendingIntent(R.id.back_button, getPendingSelfIntent(context, R.id.back_button))
        updateViews()
    }

    private fun setObservers(views: RemoteViews, updateViews: () -> Unit) {
        setErrorObserver(views)
        setLoadingObserver(views, updateViews)
        setWeatherInfoObserver(views)
    }

    private fun setErrorObserver(views: RemoteViews) {
        viewModel.isError.observeForever {
            if (it) {
                views.setViewVisibility(R.id.error, View.VISIBLE)
                views.setViewVisibility(R.id.main_content, View.GONE)
            }
            else {
                views.setViewVisibility(R.id.error, View.GONE)
                views.setViewVisibility(R.id.main_content, View.VISIBLE)
            }
        }
    }

    private fun setLoadingObserver(views: RemoteViews, updateViews: () -> Unit) {
        viewModel.isLoading.observeForever {
            if (it) views.setViewVisibility(R.id.loading_spinner, View.VISIBLE)
            else views.setViewVisibility(R.id.loading_spinner, View.GONE)
            views.setTextViewText(R.id.page_number, "${currentCityNumber + 1}/${cities.count()}")
            updateViews()
        }
    }

    private fun setWeatherInfoObserver(views: RemoteViews) {
        viewModel.weatherInfo.observeForever {
            views.setTextViewText(R.id.city, it.city)
            views.setTextViewText(R.id.country, it.country)
            views.setTextViewText(R.id.description, it.description)
            views.setTextViewText(R.id.temperature, "${it.temperature}°")
        }
    }

    private fun getPendingSelfIntent(context: Context, action: Int): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action.toString()
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if(intent.action == R.id.forward_button.toString())
            showNextCityInfo(context)
        else if(intent.action == R.id.back_button.toString())
            showPreviousCityInfo(context)
    }

    private fun showNextCityInfo(context: Context) {
        incrementCurrentCityNumber()
        showCityInfo(context)
    }

    private fun showCityInfo(context: Context) {
        val views = RemoteViews(context.packageName, R.layout.weather_widget)
        viewModel.retrieveWeatherInfoFor(cities[currentCityNumber])
        setObservers(views) {
            AppWidgetManager.getInstance(context).updateAppWidget(
                ComponentName(context, WeatherWidget::class.java), views
            )
        }
    }

    private fun incrementCurrentCityNumber() {
        currentCityNumber++
        if(currentCityNumber >= cities.count())
            currentCityNumber = 0
    }

    private fun showPreviousCityInfo(context: Context) {
        decrementCurrentCityNumber()
        showCityInfo(context)
    }

    private fun decrementCurrentCityNumber() {
        currentCityNumber--
        if(currentCityNumber < 0)
            currentCityNumber = cities.count() - 1
    }

    companion object{
        private val cities = listOf("beijing", "berlin", "cardiff", "edinburgh", "london", "nottingham")
        private var currentCityNumber = 0
    }

}
