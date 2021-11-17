package com.motorola.interviewAssignment.di

import androidx.paging.ExperimentalPagingApi
import com.motorola.interviewAssignment.data.local.RoomDb
import com.motorola.interviewAssignment.data.local.UserDetailsDao
import com.motorola.interviewAssignment.data.local.UserDetailsDaoHelper
import com.motorola.interviewAssignment.data.network.RandomuserApiHelper
import com.motorola.interviewAssignment.data.network.RandomuserApiService
import com.motorola.interviewAssignment.domain.paging.UsersRemoteMediatorCreator
import com.motorola.interviewAssignment.domain.repositories.RandomuserApi
import com.motorola.interviewAssignment.domain.repositories.UserDetailsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient




@Module
@InstallIn(SingletonComponent::class)
object RandomuserApiProvider {

    @Provides
    @Singleton
    fun provideRandomUserApiService(): RandomuserApiService {
        return getRetrofitRandomUser()
    }

    private fun getRetrofitRandomUser(): RandomuserApiService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val ret = Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return ret.create(RandomuserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRandomUserApi(randomuserApi: RandomuserApiService): RandomuserApi {
        return RandomuserApiHelper(randomuserApi)
    }

    @Provides
    @Singleton
    @ExperimentalPagingApi
    fun provideUsersRemoteMediatorBuilder(randomuserApi: RandomuserApi, db: RoomDb): UsersRemoteMediatorCreator{
        return UsersRemoteMediatorCreator(randomuserApi,db)
    }

    @Provides
    @Singleton
    fun provideUserDetailsRepo(userDetailsDao: UserDetailsDao): UserDetailsRepo{
        return UserDetailsDaoHelper(userDetailsDao)
    }

}