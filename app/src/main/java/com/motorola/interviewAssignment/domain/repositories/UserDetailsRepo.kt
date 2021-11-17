package com.motorola.interviewAssignment.domain.repositories

import androidx.paging.PagingSource
import com.motorola.interviewAssignment.domain.models.UserDetails
import com.motorola.interviewAssignment.domain.models.UserItem

interface UserDetailsRepo {

    suspend fun getUserDetailsById(userId: Int): UserDetails

    fun getUsers(): PagingSource<Int, UserItem>
}