package com.example.testtaskeffectivemobile.data.model

import com.google.gson.annotations.SerializedName

data class Offer(
    @SerializedName("id") val imageId: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("button") val button: Button?,
    @SerializedName("link") val link: String?
)

data class Button(
    @SerializedName("text") val text: String?
)
