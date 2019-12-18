package ru.skillbranch.gameofthrones.util

import android.content.Context
import android.util.DisplayMetrics

fun convertDpToPx(dp: Int, context: Context) =
    Math.round(dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))

fun convertPxToDp(px: Int, context: Context) =
    Math.round(px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))