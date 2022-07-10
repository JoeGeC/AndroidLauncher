package com.joebarker.haelauncher

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.joebarker.haelauncher.databinding.FragmentAppLauncherBinding

class AppLauncherFragment : Fragment() {

    private lateinit var binding: FragmentAppLauncherBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAppLauncherBinding.inflate(layoutInflater)
        binding.root.adapter = AppLauncherAdapter(requireContext(), getAppsList())
        binding.root.layoutManager = GridLayoutManager(requireContext(), 4)
        return binding.root
    }

    private fun getAppsList(): List<App> {
        val result = mutableListOf<App>()
        for (appInfo in getAllApps()) {
            val app = App(
                appInfo.loadLabel(requireContext().packageManager),
                appInfo.activityInfo.packageName,
                appInfo.activityInfo.loadIcon(requireContext().packageManager)
            )
            result.add(app)
        }
        return result
    }

    private fun getAllApps(): MutableList<ResolveInfo> {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        return requireContext().packageManager.queryIntentActivities(intent, 0)
    }

}
