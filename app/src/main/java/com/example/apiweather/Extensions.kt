package com.example.apiweather

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String) {
    Picasso.get().load(url)
        .error(R.drawable.ic_launcher_foreground)
        .resize(MainActivity.IMAGE_WIDTH, MainActivity.IMAGE_HEIGHT)
        .centerCrop()
        .into(this)
}