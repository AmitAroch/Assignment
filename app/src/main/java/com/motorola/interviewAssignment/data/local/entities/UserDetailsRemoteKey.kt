package com.motorola.interviewAssignment.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDetailsRemoteKey(
    @PrimaryKey(autoGenerate = true)
    val key: Int = 0,
    val prevPage: Int?,
    val nextPage: Int?
)
