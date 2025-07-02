package com.sa.core.data.model


import com.google.gson.annotations.SerializedName
import com.sa.core.data.mapper.toSectionEntity
import com.sa.core.domain.entities.HomeSectionEntity

data class Section(
    @SerializedName("content")
    val content: List<Content>?,
    @SerializedName("content_type")
    val contentType: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("order")
    val order: Int?,
    @SerializedName("type")
    val type: String?
) {
    fun toDomain(): HomeSectionEntity {
        return this.toSectionEntity()
    }
}