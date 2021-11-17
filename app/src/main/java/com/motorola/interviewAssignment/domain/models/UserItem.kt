package com.motorola.interviewAssignment.domain.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItem (
    @ColumnInfo(name = "id") val id : Int = 0,
    @ColumnInfo(name = "user_picture_url") val userPictureUrl: String,
    @ColumnInfo(name = "user_thumbnail_picture_url") val userThumbnailPictureUrl: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
): Parcelable