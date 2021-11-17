package com.motorola.interviewAssignment.data.network

import com.motorola.interviewAssignment.data.network.requests.GetUsersRequest
import com.motorola.interviewAssignment.data.network.responses.GetUsersResponse
import com.motorola.interviewAssignment.domain.repositories.RandomuserApi
import javax.inject.Inject

class RandomuserApiHelper @Inject constructor(
    private val randomuserApi: RandomuserApiService,
    ): RandomuserApi {

    override suspend fun getUsers(getUsersRequest: GetUsersRequest): GetUsersResponse {
        if (getUsersRequest.withInfo) {
            return randomuserApi.getUsers(
                convertGetUsersRequestToMap(getUsersRequest)
            )
        } else {
            return randomuserApi.getUsersWithoutInfo(
                convertGetUsersRequestToMap(getUsersRequest)
            )
        }
    }

    private fun convertGetUsersRequestToMap(getUsersRequest: GetUsersRequest): Map<String, String> {
        val result = HashMap<String, String>()
        result.put("page", getUsersRequest.page.toString())
        result.put("results", getUsersRequest.perPage.toString())
        //result.put("withInfo", getUsersRequest.withInfo.toString())
        listToString(getUsersRequest.fieldsToInclude)?.let { result.put("inc", it) }
        return result
    }

    private fun listToString(randomuserFields: List<GetUsersRequest.Fields>?): String? {
        if (randomuserFields.isNullOrEmpty()) return null

        val stringBuilder = StringBuilder()
        for (field in randomuserFields) {
            stringBuilder.append(field.value)
            stringBuilder.append(",")
        }
        stringBuilder.deleteAt(stringBuilder.lastIndex)
        return stringBuilder.toString()
    }
}