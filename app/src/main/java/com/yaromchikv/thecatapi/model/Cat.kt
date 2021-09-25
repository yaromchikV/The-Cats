package com.yaromchikv.thecatapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cat(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
) : Parcelable