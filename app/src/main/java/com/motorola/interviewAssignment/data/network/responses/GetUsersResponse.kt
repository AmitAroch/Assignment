package com.motorola.interviewAssignment.data.network.responses

data class GetUsersResponse(
    val results: List<UserDetails>?,
    val error: String? = null,
    val info: ResultInfo?
){
    data class UserDetails(
        val name: Name,
        val email: String,
        val picture: UserPicture,
        val dob: Dob
    ){
        data class Name(
            val title: String,
            val first: String,
            val last: String
        )

        data class UserPicture(
            val large: String,
            val medium: String,
            val thumbnail: String
        )

        data class Dob(
            val date: String,
            val age: Int
        )
    }

    data class ResultInfo(
        val seed: String,
        val results: Int,
        val page: Int,
        val version: String
    )
}


