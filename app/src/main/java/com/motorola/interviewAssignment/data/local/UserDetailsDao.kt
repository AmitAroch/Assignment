package com.motorola.interviewAssignment.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.motorola.interviewAssignment.data.local.entities.UserDetailsData
import com.motorola.interviewAssignment.domain.models.UserItem

@Dao
interface UserDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(userssList: List<UserDetailsData>)

    @Query("DELETE FROM UserDetailsData")
    suspend fun deleteAllUsers()

    @Query("SELECT id,name,email,user_picture_url,user_thumbnail_picture_url FROM UserDetailsData ORDER BY age") // ORDER BY
    fun getUsers(): PagingSource<Int,UserItem>

    @Query("SELECT * FROM userdetailsdata WHERE id = :id")
    suspend fun getUserDetailsById(id:Int): UserDetailsData
}