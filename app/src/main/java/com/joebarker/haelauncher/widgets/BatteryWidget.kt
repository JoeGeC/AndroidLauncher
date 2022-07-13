package com.joebarker.haelauncher.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.os.BatteryManager
import android.widget.RemoteViews
import com.joebarker.haelauncher.R


class BatteryWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds)
            updateAppWidget(context, appWidgetManager, appWidgetId)
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.battery_widget)
        val batteryManager = context.getSystemService(BATTERY_SERVICE) as BatteryManager
        val batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        views.setTextViewText(R.id.battery_progress_label, "$batteryLevel%")
        views.setProgressBar(R.id.battery_progress_bar, 100, batteryLevel, false)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

}
