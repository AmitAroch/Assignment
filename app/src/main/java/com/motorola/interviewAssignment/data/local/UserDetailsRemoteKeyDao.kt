package com.motorola.interviewAssignment.data.local

import androidx.room.*
import com.motorola.interviewAssignment.data.local.entities.UserDetailsRemoteKey

@Dao
interface UserDetailsRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(userDetailsRemoteKey: UserDetailsRemoteKey)

    @Query("DELETE FROM UserDetailsRemoteKey")
    fun deleteAllKeys()

    @Query("SELECT * FROM UserDetailsRemoteKey LIMIT 1")
    fun getRemoteKey(): UserDetailsRemoteKey?

}