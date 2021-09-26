package com.yaromchikv.thecatapi.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cat(
    val id: String,
    @Json(name = "url") val imageUrl: String,
    val width: Int,
    val height: Int
) : Parcelable