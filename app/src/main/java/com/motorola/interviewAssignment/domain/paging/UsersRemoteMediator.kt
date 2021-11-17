package com.motorola.interviewAssignment.domain.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.motorola.interviewAssignment.domain.models.UserItem
import com.motorola.interviewAssignment.data.local.RoomDb
import com.motorola.interviewAssignment.data.local.entities.UserDetailsData
import com.motorola.interviewAssignment.data.local.entities.UserDetailsRemoteKey
import com.motorola.interviewAssignment.data.network.requests.GetUsersRequest
import com.motorola.interviewAssignment.domain.repositories.RandomuserApi
import java.io.IOException
import javax.inject.Inject

private const val INITIAL_PAGE_NUM = 1


@ExperimentalPagingApi
class UsersRemoteMediator @Inject constructor(
    private val randomuserApi: RandomuserApi,
    private val db: RoomDb,
    private val getUsersRequest: GetUsersRequest,
    private val errorListener: () -> Unit
) : RemoteMediator<Int, UserItem>() {
    private val userDetailsDao = db.userDetailsDao()
    private val remoteKeyDao = db.userDetailsRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserItem>
    ): MediatorResult {
        return try {

            val nextPage = when (loadType) {
                LoadType.REFRESH ->
                    INITIAL_PAGE_NUM
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val remoteKey = db.withTransaction {
                        remoteKeyDao.getRemoteKey()
                    }
                    if (remoteKey == null) {
                        INITIAL_PAGE_NUM
                    } else remoteKey.nextPage // always null in this assignment
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                }
            }

            val response =
                randomuserApi.getUsers(getUsersRequest.copy(page = nextPage)) // in this part of the code nextPage will always be 1 in this assignment
            response.error?.let {
                errorListener()
                return MediatorResult.Error(IOException(response.error))
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteAllKeys()
                    userDetailsDao.deleteAllUsers()
                }

                remoteKeyDao.insertOrReplace(
                    UserDetailsRemoteKey(prevPage = null, nextPage = null)
                )

                userDetailsDao.insertAllUsers(
                    response.results!!.map {
                        UserDetailsData(
                            name = it.name.first + " " + it.name.last,
                            birthday = it.dob.date,
                            email = it.email,
                            userPictureUrl = it.picture.large,
                            userThumbnailPictureUrl = it.picture.thumbnail,
                            age = it.dob.age
                        )
                    }
                )

            }

            MediatorResult.Success(
                endOfPaginationReached = true // in this assignment there is always one page
            )

        } catch (e: Exception) {
            errorListener()
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

}