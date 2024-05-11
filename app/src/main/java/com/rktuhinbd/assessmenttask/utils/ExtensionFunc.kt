package com.rktuhinbd.assessmenttask.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

object ExtensionFunc {

    fun ImageView.loadImage(url: String, placeholderResId: Int) {
        Glide.with(context)
            .load(url)
            .placeholder(placeholderResId)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}