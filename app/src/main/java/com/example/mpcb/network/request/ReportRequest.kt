package com.example.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReportRequest {

    @SerializedName("RequestId")
    @Expose
    val requestId: String? = null
    @SerializedName("visitId")
    @Expose
    val visitId: Int? = null
    @SerializedName("indus_imis_id")
    @Expose
    val indusImisId: String? = null
    @SerializedName("UserId")
    @Expose
    val userId: Int? = null
    @SerializedName("data")
    @Expose
    val data: Data? = null
}

class Data {
    @SerializedName("industry_category_reselect")
    @Expose
    val industryCategoryReselect: String? = null
    @SerializedName("routine_report")
    @Expose
    val routineReport: RoutineReport? = null
    @SerializedName("routine_report_products")
    @Expose
    val routineReportProducts: ArrayList<RoutineReportProduct>? = null
    @SerializedName("jvs_sample_collected_water_source")
    @Expose
    val jvsSampleCollectedWaterSource: ArrayList<JvsSampleCollectedWaterSource>? = null
    @SerializedName("routine_report_air_pollution")
    @Expose
    val routineReportAirPollution: ArrayList<RoutineReportAirPollution>? = null
    @SerializedName("jvs_sample_collected_air_source")
    @Expose
    val jvsSampleCollectedAirSource: ArrayList<JvsSampleCollectedAirSource>? = null
    @SerializedName("routine_report_hazardous_waste")
    @Expose
    val routineReportHazardousWaste: ArrayList<RoutineReportHazardousWaste>? = null
    @SerializedName("routine_report_non_hazardous_waste")
    @Expose
    val routineReportNonHazardousWaste: ArrayList<RoutineReportNonHazardousWaste>? = null
    @SerializedName("routine_report_bank_details")
    @Expose
    val routineReportBankDetails: ArrayList<RoutineReportBankDetail>? = null
}

class JvsSampleCollectedAirSource {
    @SerializedName("name_of_source")
    @Expose
    val nameOfSource: String? = null
    @SerializedName("jvs_air_source_parameter")
    @Expose
    val jvsAirSourceParameter: ArrayList<String>? = null
    @SerializedName("jvs_air_source_std_prescribed")
    @Expose
    val jvsAirSourceStdPrescribed: ArrayList<String>? = null
}

class JvsSampleCollectedWaterSource {
    @SerializedName("name_of_source")
    @Expose
    val nameOfSource: String? = null
    @SerializedName("jvs_water_source_parameter")
    @Expose
    val jvsWaterSourceParameter: ArrayList<String>? = null
    @SerializedName("jvs_water_source_std_prescribed")
    @Expose
    val jvsWaterSourceStdPrescribed: ArrayList<String>? = null
}

class RoutineReport {
    @SerializedName("visited_on")
    @Expose
    val visitedOn: String? = null
    @SerializedName("email_address")
    @Expose
    val emailAddress: String? = null
    @SerializedName("telephone_number")
    @Expose
    val telephoneNumber: String? = null
    @SerializedName("consent_obtain")
    @Expose
    val consentObtain: Int? = null
    @SerializedName("validity_of_consent_upto")
    @Expose
    val validityOfConsentUpto: String? = null
    @SerializedName("validity_of_consent_ie")
    @Expose
    val validityOfConsentIe: String? = null
    @SerializedName("hw_of_valid_upto_de")
    @Expose
    val hwOfValidUptoDe: String? = null
    @SerializedName("generation_industrial_as_consent")
    @Expose
    val generationIndustrialAsConsent: String? = null
    @SerializedName("generation_industrial_as_consent_cooling")
    @Expose
    val generationIndustrialAsConsentCooling: String? = null
    @SerializedName("generation_domestic_as_consent")
    @Expose
    val generationDomesticAsConsent: String? = null
    @SerializedName("generation_industrial_actual")
    @Expose
    val generationIndustrialActual: String? = null
    @SerializedName("generation_industrial_actual_cooling")
    @Expose
    val generationIndustrialActualCooling: String? = null
    @SerializedName("generation_domestic_actual")
    @Expose
    val generationDomesticActual: String? = null
    @SerializedName("treatment_industrial_primary")
    @Expose
    val treatmentIndustrialPrimary: Int? = null
    @SerializedName("industrial_primary_OG_trap")
    @Expose
    val industrialPrimaryOGTrap: Int? = null
    @SerializedName("industrial_primary_screening")
    @Expose
    val industrialPrimaryScreening: Int? = null
    @SerializedName("industrial_primary_neutralization")
    @Expose
    val industrialPrimaryNeutralization: Int? = null
    @SerializedName("industrial_primary_primary_settling")
    @Expose
    val industrialPrimaryPrimarySettling: Int? = null
    @SerializedName("industrial_primary_any_other")
    @Expose
    val industrialPrimaryAnyOther: Int? = null
    @SerializedName("industrial_primary_any_other_text")
    @Expose
    val industrialPrimaryAnyOtherText: String? = null
    @SerializedName("treatment_domestic_septic_tank")
    @Expose
    val treatmentDomesticSepticTank: Int? = null
    @SerializedName("treatment_industrial_secondary")
    @Expose
    val treatmentIndustrialSecondary: Int? = null
    @SerializedName("industrial_secondary_activated_sludge_process")
    @Expose
    val industrialSecondaryActivatedSludgeProcess: Int? = null
    @SerializedName("industrial_secondary_mbbr")
    @Expose
    val industrialSecondaryMbbr: Int? = null
    @SerializedName("industrial_secondary_sbr")
    @Expose
    val industrialSecondarySbr: Int? = null
    @SerializedName("industrial_secondary_trickling_filter")
    @Expose
    val industrialSecondaryTricklingFilter: Int? = null
    @SerializedName("industrial_secondary_any_other")
    @Expose
    val industrialSecondaryAnyOther: Int? = null
    @SerializedName("industrial_secondary_any_other_text")
    @Expose
    val industrialSecondaryAnyOtherText: String? = null
    @SerializedName("treatment_domestic_severage_treatment_plant")
    @Expose
    val treatmentDomesticSeverageTreatmentPlant: Int? = null
    @SerializedName("treatment_domestic_activated_sludge_process")
    @Expose
    val treatmentDomesticActivatedSludgeProcess: Int? = null
    @SerializedName("treatment_domestic_mbbr")
    @Expose
    val treatmentDomesticMbbr: Int? = null
    @SerializedName("treatment_domestic_sbr")
    @Expose
    val treatmentDomesticSbr: Int? = null
    @SerializedName("treatment_domestic_trickling_filter")
    @Expose
    val treatmentDomesticTricklingFilter: Int? = null
    @SerializedName("treatment_domestic_any_other")
    @Expose
    val treatmentDomesticAnyOther: Int? = null
    @SerializedName("treatment_domestic_any_other_text")
    @Expose
    val treatmentDomesticAnyOtherText: String? = null
    @SerializedName("treatment_industrial_tertiary")
    @Expose
    val treatmentIndustrialTertiary: Int? = null
    @SerializedName("industrial_tertiary_presser_sand_filter")
    @Expose
    val industrialTertiaryPresserSandFilter: Int? = null
    @SerializedName("industrial_tertiary_activated_carbon_filter")
    @Expose
    val industrialTertiaryActivatedCarbonFilter: Int? = null
    @SerializedName("industrial_tertiary_dual_media_filter")
    @Expose
    val industrialTertiaryDualMediaFilter: Int? = null
    @SerializedName("industrial_tertiary_any_other")
    @Expose
    val industrialTertiaryAnyOther: Int? = null
    @SerializedName("industrial_tertiary_any_other_text")
    @Expose
    val industrialTertiaryAnyOtherText: String? = null
    @SerializedName("treatment_industrial_advanced")
    @Expose
    val treatmentIndustrialAdvanced: Int? = null
    @SerializedName("industrial_advanced_reverse_osmosis")
    @Expose
    val industrialAdvancedReverseOsmosis: Int? = null
    @SerializedName("industrial_advanced_mee")
    @Expose
    val industrialAdvancedMee: Int? = null
    @SerializedName("ultra_filtration")
    @Expose
    val ultraFiltration: Int? = null
    @SerializedName("nano_filtration")
    @Expose
    val nanoFiltration: Int? = null
    @SerializedName("atfd")
    @Expose
    val atfd: Int? = null
    @SerializedName("industrial_advanced_any_other")
    @Expose
    val industrialAdvancedAnyOther: Int? = null
    @SerializedName("industrial_advanced_any_other_text")
    @Expose
    val industrialAdvancedAnyOtherText: String? = null
    @SerializedName("treatment_observation")
    @Expose
    val treatmentObservation: String? = null
    @SerializedName("etp_operational")
    @Expose
    val etpOperational: Int? = null
    @SerializedName("stp_operational")
    @Expose
    val stpOperational: Int? = null
    @SerializedName("disposal_industrial_CETP")
    @Expose
    val disposalIndustrialCETP: Int? = null
    @SerializedName("disposal_industrial_CETP_text")
    @Expose
    val disposalIndustrialCETPText: String? = null
    @SerializedName("disposal_domestic_CETP")
    @Expose
    val disposalDomesticCETP: Int? = null
    @SerializedName("disposal_domestic_CETP_text")
    @Expose
    val disposalDomesticCETPText: String? = null
    @SerializedName("disposal_industrial_land_gardening")
    @Expose
    val disposalIndustrialLandGardening: Int? = null
    @SerializedName("disposal_industrial_land_gardening_text")
    @Expose
    val disposalIndustrialLandGardeningText: String? = null
    @SerializedName("disposal_domestic_land_gardening")
    @Expose
    val disposalDomesticLandGardening: Int? = null
    @SerializedName("disposal_domestic_land_gardening_text")
    @Expose
    val disposalDomesticLandGardeningText: String? = null
    @SerializedName("disposal_industrial_recycle")
    @Expose
    val disposalIndustrialRecycle: Int? = null
    @SerializedName("disposal_industrial_recycle_text")
    @Expose
    val disposalIndustrialRecycleText: String? = null
    @SerializedName("disposal_domestic_recycle")
    @Expose
    val disposalDomesticRecycle: Int? = null
    @SerializedName("disposal_domestic_recycle_text")
    @Expose
    val disposalDomesticRecycleText: String? = null
    @SerializedName("disposal_industrial_local_body_sewage")
    @Expose
    val disposalIndustrialLocalBodySewage: Int? = null
    @SerializedName("disposal_industrial_local_body_sewage_text")
    @Expose
    val disposalIndustrialLocalBodySewageText: String? = null
    @SerializedName("disposal_domestic_local_body_sewage")
    @Expose
    val disposalDomesticLocalBodySewage: Int? = null
    @SerializedName("disposal_domestic_local_body_sewage_text")
    @Expose
    val disposalDomesticLocalBodySewageText: String? = null
    @SerializedName("disposal_industrial_any_other")
    @Expose
    val disposalIndustrialAnyOther: Int? = null
    @SerializedName("disposal_industrial_any_other_text_remarks")
    @Expose
    val disposalIndustrialAnyOtherTextRemarks: String? = null
    @SerializedName("disposal_industrial_any_other_text")
    @Expose
    val disposalIndustrialAnyOtherText: String? = null
    @SerializedName("disposal_domestic_any_other")
    @Expose
    val disposalDomesticAnyOther: Int? = null
    @SerializedName("disposal_domestic_any_other_text_remarks")
    @Expose
    val disposalDomesticAnyOtherTextRemarks: String? = null
    @SerializedName("disposal_domestic_any_other_text")
    @Expose
    val disposalDomesticAnyOtherText: String? = null
    @SerializedName("disposal_industrial_total")
    @Expose
    val disposalIndustrialTotal: Int? = null
    @SerializedName("disposal_domestic_total")
    @Expose
    val disposalDomesticTotal: Int? = null
    @SerializedName("disposal_observation")
    @Expose
    val disposalObservation: String? = null
    @SerializedName("disposal_industrial_as_per_consent")
    @Expose
    val disposalIndustrialAsPerConsent: String? = null
    @SerializedName("disposal_domestic_as_per_consent")
    @Expose
    val disposalDomesticAsPerConsent: String? = null
    @SerializedName("operation_and_maintainance_insus")
    @Expose
    val operationAndMaintainanceInsus: String? = null
    @SerializedName("operation_and_maintainance_domestic")
    @Expose
    val operationAndMaintainanceDomestic: String? = null
    @SerializedName("omsw_applicable")
    @Expose
    val omswApplicable: Int? = null
    @SerializedName("omsw_installed")
    @Expose
    val omswInstalled: Int? = null
    @SerializedName("omsw_cpcb")
    @Expose
    val omswCpcb: Int? = null
    @SerializedName("omsw_mpcb")
    @Expose
    val omswMpcb: Int? = null
    @SerializedName("remote_cal_applicable_water")
    @Expose
    val remoteCalApplicableWater: Int? = null
    @SerializedName("sensor_placed_water")
    @Expose
    val sensorPlacedWater: Int? = null
    @SerializedName("electrict_meter_provided")
    @Expose
    val electrictMeterProvided: Int? = null
    @SerializedName("electrict_meter_reading")
    @Expose
    val electrictMeterReading: Int? = null
    @SerializedName("date_of_collection_indus")
    @Expose
    val dateOfCollectionIndus: String? = null
    @SerializedName("date_of_collection_domestic")
    @Expose
    val dateOfCollectionDomestic: String? = null
    @SerializedName("payment_details_indus")
    @Expose
    val paymentDetailsIndus: String? = null
    @SerializedName("payment_details_domestic")
    @Expose
    val paymentDetailsDomestic: String? = null
    @SerializedName("payment_details_indus_amount")
    @Expose
    val paymentDetailsIndusAmount: String? = null
    @SerializedName("payment_details_domestic_amount")
    @Expose
    val paymentDetailsDomesticAmount: String? = null
    @SerializedName("payment_details_indus_date")
    @Expose
    val paymentDetailsIndusDate: String? = null
    @SerializedName("payment_details_domestic_date")
    @Expose
    val paymentDetailsDomesticDate: String? = null
    @SerializedName("jvs_sample_collected_for_water")
    @Expose
    val jvsSampleCollectedForWater: Int? = null
    @SerializedName("air_pollution_observation")
    @Expose
    val airPollutionObservation: String? = null
    @SerializedName("omsa_applicable")
    @Expose
    val omsaApplicable: Int? = null
    @SerializedName("omsa_installed")
    @Expose
    val omsaInstalled: Int? = null
    @SerializedName("omsa_cpcb")
    @Expose
    val omsaCpcb: Int? = null
    @SerializedName("omsa_mpcb")
    @Expose
    val omsaMpcb: Int? = null
    @SerializedName("remote_cal_applicable")
    @Expose
    val remoteCalApplicable: Int? = null
    @SerializedName("sensor_placed")
    @Expose
    val sensorPlaced: Int? = null
    @SerializedName("stack_facility_exist")
    @Expose
    val stackFacilityExist: Int? = null
    @SerializedName("cal_fac_exist")
    @Expose
    val calFacExist: Int? = null
    @SerializedName("omsam_applicable")
    @Expose
    val omsamApplicable: Int? = null
    @SerializedName("omsam_installed")
    @Expose
    val omsamInstalled: Int? = null
    @SerializedName("omsam_cpcb")
    @Expose
    val omsamCpcb: Int? = null
    @SerializedName("omsam_mpcb")
    @Expose
    val omsamMpcb: Int? = null
    @SerializedName("jvs_sample_collected_for_air")
    @Expose
    val jvsSampleCollectedForAir: Int? = null
    @SerializedName("jvs_observation")
    @Expose
    val jvsObservation: String? = null
    @SerializedName("tree_plantation_plot_area")
    @Expose
    val treePlantationPlotArea: String? = null
    @SerializedName("tree_plantation_built_area")
    @Expose
    val treePlantationBuiltArea: String? = null
    @SerializedName("tree_plantation_green_belt_area")
    @Expose
    val treePlantationGreenBeltArea: String? = null
    @SerializedName("tree_plantation_plantation_no")
    @Expose
    val treePlantationPlantationNo: String? = null
    @SerializedName("tree_plantation_proposed_plantation")
    @Expose
    val treePlantationProposedPlantation: String? = null
    @SerializedName("hw_annual_return_date")
    @Expose
    val hwAnnualReturnDate: String? = null
    @SerializedName("env_statement_report")
    @Expose
    val envStatementReport: String? = null
    @SerializedName("action_initiated_date")
    @Expose
    val actionInitiatedDate: String? = null
    @SerializedName("special_compliance")
    @Expose
    val specialCompliance: String? = null
    @SerializedName("bg_imposed")
    @Expose
    val bgImposed: String? = null
    @SerializedName("bg_imposed_against")
    @Expose
    val bgImposedAgainst: String? = null
    @SerializedName("bg_imposed_number")
    @Expose
    val bgImposedNumber: String? = null
    @SerializedName("additional_info")
    @Expose
    val additionalInfo: String? = null
    @SerializedName("legal_action_unit_complied")
    @Expose
    val legalActionUnitComplied: Int? = null
}

class RoutineReportAirPollution {
    @SerializedName("air_pollution_source")
    @Expose
    val airPollutionSource: String? = null
    @SerializedName("air_pollution_source_other")
    @Expose
    val airPollutionSourceOther: String? = null
    @SerializedName("air_pollution_type")
    @Expose
    val airPollutionType: String? = null
    @SerializedName("air_pollution_fuel_name")
    @Expose
    val airPollutionFuelName: String? = null
    @SerializedName("air_pollution_fuel_quantity")
    @Expose
    val airPollutionFuelQuantity: String? = null
    @SerializedName("air_pollution_fuel_unit")
    @Expose
    val airPollutionFuelUnit: String? = null
    @SerializedName("air_pollution_pollutants")
    @Expose
    val airPollutionPollutants: String? = null
    @SerializedName("air_pollution_mech_dust_collector")
    @Expose
    val airPollutionMechDustCollector: Int? = null
    @SerializedName("air_pollution_cyclone_dust_collector")
    @Expose
    val airPollutionCycloneDustCollector: Int? = null
    @SerializedName("air_pollution_multi_dust_collector")
    @Expose
    val airPollutionMultiDustCollector: Int? = null
    @SerializedName("air_pollution_fabric_bag_filter")
    @Expose
    val airPollutionFabricBagFilter: Int? = null
    @SerializedName("air_pollution_package_tower")
    @Expose
    val airPollutionPackageTower: String? = null
    @SerializedName("air_pollution_venturi_scrubber")
    @Expose
    val airPollutionVenturiScrubber: Int? = null
    @SerializedName("air_pollution_electro_static")
    @Expose
    val airPollutionElectroStatic: Int? = null
    @SerializedName("air_pollution_no_provision")
    @Expose
    val airPollutionNoProvision: Int? = null
    @SerializedName("air_pollution_any_other")
    @Expose
    val airPollutionAnyOther: Int? = null
    @SerializedName("air_pollution_stack_height")
    @Expose
    val airPollutionStackHeight: Int? = null
}

class RoutineReportBankDetail {
    @SerializedName("bank_guarantee_imposed_for")
    @Expose
    val bankGuaranteeImposedFor: String? = null
    @SerializedName("bank_submitted")
    @Expose
    val bankSubmitted: String? = null
    @SerializedName("bank_guarented_no")
    @Expose
    val bankGuarentedNo: String? = null
    @SerializedName("date_of_guarantee")
    @Expose
    val dateOfGuarantee: String? = null
    @SerializedName("date_of_validity")
    @Expose
    val dateOfValidity: String? = null
}

class RoutineReportHazardousWaste {
    @SerializedName("hazardous_waste_category_name")
    @Expose
    val hazardousWasteCategoryName: String? = null
    @SerializedName("hazardous_waste_quantity")
    @Expose
    val hazardousWasteQuantity: String? = null
    @SerializedName("hw_disposal_method")
    @Expose
    val hwDisposalMethod: String? = null
    @SerializedName("hw_actual_disposal")
    @Expose
    val hwActualDisposal: String? = null
    @SerializedName("hw_form_disposal")
    @Expose
    val hwFormDisposal: String? = null
    @SerializedName("hw_form_cswtsdf")
    @Expose
    val hwFormCswtsdf: String? = null
    @SerializedName("hw_form_co_processing")
    @Expose
    val hwFormCoProcessing: String? = null
    @SerializedName("hw_disposed_actualuser")
    @Expose
    val hwDisposedActualuser: String? = null
    @SerializedName("hw_disposal_quantity")
    @Expose
    val hwDisposalQuantity: String? = null
    @SerializedName("hw_disposal_date")
    @Expose
    val hwDisposalDate: String? = null
    @SerializedName("hw_disposal_quantity_unit")
    @Expose
    val hwDisposalQuantityUnit: String? = null
}

class RoutineReportNonHazardousWaste {
    @SerializedName("nhw_waste_name")
    @Expose
    val nhwWasteName: String? = null
    @SerializedName("nhw_quantity")
    @Expose
    val nhwQuantity: String? = null
    @SerializedName("nhw_disposal_method")
    @Expose
    val nhwDisposalMethod: String? = null
    @SerializedName("nhw_disposal_date")
    @Expose
    val nhwDisposalDate: String? = null
    @SerializedName("nhw_disposal_quantity")
    @Expose
    val nhwDisposalQuantity: String? = null
    @SerializedName("nhw_actualdisposal")
    @Expose
    val nhwActualdisposal: String? = null
    @SerializedName("nhw_disposal_quantity_unit")
    @Expose
    val nhwDisposalQuantityUnit: String? = null
}

class RoutineReportProduct {
    @SerializedName("product_name")
    @Expose
    val productName: String? = null
    @SerializedName("product_quantity")
    @Expose
    val productQuantity: String? = null
    @SerializedName("product_uom")
    @Expose
    val productUom: String? = null
    @SerializedName("product_quantity_actual")
    @Expose
    val productQuantityActual: String? = null
    @SerializedName("product_uom_actual")
    @Expose
    val productUomActual: String? = null
}