package com.example.nekosapp.utils

import android.util.TypedValue
import android.widget.ImageView

object ImageUtils {

    fun changeImageViewSize(imageView: ImageView, widthInDp: Float, heightInDp: Float) {
        // Convert dp to pixels
        val widthInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, widthInDp, imageView.resources.displayMetrics
        ).toInt()

        val heightInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, heightInDp, imageView.resources.displayMetrics
        ).toInt()

        // Set the width and height in layout params
        val layoutParams = imageView.layoutParams
        layoutParams.width = widthInPx
        layoutParams.height = heightInPx
        imageView.layoutParams = layoutParams
    }
}