package com.sa.core.data.model


import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("article_id")
    val articleId: String?,
    @SerializedName("audio_url")
    val audioUrl: String?,
    @SerializedName("audiobook_id")
    val audiobookId: String?,
    @SerializedName("author_name")
    val authorName: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("chapters")
    val chapters: List<Any?>?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("duration")
    val duration: Long?,
    @SerializedName("episode_count")
    val episodeCount: Int?,
    @SerializedName("episode_id")
    val episodeId: String?,
    @SerializedName("episode_type")
    val episodeType: String?,
    @SerializedName("free_transcript_url")
    val freeTranscriptUrl: Any?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("number")
    val number: Any?,
    @SerializedName("paid_early_access_audio_url")
    val paidEarlyAccessAudioUrl: Any?,
    @SerializedName("paid_early_access_date")
    val paidEarlyAccessDate: Any?,
    @SerializedName("paid_exclusive_start_time")
    val paidExclusiveStartTime: Int?,
    @SerializedName("paid_exclusivity_type")
    val paidExclusivityType: Any?,
    @SerializedName("paid_is_early_access")
    val paidIsEarlyAccess: Boolean?,
    @SerializedName("paid_is_exclusive")
    val paidIsExclusive: Boolean?,
    @SerializedName("paid_is_exclusive_partially")
    val paidIsExclusivePartially: Boolean?,
    @SerializedName("paid_is_now_early_access")
    val paidIsNowEarlyAccess: Boolean?,
    @SerializedName("paid_transcript_url")
    val paidTranscriptUrl: Any?,
    @SerializedName("podcast_id")
    val podcastId: String?,
    @SerializedName("podcast_name")
    val podcastName: String?,
    @SerializedName("podcastPopularityScore")
    val podcastPopularityScore: Int?,
    @SerializedName("podcastPriority")
    val podcastPriority: Int?,
    @SerializedName("popularityScore")
    val popularityScore: Int?,
    @SerializedName("priority")
    val priority: Int?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("score")
    val score: Double?,
    @SerializedName("season_number")
    val seasonNumber: Any?,
    @SerializedName("separated_audio_url")
    val separatedAudioUrl: String?
)