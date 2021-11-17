package com.motorola.interviewAssignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.motorola.interviewAssignment.data.local.entities.UserDetailsData
import com.motorola.interviewAssignment.data.local.entities.UserDetailsRemoteKey

@Database(
    entities = [
        UserDetailsData::class,
        UserDetailsRemoteKey::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RoomDb : RoomDatabase() {

    abstract fun userDetailsDao(): UserDetailsDao

    abstract fun userDetailsRemoteKeyDao(): UserDetailsRemoteKeyDao

}