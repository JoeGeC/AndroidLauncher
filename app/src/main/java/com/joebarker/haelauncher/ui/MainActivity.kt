package com.joebarker.haelauncher.ui

import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.joebarker.haelauncher.databinding.ActivityMainBinding
import com.joebarker.haelauncher.ui.applauncher.AppLauncherFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var widgetHost: AppWidgetHost
    private lateinit var widgetManager: AppWidgetManager
    private val weatherWidget = "WeatherWidget"
    private val batteryWidget = "BatteryWidget"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.appLauncherIcon.setOnClickListener { openAppLauncherFragment() }
        widgetHost = AppWidgetHost(this, 1)
        widgetManager = AppWidgetManager.getInstance(this)
        createWidget(weatherWidget)
        createWidget(batteryWidget)
    }

    override fun onStart() {
        super.onStart()
        widgetHost.startListening()
    }

    override fun onStop() {
        super.onStop()
        widgetHost.stopListening()
    }

    private fun openAppLauncherFragment() {
        AppLauncherFragment().show(supportFragmentManager, AppLauncherFragment.TAG)
    }

    private fun createWidget(widgetClassName: String) {
        val appWidgetId = widgetHost.allocateAppWidgetId()
        val widgetInfo = getWidgetInfo(widgetManager.installedProviders, widgetClassName)
        val widgetView = widgetHost.createView(this, appWidgetId, widgetInfo)
        widgetView?.setAppWidget(appWidgetId, widgetInfo)
        binding.widgetContainer.addView(widgetView)
        requestWidgetPermission(appWidgetId, widgetInfo)
    }

    private fun requestWidgetPermission(appWidgetId: Int, widgetInfo: AppWidgetProviderInfo?) {
        val allowed = widgetManager.bindAppWidgetIdIfAllowed(appWidgetId, widgetInfo?.provider)
        if (allowed) return
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_BIND)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_PROVIDER, widgetInfo?.provider)
        startActivityForResult(intent, 0)
    }

    private fun getWidgetInfo(appWidgets: MutableList<AppWidgetProviderInfo>, widgetClassName: String): AppWidgetProviderInfo? {
        appWidgets.forEach { widget ->
            if (widget.provider.packageName == "com.joebarker.haelauncher"
                && widget.provider.className == "com.joebarker.haelauncher.widgets.$widgetClassName"
            ) {
                return widget
            }
        }
        return null
    }

}