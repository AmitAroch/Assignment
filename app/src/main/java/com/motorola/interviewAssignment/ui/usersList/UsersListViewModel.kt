package com.motorola.interviewAssignment.ui.usersList

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.motorola.interviewAssignment.domain.models.UserDetails
import com.motorola.interviewAssignment.domain.models.UserItem
import com.motorola.interviewAssignment.domain.paging.UsersRemoteMediator
import com.motorola.interviewAssignment.data.network.requests.GetUsersRequest
import com.motorola.interviewAssignment.domain.paging.UsersRemoteMediatorCreator
import com.motorola.interviewAssignment.domain.repositories.UserDetailsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class UsersListViewModel @Inject constructor(
    application: Application,
    private val userDetailsRepo: UserDetailsRepo,
    private val usersRemoteMediatorCreator: UsersRemoteMediatorCreator
) : AndroidViewModel(application) {

    private val getUsersRequest: GetUsersRequest =  GetUsersRequest(
        fieldsToInclude = listOf(
            GetUsersRequest.Fields.EMAIL,
            GetUsersRequest.Fields.DATE_OF_BIRT,
            GetUsersRequest.Fields.PICTURE,
            GetUsersRequest.Fields.NAME
        )
    )
    private lateinit var remoteMediator: UsersRemoteMediator
    private lateinit var paginConfig: PagingConfig

    private val _usersFlow =
        MutableStateFlow<PagingData<UserItem>>(PagingData.empty())
    val usersFlow: Flow<PagingData<UserItem>> = _usersFlow.asStateFlow()

    private val _errorFlow = MutableStateFlow(false)
    val errorFlow: Flow<Boolean> = _errorFlow.asStateFlow()

    private val _networkAvailableFlow = MutableStateFlow(false)
    val networkAvailableFlow: Flow<Boolean> = _networkAvailableFlow

    private fun initUsersFlowByGetUsersRequest() {
        remoteMediator =  usersRemoteMediatorCreator.create(getUsersRequest) {
            _errorFlow.value = true
        }
        viewModelScope.launch {
            initPager(remoteMediator)
        }
    }

    private suspend fun initPager(
        remoteMediator: RemoteMediator<Int, UserItem>,
    ) {
        val pager = Pager(
            config = paginConfig,
            remoteMediator = remoteMediator
        ) {
            userDetailsRepo.getUsers()
        }
        pager.flow.cachedIn(viewModelScope).collectLatest {
            _usersFlow.value = it
        }

    }

    fun loadUsers() {
        paginConfig = PagingConfig(
            pageSize = getUsersRequest.perPage,
            enablePlaceholders = false,
            initialLoadSize = getUsersRequest.perPage,
            prefetchDistance = 1,
        )
        initUsersFlowByGetUsersRequest()
    }

    suspend fun refreshList() {
        remoteMediator.load(
            LoadType.REFRESH, PagingState(
                pages = listOf(),
                anchorPosition = null,
                config = paginConfig,
                leadingPlaceholderCount = 0
            )
        )
    }

    suspend fun getUserDetailsById(userId: Int?): UserDetails? {
        return userId?.let { userDetailsRepo.getUserDetailsById(userId) }
    }

    fun onNetWorkChange(): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _networkAvailableFlow.value = true
            }

            override fun onLost(network: Network) {
                _networkAvailableFlow.value = false
            }
        }
    }

    fun requestErrorRegistered(){
        _errorFlow.value = false
    }

}