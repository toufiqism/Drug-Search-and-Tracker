package com.example.drugsearchandtracker.data.remote.model

import com.google.gson.annotations.SerializedName

data class DrugResponse(
    @SerializedName("drugGroup")
    val drugGroup: DrugGroup
)

data class DrugGroup(
    @SerializedName("name")
    val name: String?,
    @SerializedName("conceptGroup")
    val conceptGroup: List<ConceptGroup>?
)

data class ConceptGroup(
    @SerializedName("tty")
    val tty: String,
    @SerializedName("conceptProperties")
    val conceptProperties: List<ConceptProperty>?
)

data class ConceptProperty(
    @SerializedName("rxcui")
    val rxcui: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("synonym")
    val synonym: String,
    @SerializedName("tty")
    val tty: String
) 