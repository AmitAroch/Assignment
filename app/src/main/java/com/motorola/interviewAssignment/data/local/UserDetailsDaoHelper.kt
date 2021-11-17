package com.motorola.interviewAssignment.data.local

import androidx.paging.PagingSource
import com.motorola.interviewAssignment.data.local.entities.UserDetailsData
import com.motorola.interviewAssignment.domain.models.UserDetails
import com.motorola.interviewAssignment.domain.models.UserItem
import com.motorola.interviewAssignment.domain.mappers.UserDetailsMapper
import com.motorola.interviewAssignment.domain.repositories.UserDetailsRepo
import javax.inject.Inject

class UserDetailsDaoHelper @Inject constructor(
    private val userDetailsDao: UserDetailsDao
): UserDetailsRepo {

    suspend fun insertAllUsers(userssList: List<UserDetailsData>){
        userDetailsDao.insertAllUsers(userssList)
    }

    suspend fun deleteAllUsers(){
        userDetailsDao.deleteAllUsers()
    }

    override fun getUsers(): PagingSource<Int,UserItem>{
        return userDetailsDao.getUsers()
    }

    override suspend fun getUserDetailsById(userId:Int): UserDetails {
        return UserDetailsMapper.buildFrom(userDetailsDao.getUserDetailsById(userId))
    }
}