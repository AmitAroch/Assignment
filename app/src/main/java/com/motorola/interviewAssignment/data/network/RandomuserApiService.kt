package com.motorola.interviewAssignment.data.network

import com.motorola.interviewAssignment.data.network.responses.GetUsersResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RandomuserApiService {

    /*@GET("api/")
    protected abstract fun getUsers(
        @Query("page") page: Int = 1,
        @Query("results") perPage: Int = 10,
        @Query("inc") includeOnlyFields: String = "name,email,dob,picture",
    ): GetUsersResponse*/

    @GET("api/")
    suspend fun getUsers(
        @QueryMap options: Map<String, String>
    ): GetUsersResponse

    @GET("api/?noinfo")
    suspend fun getUsersWithoutInfo(
        @QueryMap options: Map<String, String>
    ): GetUsersResponse

}
