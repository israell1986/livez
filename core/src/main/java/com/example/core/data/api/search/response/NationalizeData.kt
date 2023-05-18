package com.example.core.data.api.search.response

import com.google.gson.annotations.SerializedName

data class NationalizeData(
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: List<Country>
) {
    data class Country(
        @SerializedName("country_id")
        val countryId: String,
        @SerializedName("probability")
        val probability: Double
    )
}