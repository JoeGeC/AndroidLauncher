package com.joebarker.haelauncher

import android.graphics.drawable.Drawable

data class App(
    val label: CharSequence,
    val packageName: String,
    val icon: Drawable
) {
}