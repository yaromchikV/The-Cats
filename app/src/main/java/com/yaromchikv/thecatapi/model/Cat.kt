package com.yaromchikv.thecatapi.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cat(
    @Json(name = "id") val id: String,
    @Json(name = "url") val imageUrl: String,
    @Json(name = "width") val width: Int,
    @Json(name = "height") val height: Int
) : Parcelable