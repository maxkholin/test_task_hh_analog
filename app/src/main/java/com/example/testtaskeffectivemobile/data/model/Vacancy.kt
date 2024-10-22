package com.example.testtaskeffectivemobile.data.model

import com.google.gson.annotations.SerializedName

data class Vacancy(
    @SerializedName("id") val id: String?,
    @SerializedName("lookingNumber") val lookingNumber: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("address") val address: Address?,
    @SerializedName("company") val company: String?,
    @SerializedName("experience") val experience: Experience?,
    @SerializedName("publishedDate") val publishedDate: String?,
    @SerializedName("isFavorite") var isFavorite: Boolean?,
    @SerializedName("salary") val salary: Salary?,
    @SerializedName("schedules") val schedules: List<String>,
    @SerializedName("appliedNumber") val appliedNumber: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("responsibilities") val responsibilities: String?,
    @SerializedName("questions") val questions: List<String>
)

data class Address(
    @SerializedName("town") val town: String?,
    @SerializedName("street") val street: String?,
    @SerializedName("house") val house: String?
)

data class Experience(
    @SerializedName("previewText") val previewText: String?,
    @SerializedName("text") val text: String?
)

data class Salary(
    @SerializedName("full") val full: String?
)
