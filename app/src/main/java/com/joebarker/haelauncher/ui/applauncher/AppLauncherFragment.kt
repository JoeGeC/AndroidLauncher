package com.joebarker.haelauncher.ui.applauncher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joebarker.haelauncher.databinding.FragmentAppLauncherBinding
import com.joebarker.haelauncher.viewmodels.AppLauncherViewModel

class AppLauncherFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "AppLauncherFragment"
    }

    private lateinit var binding: FragmentAppLauncherBinding
    private lateinit var viewModel: AppLauncherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAppLauncherBinding.inflate(layoutInflater)
        viewModel = AppLauncherViewModel(requireContext().packageManager)
        val adapter = AppLauncherAdapter(requireContext())
        binding.appList.adapter = adapter
        binding.appList.layoutManager = GridLayoutManager(requireContext(), 4)
        viewModel.getAppList().observe(this) {
            adapter.updateItems(it)
            binding.loadingSpinner.visibility = View.GONE
        }
        return binding.root
    }

}
