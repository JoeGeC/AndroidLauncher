package com.joebarker.haelauncher.viewmodels

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joebarker.haelauncher.ui.applauncher.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppLauncherViewModel(private val packageManager: PackageManager): ViewModel() {

    private val appList: MutableLiveData<List<App>> by lazy {
        MutableLiveData<List<App>>().also {
            loadAppList()
        }
    }

    fun getAppList(): LiveData<List<App>> = appList

    private fun loadAppList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = mutableListOf<App>()
            for (appInfo in getAllApps()) {
                val app = App(
                    appInfo.loadLabel(packageManager),
                    appInfo.activityInfo.packageName,
                    appInfo.loadIcon(packageManager)
                )
                result.add(app)
            }
            appList.postValue(result)
        }
    }

    private fun getAllApps(): MutableList<ResolveInfo> {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        return packageManager.queryIntentActivities(intent, 0)
    }

}