package com.chahinem.tmdb.entities

import com.squareup.moshi.Json

class CrewMember(
    val id: Int? = null,
    @Json(name = "credit_id") val creditId: String? = null,
    val name: String? = null,
    @Json(name = "profile_path") val profilePath: String? = null,
    val department: String? = null,
    val job: String? = null
)
