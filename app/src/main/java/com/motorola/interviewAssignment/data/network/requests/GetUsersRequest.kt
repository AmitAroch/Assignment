package com.motorola.interviewAssignment.data.network.requests

data class GetUsersRequest(
    val page: Int = 1,
    val perPage: Int = 10,
    val withInfo: Boolean = true,
    val fieldsToInclude: List<Fields>? = null,
){

    enum class Fields(val value: String) {
        GENDER("gender"),
        NAME("name"),
        LOCATION("location"),
        EMAIL("email"),
        LOGIN("login"),
        REGISTERED("registered"),
        DATE_OF_BIRT("dob"),
        PHONE("phone"),
        CELL("cell"),
        ID("id"),
        PICTURE("picture"),
        NAT("nat")
    }
}
