package com.motorola.interviewAssignment.domain.repositories

import com.motorola.interviewAssignment.data.network.requests.GetUsersRequest
import com.motorola.interviewAssignment.data.network.responses.GetUsersResponse

interface RandomuserApi {
    suspend fun getUsers(getUsersRequest: GetUsersRequest): GetUsersResponse
}