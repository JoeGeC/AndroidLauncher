package com.joebarker.haelauncher

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val icon = view.findViewById<ImageView>(R.id.app_icon)
    val label = view.findViewById<TextView>(R.id.app_label)
}