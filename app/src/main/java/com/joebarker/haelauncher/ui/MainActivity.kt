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

    val APPWIDGET_HOST_ID = 2048
    val REQUEST_PICK_APPWIDGET = 0
    val REQUEST_CREATE_APPWIDGET = 5

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
            if (widget.provider.packageName == "com.google.android.deskclock"
                && widget.provider.className == "com.android.alarmclock.AnalogAppWidgetProvider"
            ) {
                return widget
            }
        }
        val d = AppWidgetProviderInfo()
        return null
    }

    fun selectWidget() {
        val appWidgetId = appWidgetHost.allocateAppWidgetId()
        val pickIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_PICK)
        pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        addEmptyData(pickIntent)
        startActivityForResult(pickIntent, REQUEST_PICK_APPWIDGET)
    }

    // For some reason you have to add this empty data, else it won't work
    fun addEmptyData(pickIntent: Intent) {
        val customInfo = ArrayList<AppWidgetProviderInfo>()
        pickIntent.putParcelableArrayListExtra(
            AppWidgetManager.EXTRA_CUSTOM_INFO, customInfo
        )
        val customExtras = ArrayList<Bundle>()
        pickIntent.putParcelableArrayListExtra(
            AppWidgetManager.EXTRA_CUSTOM_EXTRAS, customExtras
        )
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_APPWIDGET) {
                configureWidget(data!!)
            } else if (requestCode == REQUEST_CREATE_APPWIDGET) {
                createWidget(data!!)
            }
        } else if (resultCode == RESULT_CANCELED && data != null) {
            val appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
            if (appWidgetId != -1) {
                appWidgetHost.deleteAppWidgetId(appWidgetId)
            }
        }
    }

    private fun configureWidget(data: Intent) {
        val extras = data.extras
        val appWidgetId = extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
        val appWidgetInfo: AppWidgetProviderInfo = appWidgetManager.getAppWidgetInfo(appWidgetId)
        if (appWidgetInfo.configure != null) {
            val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE)
            intent.component = appWidgetInfo.configure
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            createWidget(intent)
        } else {
            createWidget(data)
        }
    }

    fun createWidget(data: Intent) {
        val extras = data.extras
        val appWidgetId = extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
        val appWidgetInfo = appWidgetManager.getAppWidgetInfo(appWidgetId)
        val hostView = appWidgetHost.createView(this, appWidgetId, appWidgetInfo)
        hostView.setAppWidget(appWidgetId, appWidgetInfo)
        binding.widgetContainer.addView(hostView)
    }

}