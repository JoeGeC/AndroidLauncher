package com.joebarker.haelauncher

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class AppLauncherAdapter(private val context: Context, private val appList: List<App>) : RecyclerView.Adapter<AppViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_app_icon, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = appList[position]
        holder.icon.setImageDrawable(app.icon)
        holder.label.text = app.label
        holder.view.setOnClickListener { open(app) }
    }

    private fun open(app: App) {
        val intent = context.packageManager.getLaunchIntentForPackage(app.packageName)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int = appList.size

}
