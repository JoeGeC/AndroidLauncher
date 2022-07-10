package com.joebarker.haelauncher.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joebarker.haelauncher.ui.applauncher.AppLauncherFragment
import com.joebarker.haelauncher.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.appLauncherIcon.setOnClickListener { openAppLauncherFragment() }
        return binding.root
    }

    private fun openAppLauncherFragment() {
        AppLauncherFragment().show(parentFragmentManager, AppLauncherFragment.TAG)
    }
}