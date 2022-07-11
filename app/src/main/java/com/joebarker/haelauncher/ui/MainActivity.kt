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
    private lateinit var appWidgetHost: AppWidgetHost
    private lateinit var appWidgetManager: AppWidgetManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.appLauncherIcon.setOnClickListener { openAppLauncherFragment() }
    }

    override fun onStart() {
        super.onStart()
        appWidgetHost = AppWidgetHost(this, 1)
        appWidgetManager = AppWidgetManager.getInstance(this)
        appWidgetHost.startListening()
        createWidget()
    }

    override fun onStop() {
        super.onStop()
        appWidgetHost.stopListening()
    }

    private fun openAppLauncherFragment() {
        AppLauncherFragment().show(supportFragmentManager, AppLauncherFragment.TAG)
    }

    private fun createWidget() {
        val appWidgetId = appWidgetHost.allocateAppWidgetId()
        val widgetInfo = getWidgetInfo(appWidgetManager.installedProviders)
        val widgetView = appWidgetHost.createView(this, appWidgetId, widgetInfo)
        widgetView?.setAppWidget(appWidgetId, widgetInfo)
        binding.widgetContainer.addView(widgetView)
    }

    private fun getWidgetInfo(appWidgets: MutableList<AppWidgetProviderInfo>): AppWidgetProviderInfo? {
        appWidgets.forEach { widget ->
            if (widget.provider.packageName == "com.joebarker.haelauncher"
                && widget.provider.className == "com.joebarker.haelauncher.widgets.WeatherWidget"
            ) {
                return widget
            }
        }
        return null
    }

}