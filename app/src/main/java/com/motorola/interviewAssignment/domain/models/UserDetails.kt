package com.motorola.interviewAssignment.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetails(
    val userPictureUrl: String,
    val userThumbnailPictureUrl: String,
    val name: String,
    val birthday: String,
): Parcelable