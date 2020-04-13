package com.gov.mpcb.network.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ViewIndustryDirectoryDataResponse<T>(
    @Expose @SerializedName("status") var status: Int,
    @Expose @SerializedName("message") var message: String = "",
    @Expose @SerializedName("indus_data") var industryData: IdIndustryData,
    @Expose @SerializedName("data") var data: List<T>
)

//Application List
data class IdIndustryData(
    @Expose @SerializedName("Address") val address: String,
    @Expose @SerializedName("Email") val emailId: String,
    @Expose @SerializedName("IndustryName") val industryName: String,
    @Expose @SerializedName("TelNo") val telNo: String
)

//Consent
@Parcelize
data class IdConsentData(
    @Expose @SerializedName("Application_id") val Application_id: Int,
    @Expose @SerializedName("Approval_date") val Approval_date: String,
    @Expose @SerializedName("address") val address: String,
    @Expose @SerializedName("applicant_id") val applicant_id: Int,
    @Expose @SerializedName("industryname") val industryname: String,
    @Expose @SerializedName("outward_number") val outward_number: String,
    @Expose @SerializedName("payment_for") val payment_for: Int,
    @Expose @SerializedName("payment_received") val payment_received: Int,
    @Expose @SerializedName("payment_received_on") val payment_received_on: String,
    @Expose @SerializedName("status") val status: Int,
    @Expose @SerializedName("type") val type: String,
    @Expose @SerializedName("unique_id") val unique_id: String,
    @Expose @SerializedName("validitydate") val validitydate: String,
    @Expose @SerializedName("view_link") val view_link: String
): Parcelable

//Authorization
@Parcelize
data class IdAuthorizationData(
    @Expose @SerializedName("Application_id") val Application_id: Int,
    @Expose @SerializedName("address") val address: String,
    @Expose @SerializedName("app_type") val app_type: String,
    @Expose @SerializedName("industryname") val industryname: String,
    @Expose @SerializedName("outward_number") val outward_number: String,
    @Expose @SerializedName("status") val status: Int,
    @Expose @SerializedName("unique_id") val unique_id: String,
    @Expose @SerializedName("view_link") val view_link: String
): Parcelable

//Submission
@Parcelize
data class IdSubmissionData(
    @Expose @SerializedName("created_time") val created_time: String,
    @Expose @SerializedName("Application_id") val Application_id: Int,
    @Expose @SerializedName("address") val address: String,
    @Expose @SerializedName("app_type") val app_type: String,
    @Expose @SerializedName("industryname") val industryname: String,
    @Expose @SerializedName("outward_number") val outward_number: String,
    @Expose @SerializedName("status") val status: String,
    @Expose @SerializedName("unique_id") val unique_id: String,
    @Expose @SerializedName("view_link") val view_link: String
): Parcelable

//Bank Guarantee
data class IdBankGuaranteeData(
    @Expose @SerializedName("amount") val amount: String,
    @Expose @SerializedName("balance_amount") val balance_amount: String,
    @Expose @SerializedName("bank") val bank: String,
    @Expose @SerializedName("bank_name") val bank_name: String,
    @Expose @SerializedName("bg_action") val bg_action: String,
    @Expose @SerializedName("bg_action_by") val bg_action_by: String,
    @Expose @SerializedName("bg_action_on") val bg_action_on: String,
    @Expose @SerializedName("bg_action_remarks") val bg_action_remarks: String,
    @Expose @SerializedName("bg_copy") val bg_copy: String,
    @Expose @SerializedName("bg_for") val bg_for: Int,
    @Expose @SerializedName("bg_id") val bg_id: Int,
    @Expose @SerializedName("bg_no") val bg_no: String,
    @Expose @SerializedName("bg_obtained_for") val bg_obtained_for: String,
    @Expose @SerializedName("bg_type") val bg_type: String,
    @Expose @SerializedName("compliance_period") val compliance_period: String,
    @Expose @SerializedName("conditions") val conditions: String,
    @Expose @SerializedName("consent_date") val consent_date: String,
    @Expose @SerializedName("consent_no") val consent_no: String,
    @Expose @SerializedName("consent_validity") val consent_validity: String,
    @Expose @SerializedName("created_by") val created_by: String,
    @Expose @SerializedName("created_on") val created_on: String,
    @Expose @SerializedName("dr_no") val dr_no: String,
    @Expose @SerializedName("expected_bg_submission") val expected_bg_submission: String,
    @Expose @SerializedName("expiry_date") val expiry_date: String, //bg validity
    @Expose @SerializedName("forfit_action") val forfit_action: String,
    @Expose @SerializedName("forfit_amount") val forfit_amount: String,
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("industry_address") val industry_address: String,
    @Expose @SerializedName("industry_id") val industry_id: Int,
    @Expose @SerializedName("industry_name") val industry_name: String,
    @Expose @SerializedName("is_verified") val is_verified: String,
    @Expose @SerializedName("is_verified_by") val is_verified_by: String,
    @Expose @SerializedName("is_verified_on") val is_verified_on: String,
    @Expose @SerializedName("issue_date") val issue_date: String,
    @Expose @SerializedName("letter_copy") val letter_copy: String,
    @Expose @SerializedName("remarks") val remarks: String,
    @Expose @SerializedName("ro_region") val ro_region: Int,
    @Expose @SerializedName("sro_region") val sro_region: Int,
    @Expose @SerializedName("unique_id") val unique_id: String,
    @Expose @SerializedName("uploadcondition") val uploadcondition: String,
    @Expose @SerializedName("usertype") val usertype: Int,
    @Expose @SerializedName("view_link") val view_link: String
)

//Visit
data class IdVisitData(
    @Expose @SerializedName("address") val address: String,
    @Expose @SerializedName("assiged_to_fo") val assiged_to_fo: Int,
    @Expose @SerializedName("assigned_designation") val assigned_designation: String,
    @Expose @SerializedName("check_in_lat") val check_in_lat: String,
    @Expose @SerializedName("check_in_long") val check_in_long: String,
    @Expose @SerializedName("check_in_status") val check_in_status: Int,
    @Expose @SerializedName("checked_in_date") val checked_in_date: String,
    @Expose @SerializedName("cis_record_number") val cis_record_number: String,
    @Expose @SerializedName("fo_id") val fo_id: Int,
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("industry_category") val industry_category: String,
    @Expose @SerializedName("industry_imis_id") val industry_imis_id: String,
    @Expose @SerializedName("industry_name") val industry_name: String,
    @Expose @SerializedName("industry_scale") val industry_scale: String,
    @Expose @SerializedName("industry_type") val industry_type: String,
    @Expose @SerializedName("is_surprise_inspection") val is_surprise_inspection: Int,
    @Expose @SerializedName("jvs_reference_id") val jvs_reference_id: String,
    @Expose @SerializedName("legal_params") val legal_params: String,
    @Expose @SerializedName("no_of_reminders") val no_of_reminders: String,
    @Expose @SerializedName("remark") val remark: String,
    @Expose @SerializedName("remark_status") val remark_status: String,
    @Expose @SerializedName("reminder_dates") val reminder_dates: String,
    @Expose @SerializedName("routine_id") val routine_id: Int,
    @Expose @SerializedName("routine_visited_on") val routine_visited_on: String,
    @Expose @SerializedName("scanned_copy_link") val scanned_copy_link: String,
    @Expose @SerializedName("schduled_on") val schduled_on: String,
    @Expose @SerializedName("selfie_img") val selfie_img: String,
    @Expose @SerializedName("view_link") val view_link: String,
    @Expose @SerializedName("visit_report_file") val visit_report_file: String,
    @Expose @SerializedName("visit_satus") val visit_satus: Int,
    @Expose @SerializedName("visited_date") val visited_date: String,
    @Expose @SerializedName("visited_officer") val visited_officer: String
)

//Legal
data class IdLegalData(
    @Expose @SerializedName("comp_submitted_on") val comp_submitted_on: String,
    @Expose @SerializedName("created_by") val created_by: Int,
    @Expose @SerializedName("created_by_name") val created_by_name: String,
    @Expose @SerializedName("created_on") val created_on: String,
    @Expose @SerializedName("hearing_date") val hearing_date: String,
    @Expose @SerializedName("hearing_schd_by") val hearing_schd_by: String,
    @Expose @SerializedName("hearing_schd_on") val hearing_schd_on: String,
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("indus_comp_reply") val indus_comp_reply: String,
    @Expose @SerializedName("indus_compl_doc") val indus_compl_doc: String,
    @Expose @SerializedName("industry_address") val industry_address: String,
    @Expose @SerializedName("industry_email") val industry_email: String,
    @Expose @SerializedName("industry_id") val industry_id: Int,
    @Expose @SerializedName("industry_iin") val industry_iin: String,
    @Expose @SerializedName("industry_name") val industry_name: String,
    @Expose @SerializedName("is_application_action") val is_application_action: Int,
    @Expose @SerializedName("issued_by") val issued_by: Int,
    @Expose @SerializedName("issued_by_name") val issued_by_name: String,
    @Expose @SerializedName("issued_on") val issued_on: String,
    @Expose @SerializedName("legal_actions_id") val legal_actions_id: Int,
    @Expose @SerializedName("legal_direction") val legal_direction: String,
    @Expose @SerializedName("legal_direction_document") val legal_direction_document: String,
    @Expose @SerializedName("legal_doc_link") val legal_doc_link: String,
    @Expose @SerializedName("mom") val mom: String,
    @Expose @SerializedName("mom_on") val mom_on: String,
    @Expose @SerializedName("outward_number") val outward_number: String,
    @Expose @SerializedName("outward_number_prev") val outward_number_prev: String,
    @Expose @SerializedName("outward_prev") val outward_prev: String,
    @Expose @SerializedName("proposal_id") val proposal_id: Int,
    @Expose @SerializedName("proposed_action") val proposed_action: String,
    @Expose @SerializedName("status") val status: Int,
    @Expose @SerializedName("status_remarks") val status_remarks: String,
    @Expose @SerializedName("status_updated_on") val status_updated_on: String,
    @Expose @SerializedName("submit_to") val submit_to: Int,
    @Expose @SerializedName("unique_id") val unique_id: String,
    @Expose @SerializedName("visible_to_industry") val visible_to_industry: Int
)