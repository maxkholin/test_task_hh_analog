package com.example.testtaskeffectivemobile.data.model

import com.google.gson.annotations.SerializedName

data class Offers(
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("button") val button: Button?,
    @SerializedName("link") val link: String?
)

data class Button(
    @SerializedName("button") val text: String?
)
