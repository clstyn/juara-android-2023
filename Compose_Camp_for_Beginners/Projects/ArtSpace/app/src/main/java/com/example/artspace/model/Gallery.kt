package com.example.artspace.model

import androidx.annotation.DrawableRes

data class Gallery(
    val id: Int,
    val title: String,
    val creator: String,
    val added: String,
    @DrawableRes val imgRes: Int
)
