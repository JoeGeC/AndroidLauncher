package com.joebarker.haelauncher

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val icon: ImageView = view.findViewById(R.id.app_icon)
    val label: TextView = view.findViewById(R.id.app_label)
}