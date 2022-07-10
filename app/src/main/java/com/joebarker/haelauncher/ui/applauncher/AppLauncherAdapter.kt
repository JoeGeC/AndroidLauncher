package com.joebarker.haelauncher.ui.applauncher

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.joebarker.haelauncher.R


class AppLauncherAdapter(private val context: Context) : RecyclerView.Adapter<AppViewHolder>() {

    private var appList: List<App> = listOf()

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
        if(app.packageName == null) {
            Toast.makeText(context, "Sorry! Something went wrong.", Toast.LENGTH_SHORT)
            return
        }
        val intent = context.packageManager.getLaunchIntentForPackage(app.packageName)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int = appList.size

    fun updateItems(newAppList: List<App>?) {
        if (newAppList == null) return
        appList = newAppList
        notifyDataSetChanged()
    }

}
