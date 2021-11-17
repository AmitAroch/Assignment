package com.motorola.interviewAssignment.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class UserDetailsData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Int = 0,
    @ColumnInfo(name = "user_picture_url") val userPictureUrl: String,
    @ColumnInfo(name = "user_thumbnail_picture_url") val userThumbnailPictureUrl: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    val birthday: String,
    val age: Int
): Parcelable