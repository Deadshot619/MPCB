package com.gov.mpcb.network.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ViewAppliedListResponse(
    @Expose @SerializedName("data") val data: List<ViewAppliedListData> = listOf(),
    @Expose @SerializedName("message") val message: String = "",
    @Expose @SerializedName("status") val status: Int = -1,
    @Expose @SerializedName("total_rows") val total_rows: Int = 0
): Parcelable

@Parcelize
data class ViewAppliedListData(
    @Expose @SerializedName("DeviceToken") val DeviceToken: String = "",
    @Expose @SerializedName("Unique_id") val Unique_id: String= "",
    @Expose @SerializedName("app_committee_applicable") val app_committee_applicable: String = "",
    //@Expose @SerializedName("app_login_applicable") val app_login_applicable: Int,
    @Expose @SerializedName("applied_by") val applied_by: Int = 0,
    @Expose @SerializedName("applied_by_designation") val applied_by_designation: String = "",
    @Expose @SerializedName("applied_by_name") val applied_by_name: String = "",
    @Expose @SerializedName("applied_on") val applied_on: String = "",
    @Expose @SerializedName("auto_renewal_checked")  val auto_renewal_checked: String = "",
    @Expose @SerializedName("designation") val designation: String = "",
    @Expose @SerializedName("email_address") val email_address: String = "",
    @Expose @SerializedName("ext_no") val ext_no: String = "",
    @Expose @SerializedName("fax_number") val fax_number: String = "",
    //Doesn't exist //@Expose @SerializedName("helpdesk_admin") val helpdesk_admin: Int,
    @Expose @SerializedName("hod_approval_on") val hod_approval_on: String = "",
    @Expose @SerializedName("hod_remarks") val hod_remarks: String = "",
    @Expose @SerializedName("id") val id: Int = 0,
    @Expose @SerializedName("imis_desk_id") val imis_desk_id: String = "",
    @Expose @SerializedName("imis_emp_code") val imis_emp_code: String = "",
    @Expose @SerializedName("imis_value") val imis_value: String = "",
    @Expose @SerializedName("industry_address") val industry_address: String = "",
    @Expose @SerializedName("industry_name") val industry_name: String = "",
    @Expose @SerializedName("industry_ref_number") val industry_ref_number: String = "",
    @Expose @SerializedName("initials") val initials: String = "",
    //Doesn't exist //@Expose @SerializedName("internal_helpdesk") val internal_helpdesk: Int,
//    @Expose @SerializedName("is_account_role_assigned") val is_account_role_assigned: Int,
    @Expose @SerializedName("is_approved_by_hod")  val is_approved_by_hod: Int = 0,
//    @Expose @SerializedName("is_approved_by_ro") val is_approved_by_ro: Int,
//    @Expose @SerializedName("is_approved_by_sro") val is_approved_by_sro: Int,
    @Expose @SerializedName("is_dummy") val is_dummy: String = "",
    @Expose @SerializedName("is_inward_clerk") val is_inward_clerk: String = "",
    @Expose @SerializedName("is_listed_in_task_flow") val is_listed_in_task_flow: Int = 0,
//    @Expose @SerializedName("is_training_coordinator") val is_training_coordinator: Int,
//    @Expose @SerializedName("is_varification_role_assigned") val is_varification_role_assigned: Int,
    @Expose @SerializedName("is_verification_role_assigned") val is_verification_role_assigned: Int = 0,
    @Expose @SerializedName("landline") val landline: String = "",
    @Expose @SerializedName("last_logged_time") val last_logged_time: String = "",
    @Expose @SerializedName("name") val name: String = "",
    @Expose @SerializedName("office") val office: String = "",
    @Expose @SerializedName("office_address") val office_address: String = "",
//    @Expose @SerializedName("only_ticket") val only_ticket: Int,
    @Expose @SerializedName("password") val password: String = "",
    @Expose @SerializedName("phone_number") val phone_number: String = "",
//    @Expose @SerializedName("profile_updated") val profile_updated: Int,
    @Expose @SerializedName("reason_for_surprise_inspection") val reason_for_surprise_inspection: String = "",
    @Expose @SerializedName("reporting") val reporting: String = "",
    @Expose @SerializedName("reporting_person") val reporting_person: String = "",
    @Expose @SerializedName("reporting_to_designation") val reporting_to_designation: String = "",
    @Expose @SerializedName("reporting_to_person_id") val reporting_to_person_id: Int = 0,
    @Expose @SerializedName("ro_approval_on") val ro_approval_on: String = "",
    @Expose @SerializedName("ro_assignment") val ro_assignment: String = "",
    @Expose @SerializedName("ro_remarks") val ro_remarks: String = "",
    @Expose @SerializedName("sro_approval_on") val sro_approval_on: String = "",
    @Expose @SerializedName("sro_assignment") val sro_assignment: String = "",
    @Expose @SerializedName("sro_remarks") val sro_remarks: String = "",
    @Expose @SerializedName("surprise_inspection_on") val surprise_inspection_on: String = "",
    @Expose @SerializedName("tel_no") val tel_no: String = "",
    @Expose @SerializedName("user_login_string_ipad") val user_login_string_ipad: String = "",
    @Expose @SerializedName("web_user") val web_user: String = ""
) : Parcelable
/*
data class daata(
    val DeviceToken: String,
    val Unique_id: String,
    val app_committee_applicable: String,
    val app_login_applicable: String,
    val applied_by: Int,
    val applied_by_designation: String,
    val applied_by_name: String,
    val applied_on: String,
    val auto_renewal_checked: String,
    val designation: String,
    val email_address: String,
    val ext_no: String,
    val fax_number: String,
    val hod_approval_on: String,
    val hod_remarks: String,
    val id: Int,
    val imis_desk_id: String,
    val imis_emp_code: String,
    val imis_value: String,
    val industry_address: String,
    val industry_name: String,
    val industry_ref_number: String,
    val initials: String,
    val is_account_role_assigned: String,
    val is_approved_by_hod: Int,
    val is_approved_by_ro: String,
    val is_approved_by_sro: String,
    val is_dummy: String,
    val is_inward_clerk: String,
    val is_listed_in_task_flow: Int,
    val is_training_coordinator: String,
    val is_varification_role_assigned: String,
    val is_verification_role_assigned: Int,
    val landline: String,
    val last_logged_time: String,
    val name: String,
    val office: String,
    val office_address: String,
    val outward_number: String,
    val password: String,
    val phone_number: String,
    val profile_updated: String,
    val reason_for_surprise_inspection: String,
    val reporting: String,
    val reporting_person: String,
    val reporting_to_designation: String,
    val reporting_to_person_id: Int,
    val ro_approval_on: String,
    val ro_assignment: String,
    val ro_remarks: String,
    val sro_approval_on: String,
    val sro_assignment: String,
    val sro_remarks: String,
    val surprise_inspection_on: String,
    val tel_no: String,
    val user_login_string_ipad: String,
    val web_user: String
)*/
