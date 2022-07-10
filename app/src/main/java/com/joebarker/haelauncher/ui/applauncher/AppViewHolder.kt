package com.joebarker.haelauncher.ui.applauncher

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joebarker.haelauncher.R

class AppViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val icon: ImageView = view.findViewById(R.id.app_icon)
    val label: TextView = view.findViewById(R.id.app_label)
}