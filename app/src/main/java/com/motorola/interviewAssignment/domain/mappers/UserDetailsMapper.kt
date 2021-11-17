package com.motorola.interviewAssignment.domain.mappers

import com.motorola.interviewAssignment.data.local.entities.UserDetailsData
import com.motorola.interviewAssignment.domain.models.UserDetails

object UserDetailsMapper {

    fun buildFrom(userDetailsData: UserDetailsData): UserDetails {
        return UserDetails(
            userPictureUrl = userDetailsData.userPictureUrl,
            userThumbnailPictureUrl = userDetailsData.userThumbnailPictureUrl,
            name = userDetailsData.name,
            birthday = userDetailsData.birthday
        )
    }
}