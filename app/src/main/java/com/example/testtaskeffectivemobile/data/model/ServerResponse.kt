package com.example.testtaskeffectivemobile.data.model

import com.google.gson.annotations.SerializedName

data class ServerResponse(
    @SerializedName("offers")
    val offers: List<Offer>,
    @SerializedName("vacancies")
    val vacancies: List<Vacancy>
)
