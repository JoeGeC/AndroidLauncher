package com.joebarker.haelauncher.ui.applauncher

import android.graphics.drawable.Drawable

data class App(
    val label: CharSequence?,
    val packageName: String?,
    val icon: Drawable?
)