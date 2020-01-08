package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ViewTaskDetailsReponse(

    @Expose @SerializedName("status") var status: Boolean,
    @Expose @SerializedName("message") var message: String = "",
    @Expose @SerializedName("data") var data: List<Data>
)

data class Data(
    @Expose @SerializedName("closed_on") val closedOn: String,
    @Expose @SerializedName("created_on") val createdOn: String,
    @Expose @SerializedName("description") val description: String,
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("initiated_by") val initiatedBy: Int,
    @Expose @SerializedName("initiated_by_name") val initiatedByName: String,
    @Expose @SerializedName("scheduled_on") val scheduledOn: String,
    @Expose @SerializedName("status") val status: Int,
    @Expose @SerializedName("subject") val subject: String,
    @Expose @SerializedName("user_involved") val userInvolved: String,
    @Expose @SerializedName("users_assigned_name") val usersAssignedNames: List<UsersAssignedName>
)

data class UsersAssignedName(
    @Expose @SerializedName("name_of_officer") val nameOfOfficer: String
)