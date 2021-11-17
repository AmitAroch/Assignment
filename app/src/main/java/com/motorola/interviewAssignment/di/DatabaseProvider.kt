package com.motorola.interviewAssignment.di

import android.content.Context
import androidx.room.Room
import com.motorola.interviewAssignment.data.local.RoomDb
import com.motorola.interviewAssignment.data.local.UserDetailsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseProvider {

    @Provides
    @Singleton
    fun provideCashDatabase(@ApplicationContext appContext: Context): RoomDb {
        return Room.databaseBuilder(
            appContext,
            RoomDb::class.java,
            "UsersDb"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDetailsDao(db: RoomDb): UserDetailsDao {
        return db.userDetailsDao()
    }


}