package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ViewAppliedListResponse(
    @Expose @SerializedName("data") val data: List<Data>,
    @Expose @SerializedName("message") val message: String,
    @Expose @SerializedName("status") val status: Int,
    @Expose @SerializedName("total_rows") val total_rows: Int
)

data class Data(
    @Expose @SerializedName("DeviceToken") val DeviceToken: String,
    @Expose @SerializedName("Unique_id") val Unique_id: String,
    @Expose @SerializedName("app_committee_applicable") val app_committee_applicable: String,
    @Expose @SerializedName("app_login_applicable") val app_login_applicable: Int,
    @Expose @SerializedName("applied_by") val applied_by: Int,
    @Expose @SerializedName("applied_by_designation") val applied_by_designation: String,
    @Expose @SerializedName("applied_by_name") val applied_by_name: String,
    @Expose @SerializedName("applied_on") val applied_on: String,
    @Expose @SerializedName("auto_renewal_checked")  val auto_renewal_checked: String,
    @Expose @SerializedName("designation") val designation: String,
    @Expose @SerializedName("email_address") val email_address: String,
    @Expose @SerializedName("ext_no") val ext_no: String,
    @Expose @SerializedName("fax_number") val fax_number: Any,
    @Expose @SerializedName("helpdesk_admin") val helpdesk_admin: Int,
    @Expose @SerializedName("hod_approval_on") val hod_approval_on: String,
    @Expose @SerializedName("hod_remarks") val hod_remarks: String,
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("imis_desk_id") val imis_desk_id: Any,
    @Expose @SerializedName("imis_emp_code") val imis_emp_code: String,
    @Expose @SerializedName("imis_value") val imis_value: String,
    @Expose @SerializedName("industry_address") val industry_address: String,
    @Expose @SerializedName("industry_name") val industry_name: String,
    @Expose @SerializedName("industry_ref_number") val industry_ref_number: String,
    @Expose @SerializedName("initials") val initials: String,
    @Expose @SerializedName("internal_helpdesk") val internal_helpdesk: Int,
    @Expose @SerializedName("is_account_role_assigned") val is_account_role_assigned: Int,
    @Expose @SerializedName("is_approved_by_hod")  val is_approved_by_hod: Int,
    @Expose @SerializedName("is_approved_by_ro") val is_approved_by_ro: Int,
    @Expose @SerializedName("is_approved_by_sro") val is_approved_by_sro: Int,
    @Expose @SerializedName("is_dummy") val is_dummy: Any,
    @Expose @SerializedName("is_inward_clerk") val is_inward_clerk: Any,
    @Expose @SerializedName("is_listed_in_task_flow") val is_listed_in_task_flow: Int,
    @Expose @SerializedName("is_training_coordinator") val is_training_coordinator: Int,
    @Expose @SerializedName("is_varification_role_assigned") val is_varification_role_assigned: Int,
    @Expose @SerializedName("is_verification_role_assigned") val is_verification_role_assigned: Int,
    @Expose @SerializedName("landline") val landline: String,
    @Expose @SerializedName("last_logged_time") val last_logged_time: String,
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("office") val office: String,
    @Expose @SerializedName("office_address") val office_address: Any,
    @Expose @SerializedName("only_ticket") val only_ticket: Int,
    @Expose @SerializedName("password") val password: String,
    @Expose @SerializedName("phone_number") val phone_number: Any,
    @Expose @SerializedName("profile_updated") val profile_updated: Int,
    @Expose @SerializedName("reason_for_surprise_inspection") val reason_for_surprise_inspection: String,
    @Expose @SerializedName("reporting") val reporting: String,
    @Expose @SerializedName("reporting_person") val reporting_person: Any,
    @Expose @SerializedName("reporting_to_designation") val reporting_to_designation: String,
    @Expose @SerializedName("reporting_to_person_id") val reporting_to_person_id: Int,
    @Expose @SerializedName("ro_approval_on") val ro_approval_on: Any,
    @Expose @SerializedName("ro_assignment") val ro_assignment: String,
    @Expose @SerializedName("ro_remarks") val ro_remarks: Any,
    @Expose @SerializedName("sro_approval_on") val sro_approval_on: Any,
    @Expose @SerializedName("sro_assignment") val sro_assignment: String,
    @Expose @SerializedName("sro_remarks") val sro_remarks: Any,
    @Expose @SerializedName("surprise_inspection_on") val surprise_inspection_on: String,
    @Expose @SerializedName("tel_no") val tel_no: String,
    @Expose @SerializedName("user_login_string_ipad") val user_login_string_ipad: String,
    @Expose @SerializedName("web_user") val web_user: String
)