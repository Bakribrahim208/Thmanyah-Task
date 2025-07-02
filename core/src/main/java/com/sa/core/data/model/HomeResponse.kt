package com.sa.core.data.model


import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("pagination")
    val pagination: Pagination?,
    @SerializedName("sections")
    val sections: List<Section>
)