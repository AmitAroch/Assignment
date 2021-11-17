package com.motorola.interviewAssignment.domain.paging

import androidx.paging.ExperimentalPagingApi
import com.motorola.interviewAssignment.data.local.RoomDb
import com.motorola.interviewAssignment.data.network.requests.GetUsersRequest
import com.motorola.interviewAssignment.domain.repositories.RandomuserApi
import javax.inject.Inject

@ExperimentalPagingApi
class UsersRemoteMediatorCreator @Inject constructor(
    val randomuserApi: RandomuserApi,
    val db: RoomDb,
) {

    fun create(getUsersRequest: GetUsersRequest, onErrorListener: ()->Unit): UsersRemoteMediator {

        return UsersRemoteMediator(
            randomuserApi = randomuserApi,
            db = db,
            getUsersRequest = getUsersRequest,
            errorListener = onErrorListener
        )
    }
}