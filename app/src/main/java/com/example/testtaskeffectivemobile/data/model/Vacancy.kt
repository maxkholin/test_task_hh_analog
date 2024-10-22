package com.example.testtaskeffectivemobile.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_vacancies")
data class Vacancy(
    @PrimaryKey
    @SerializedName("id") val id: String,
    @SerializedName("lookingNumber") val lookingNumber: Int?,
    @SerializedName("title") val title: String?,
    @Embedded
    @SerializedName("address") val address: Address?,
    @SerializedName("company") val company: String?,
    @Embedded
    @SerializedName("experience") val experience: Experience?,
    @SerializedName("publishedDate") val publishedDate: String?,
    @SerializedName("isFavorite") var isFavorite: Boolean,
    @Embedded
    @SerializedName("salary") val salary: Salary?,
    // @SerializedName("schedules") val schedules: List<String>,
    @SerializedName("appliedNumber") val appliedNumber: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("responsibilities") val responsibilities: String?,
    // @SerializedName("questions") val questions: List<String>
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
