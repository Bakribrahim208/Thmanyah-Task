package com.sa.core.data.model


import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("next_page")
    val nextPage: String?,
    @SerializedName("total_pages")
    val totalPages: Int?
)