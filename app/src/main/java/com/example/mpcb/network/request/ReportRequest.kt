package com.example.mpcb.network.request

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReportRequest {

    @SerializedName("RequestId")
    @Expose
    var requestId: String = ""
    @SerializedName("visitId")
    @Expose
    var visitId: Int? = null
    @SerializedName("indus_imis_id")
    @Expose
    var indusImisId: String = ""
    @SerializedName("UserId")
    @Expose
    var userId: Int? = null
    @SerializedName("data")
    @Expose
    var data: Data = Data()
}

class Data {
    @SerializedName("industry_category_reselect")
    @Expose
    var industryCategoryReselect = ""
    @SerializedName("routine_report")
    @Expose
    var routineReport = RoutineReport()
    @SerializedName("routine_report_products")
    @Expose
    var routineReportProducts: ArrayList<RoutineReportProduct> = arrayListOf()
    @SerializedName("jvs_sample_collected_water_source")
    @Expose
    var jvsSampleCollectedWaterSource: ArrayList<JvsSampleCollectedWaterSource> = arrayListOf()
    @SerializedName("routine_report_air_pollution")
    @Expose
    var routineReportAirPollution: ArrayList<RoutineReportAirPollution> = arrayListOf()
    @SerializedName("jvs_sample_collected_air_source")
    @Expose
    var jvsSampleCollectedAirSource: ArrayList<JvsSampleCollectedAirSource> = arrayListOf()
    @SerializedName("routine_report_hazardous_waste")
    @Expose
    var routineReportHazardousWaste: ArrayList<RoutineReportHazardousWaste> = arrayListOf()
    @SerializedName("routine_report_non_hazardous_waste")
    @Expose
    var routineReportNonHazardousWaste: ArrayList<RoutineReportNonHazardousWaste> = arrayListOf()
    @SerializedName("routine_report_bank_details")
    @Expose
    var routineReportBankDetails: ArrayList<RoutineReportBankDetail> = arrayListOf()
}

class RoutineReport {
    @SerializedName("visited_on")
    @Expose
    var visitedOn = ""
    @SerializedName("email_address")
    @Expose
    var emailAddress = ""
    @SerializedName("telephone_number")
    @Expose
    var telephoneNumber = ""
    @SerializedName("consent_obtain")
    @Expose
    var consentObtain: Int? = null
    @SerializedName("validity_of_consent_upto")
    @Expose
    var validityOfConsentUpto = ""
    @SerializedName("validity_of_consent_ie")
    @Expose
    var validityOfConsentIe = ""
    @SerializedName("hw_of_valid_upto_de")
    @Expose
    var hwOfValidUptoDe = ""


    @SerializedName("generation_industrial_as_consent")
    @Expose
    var generationIndustrialAsConsent = ""
    @SerializedName("generation_industrial_as_consent_cooling")
    @Expose
    var generationIndustrialAsConsentCooling = ""
    @SerializedName("generation_domestic_as_consent")
    @Expose
    var generationDomesticAsConsent = ""
    @SerializedName("generation_industrial_actual")
    @Expose
    var generationIndustrialActual = ""
    @SerializedName("generation_industrial_actual_cooling")
    @Expose
    var generationIndustrialActualCooling = ""
    @SerializedName("generation_domestic_actual")
    @Expose
    var generationDomesticActual = ""


    @SerializedName("treatment_industrial_primary")
    @Expose
    var treatmentIndustrialPrimary: Int? = null
    @SerializedName("industrial_primary_OG_trap")
    @Expose
    var industrialPrimaryOGTrap: Int? = null
    @SerializedName("industrial_primary_screening")
    @Expose
    var industrialPrimaryScreening: Int? = null
    @SerializedName("industrial_primary_neutralization")
    @Expose
    var industrialPrimaryNeutralization: Int? = null
    @SerializedName("industrial_primary_primary_settling")
    @Expose
    var industrialPrimaryPrimarySettling: Int? = null
    @SerializedName("industrial_primary_any_other")
    @Expose
    var industrialPrimaryAnyOther: Int? = null
    @SerializedName("industrial_primary_any_other_text")
    @Expose
    var industrialPrimaryAnyOtherText: String = ""
    @SerializedName("treatment_domestic_septic_tank")
    @Expose
    var treatmentDomesticSepticTank: Int? = null
    @SerializedName("treatment_industrial_secondary")
    @Expose
    var treatmentIndustrialSecondary: Int? = null
    @SerializedName("industrial_secondary_activated_sludge_process")
    @Expose
    var industrialSecondaryActivatedSludgeProcess: Int? = null
    @SerializedName("industrial_secondary_mbbr")
    @Expose
    var industrialSecondaryMbbr: Int? = null
    @SerializedName("industrial_secondary_sbr")
    @Expose
    var industrialSecondarySbr: Int? = null
    @SerializedName("industrial_secondary_trickling_filter")
    @Expose
    var industrialSecondaryTricklingFilter: Int? = null
    @SerializedName("industrial_secondary_any_other")
    @Expose
    var industrialSecondaryAnyOther: Int? = null
    @SerializedName("industrial_secondary_any_other_text")
    @Expose
    var industrialSecondaryAnyOtherText: String = ""
    @SerializedName("treatment_domestic_severage_treatment_plant")
    @Expose
    var treatmentDomesticSeverageTreatmentPlant: Int? = null
    @SerializedName("treatment_domestic_activated_sludge_process")
    @Expose
    var treatmentDomesticActivatedSludgeProcess: Int? = null
    @SerializedName("treatment_domestic_mbbr")
    @Expose
    var treatmentDomesticMbbr: Int? = null
    @SerializedName("treatment_domestic_sbr")
    @Expose
    var treatmentDomesticSbr: Int? = null
    @SerializedName("treatment_domestic_trickling_filter")
    @Expose
    var treatmentDomesticTricklingFilter: Int? = null
    @SerializedName("treatment_domestic_any_other")
    @Expose
    var treatmentDomesticAnyOther: Int? = null
    @SerializedName("treatment_domestic_any_other_text")
    @Expose
    var treatmentDomesticAnyOtherText = ""
    @SerializedName("treatment_industrial_tertiary")
    @Expose
    var treatmentIndustrialTertiary: Int? = null
    @SerializedName("industrial_tertiary_presser_sand_filter")
    @Expose
    var industrialTertiaryPresserSandFilter: Int? = null
    @SerializedName("industrial_tertiary_activated_carbon_filter")
    @Expose
    var industrialTertiaryActivatedCarbonFilter: Int? = null
    @SerializedName("industrial_tertiary_dual_media_filter")
    @Expose
    var industrialTertiaryDualMediaFilter: Int? = null
    @SerializedName("industrial_tertiary_any_other")
    @Expose
    var industrialTertiaryAnyOther: Int? = null
    @SerializedName("industrial_tertiary_any_other_text")
    @Expose
    var industrialTertiaryAnyOtherText = ""
    @SerializedName("treatment_industrial_advanced")
    @Expose
    var treatmentIndustrialAdvanced: Int? = null
    @SerializedName("industrial_advanced_reverse_osmosis")
    @Expose
    var industrialAdvancedReverseOsmosis: Int? = null
    @SerializedName("industrial_advanced_mee")
    @Expose
    var industrialAdvancedMee: Int? = null
    @SerializedName("ultra_filtration")
    @Expose
    var ultraFiltration: Int? = null
    @SerializedName("nano_filtration")
    @Expose
    var nanoFiltration: Int? = null
    @SerializedName("atfd")
    @Expose
    var atfd: Int? = null
    @SerializedName("industrial_advanced_any_other")
    @Expose
    var industrialAdvancedAnyOther: Int? = null
    @SerializedName("industrial_advanced_any_other_text")
    @Expose
    var industrialAdvancedAnyOtherText = ""
    @SerializedName("treatment_observation")
    @Expose
    var treatmentObservation: String = ""
    @SerializedName("tba")
    @Expose
    var treatmentDomesticObservation: String = ""
    @SerializedName("etp_operational")
    @Expose
    var etpOperational: Int? = null
    @SerializedName("stp_operational")
    @Expose
    var stpOperational: Int? = null


    @SerializedName("disposal_industrial_CETP")
    @Expose
    var disposalIndustrialCETP: Int? = null
    @SerializedName("disposal_industrial_CETP_text")
    @Expose
    var disposalIndustrialCETPText = ""
    @SerializedName("disposal_domestic_CETP")
    @Expose
    var disposalDomesticCETP: Int? = null
    @SerializedName("disposal_domestic_CETP_text")
    @Expose
    var disposalDomesticCETPText = ""
    @SerializedName("disposal_industrial_land_gardening")
    @Expose
    var disposalIndustrialLandGardening: Int? = null
    @SerializedName("disposal_industrial_land_gardening_text")
    @Expose
    var disposalIndustrialLandGardeningText = ""
    @SerializedName("disposal_domestic_land_gardening")
    @Expose
    var disposalDomesticLandGardening: Int? = null
    @SerializedName("disposal_domestic_land_gardening_text")
    @Expose
    var disposalDomesticLandGardeningText = ""
    @SerializedName("disposal_industrial_recycle")
    @Expose
    var disposalIndustrialRecycle: Int? = null
    @SerializedName("disposal_industrial_recycle_text")
    @Expose
    var disposalIndustrialRecycleText = ""
    @SerializedName("disposal_domestic_recycle")
    @Expose
    var disposalDomesticRecycle: Int? = null
    @SerializedName("disposal_domestic_recycle_text")
    @Expose
    var disposalDomesticRecycleText = ""
    @SerializedName("disposal_industrial_local_body_sewage")
    @Expose
    var disposalIndustrialLocalBodySewage: Int? = null
    @SerializedName("disposal_industrial_local_body_sewage_text")
    @Expose
    var disposalIndustrialLocalBodySewageText = ""
    @SerializedName("disposal_domestic_local_body_sewage")
    @Expose
    var disposalDomesticLocalBodySewage: Int? = null
    @SerializedName("disposal_domestic_local_body_sewage_text")
    @Expose
    var disposalDomesticLocalBodySewageText = ""
    @SerializedName("disposal_industrial_any_other")
    @Expose
    var disposalIndustrialAnyOther: Int? = null
    @SerializedName("disposal_industrial_any_other_text_remarks")
    @Expose
    var disposalIndustrialAnyOtherTextRemarks = ""
    @SerializedName("disposal_industrial_any_other_text")
    @Expose
    var disposalIndustrialAnyOtherText = ""
    @SerializedName("disposal_domestic_any_other")
    @Expose
    var disposalDomesticAnyOther: Int? = null
    @SerializedName("disposal_domestic_any_other_text_remarks")
    @Expose
    var disposalDomesticAnyOtherTextRemarks = ""
    @SerializedName("disposal_domestic_any_other_text")
    @Expose
    var disposalDomesticAnyOtherText = ""
    @SerializedName("disposal_industrial_total")
    @Expose
    var disposalIndustrialTotal: Int? = null
    @SerializedName("disposal_domestic_total")
    @Expose
    var disposalDomesticTotal: Int? = null
    @SerializedName("disposal_observation")
    @Expose
    var disposalObservation = ""
    @SerializedName("disposal_industrial_as_per_consent")
    @Expose
    var disposalIndustrialAsPerConsent = ""
    @SerializedName("disposal_domestic_as_per_consent")
    @Expose
    var disposalDomesticAsPerConsent = ""
    @SerializedName("operation_and_maintainance_insus")
    @Expose
    var operationAndMaintainanceInsus = ""
    @SerializedName("operation_and_maintainance_domestic")
    @Expose
    var operationAndMaintainanceDomestic = ""


    @SerializedName("omsw_applicable")
    @Expose
    var omswApplicable: Int? = null
    @SerializedName("omsw_installed")
    @Expose
    var omswInstalled: Int? = null
    @SerializedName("omsw_cpcb")
    @Expose
    var omswCpcb: Int? = null
    @SerializedName("omsw_mpcb")
    @Expose
    var omswMpcb: Int? = null
    @SerializedName("remote_cal_applicable_water")
    @Expose
    var remoteCalApplicableWater: Int? = null
    @SerializedName("sensor_placed_water")
    @Expose
    var sensorPlacedWater: Int? = null


    @SerializedName("electrict_meter_provided")
    @Expose
    var electrictMeterProvided: Int? = null
    @SerializedName("electrict_meter_reading")
    @Expose
    var electrictMeterReading: Int? = null


    @SerializedName("date_of_collection_indus")
    @Expose
    var dateOfCollectionIndus = ""
    @SerializedName("date_of_collection_domestic")
    @Expose
    var dateOfCollectionDomestic = ""
    @SerializedName("payment_details_indus")
    @Expose
    var paymentDetailsIndus = ""
    @SerializedName("payment_details_domestic")
    @Expose
    var paymentDetailsDomestic = ""
    @SerializedName("payment_details_indus_amount")
    @Expose
    var paymentDetailsIndusAmount = ""
    @SerializedName("payment_details_domestic_amount")
    @Expose
    var paymentDetailsDomesticAmount = ""
    @SerializedName("payment_details_indus_date")
    @Expose
    var paymentDetailsIndusDate = ""
    @SerializedName("payment_details_domestic_date")
    @Expose
    var paymentDetailsDomesticDate = ""
    @SerializedName("jvs_sample_collected_for_water")
    @Expose
    var jvsSampleCollectedForWater: Int? = null


    @SerializedName("air_pollution_observation")
    @Expose
    var airPollutionObservation: String? = null


    @SerializedName("omsa_applicable")
    @Expose
    var omsaApplicable: Int? = null
    @SerializedName("omsa_installed")
    @Expose
    var omsaInstalled: Int? = null
    @SerializedName("omsa_cpcb")
    @Expose
    var omsaCpcb: Int? = null
    @SerializedName("omsa_mpcb")
    @Expose
    var omsaMpcb: Int? = null
    @SerializedName("remote_cal_applicable")
    @Expose
    var remoteCalApplicable: Int? = null
    @SerializedName("sensor_placed")
    @Expose
    var sensorPlaced: Int? = null
    @SerializedName("stack_facility_exist")
    @Expose
    var stackFacilityExist: Int? = null
    @SerializedName("cal_fac_exist")
    @Expose
    var calFacExist: Int? = null


    @SerializedName("omsam_applicable")
    @Expose
    var omsamApplicable: Int? = null
    @SerializedName("omsam_installed")
    @Expose
    var omsamInstalled: Int? = null
    @SerializedName("omsam_cpcb")
    @Expose
    var omsamCpcb: Int? = null
    @SerializedName("omsam_mpcb")
    @Expose
    var omsamMpcb: Int? = null
    @SerializedName("jvs_sample_collected_for_air")
    @Expose
    var jvsSampleCollectedForAir: Int? = null
    @SerializedName("jvs_observation")
    @Expose
    var jvsObservation: String? = null


    @SerializedName("tree_plantation_plot_area")
    @Expose
    var treePlantationPlotArea: String? = null
    @SerializedName("tree_plantation_built_area")
    @Expose
    var treePlantationBuiltArea: String? = null
    @SerializedName("tree_plantation_green_belt_area")
    @Expose
    var treePlantationGreenBeltArea: String? = null
    @SerializedName("tree_plantation_plantation_no")
    @Expose
    var treePlantationPlantationNo: String? = null
    @SerializedName("tree_plantation_proposed_plantation")
    @Expose
    var treePlantationProposedPlantation: String? = null


    @SerializedName("hw_annual_return_date")
    @Expose
    var hwAnnualReturnDate: String? = null
    @SerializedName("env_statement_report")
    @Expose
    var envStatementReport: String? = null


    @SerializedName("action_initiated_date")
    @Expose
    var actionInitiatedDate: String? = null
    @SerializedName("special_compliance")
    @Expose
    var specialCompliance: String? = null


    @SerializedName("bg_imposed")
    @Expose
    var bgImposed: String? = null
    @SerializedName("bg_imposed_against")
    @Expose
    var bgImposedAgainst: String? = null
    @SerializedName("bg_imposed_number")
    @Expose
    var bgImposedNumber: String? = null


    @SerializedName("additional_info")
    @Expose
    var additionalInfo: String? = null
    @SerializedName("legal_action_unit_complied")
    @Expose
    var legalActionUnitComplied: Int? = null
}

//Production Details Report data
class RoutineReportProduct : BaseObservable() {
    @SerializedName("product_name")
    @Expose
    var productName = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.productName)

        }
    @SerializedName("product_quantity")
    @Expose
    var productQuantity = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.productQuantity)
        }
    @SerializedName("product_uom")
    @Expose
    var productUom: String? = null
    @SerializedName("product_quantity_actual")
    @Expose
    var productQuantityActual = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.productQuantityActual)
        }
    @SerializedName("product_uom_actual")
    @Expose
    var productUomActual: String? = null
}

class JvsSampleCollectedAirSource : BaseObservable() {
    @SerializedName("name_of_source")
    @Expose
    var nameOfSource: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.nameOfSource)
        }
    @SerializedName("jvs_air_source_parameter")
    @Expose
    var jvsAirSourceParameter: ArrayList<String> = arrayListOf()
    @SerializedName("jvs_air_source_std_prescribed")
    @Expose
    var jvsAirSourceStdPrescribed: ArrayList<String> = arrayListOf()

    var ambientAirChild: ArrayList<AmbientAirChild> = arrayListOf()
}

class AmbientAirChild : BaseObservable() {
    var parameter = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.parameter)
        }
    var prescribedValue = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.prescribedValue)
        }
}

class JvsSampleCollectedWaterSource : BaseObservable() {
    @SerializedName("name_of_source")
    @Expose
    var nameOfSource = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.nameOfSource)
        }
    @SerializedName("jvs_water_source_parameter")
    @Expose
    var jvsWaterSourceParameter: ArrayList<String> = arrayListOf()
    @SerializedName("jvs_water_source_std_prescribed")
    @Expose
    var jvsWaterSourceStdPrescribed: ArrayList<String> = arrayListOf()

    var lastJvsChild = arrayListOf<LastJVSChild>()
}

class LastJVSChild : BaseObservable() {
    var parameter = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.parameter)
        }
    var prescribedValue = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.prescribedValue)
        }
}

class RoutineReportAirPollution : BaseObservable() {
    @SerializedName("air_pollution_source")
    @Expose
    var airPollutionSource: String? = null
    @SerializedName("air_pollution_source_other")
    @Expose
    var airPollutionSourceOther: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.airPollutionSourceOther)
        }
    @SerializedName("air_pollution_type")
    @Expose
    var airPollutionType: String? = null
    @SerializedName("air_pollution_fuel_name")
    @Expose
    var airPollutionFuelName: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.airPollutionFuelName)
        }
    @SerializedName("air_pollution_fuel_quantity")
    @Expose
    var airPollutionFuelQuantity: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.airPollutionFuelQuantity)
        }
    @SerializedName("air_pollution_fuel_unit")
    @Expose
    var airPollutionFuelUnit: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.airPollutionFuelUnit)
        }
    @SerializedName("air_pollution_pollutants")
    @Expose
    var airPollutionPollutants: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.airPollutionPollutants)
        }
    @SerializedName("air_pollution_mech_dust_collector")
    @Expose
    var airPollutionMechDustCollector: Int? = null
    @SerializedName("air_pollution_cyclone_dust_collector")
    @Expose
    var airPollutionCycloneDustCollector: Int? = null
    @SerializedName("air_pollution_multi_dust_collector")
    @Expose
    var airPollutionMultiDustCollector: Int? = null
    @SerializedName("air_pollution_fabric_bag_filter")
    @Expose
    var airPollutionFabricBagFilter: Int? = null
    @SerializedName("air_pollution_package_tower")
    @Expose
    var airPollutionPackageTower: Int? = null
    @SerializedName("air_pollution_venturi_scrubber")
    @Expose
    var airPollutionVenturiScrubber: Int? = null
    @SerializedName("air_pollution_electro_static")
    @Expose
    var airPollutionElectroStatic: Int? = null
    @SerializedName("air_pollution_no_provision")
    @Expose
    var airPollutionNoProvision: Int? = null
    @SerializedName("air_pollution_any_other")
    @Expose
    var airPollutionAnyOther: Int? = null
    @SerializedName("air_pollution_stack_height")
    @Expose
    var airPollutionStackHeight: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.airPollutionStackHeight)
        }
}

class RoutineReportBankDetail : BaseObservable() {
    @SerializedName("bank_guarantee_imposed_for")
    @Expose
    var bankGuaranteeImposedFor: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.bankGuaranteeImposedFor)
        }
    @SerializedName("bank_submitted")
    @Expose
    var bankSubmitted: String = ""
    @SerializedName("bank_guarented_no")
    @Expose
    var bankGuarentedNo: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.bankGuarentedNo)
        }
    @SerializedName("date_of_guarantee")
    @Expose
    var dateOfGuarantee: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.dateOfGuarantee)
        }
    @SerializedName("date_of_validity")
    @Expose
    var dateOfValidity: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.dateOfValidity)
        }
}

class RoutineReportHazardousWaste : BaseObservable() {
    @SerializedName("hazardous_waste_category_name")
    @Expose
    var hazardousWasteCategoryName: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.hazardousWasteCategoryName)
        }

    @SerializedName("hazardous_waste_quantity")
    @Expose
    var hazardousWasteQuantity: Int? = null
    var hazardousWasteQuantityString: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.hazardousWasteQuantityString)
            hazardousWasteQuantity = try {
                hazardousWasteQuantityString!!.toInt()
            } catch (e: Exception) {
                0
            }
        }

    @SerializedName("hw_disposal_method")
    @Expose
    var hwDisposalMethod: Int? = null
    var hwDisposalMethodString: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.hwDisposalMethodString)
            hwDisposalMethod = try {
                hwDisposalMethodString!!.toInt()
            } catch (e: Exception) {
                0
            }
        }
    @SerializedName("hw_actual_disposal")
    @Expose
    var hwActualDisposal: Int? = null
    var hwActualDisposalString: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.hwActualDisposalString)
            hwActualDisposal = try {
                hwActualDisposalString!!.toInt()
            } catch (e: Exception) {
                0
            }
        }
    @SerializedName("hw_form_disposal")
    @Expose
    var hwFormDisposal: Int? = null
    var hwFormDisposalString: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.hwFormDisposalString)
            hwFormDisposal = try {
                hwFormDisposalString!!.toInt()
            } catch (e: Exception) {
                0
            }
        }
    @SerializedName("hw_form_cswtsdf")
    @Expose
    var hwFormCswtsdf: Int? = null
    var hwFormCswtsdfString: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.hwFormCswtsdfString)
            hwFormCswtsdf = try {
                hwFormCswtsdfString!!.toInt()
            } catch (e: Exception) {
                0
            }
        }
    @SerializedName("hw_form_co_processing")
    @Expose
    var hwFormCoProcessing: Int? = null
    var hwFormCoProcessingString: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.hwFormCoProcessingString)
            hwFormCoProcessing = try {
                hwFormCoProcessingString!!.toInt()
            } catch (e: Exception) {
                0
            }
        }
    @SerializedName("hw_disposed_actualuser")
    @Expose
    var hwDisposedActualuser: Int? = null
    var hwDisposedActualuserString: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.hwDisposedActualuserString)
            hwDisposedActualuser = try {
                hwDisposedActualuserString!!.toInt()
            } catch (e: Exception) {
                0
            }
        }
    @SerializedName("hw_disposal_quantity")
    @Expose
    var hwDisposalQuantity: Int? = null
    var hwDisposalQuantityString: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.hwDisposalQuantityString)
            hwDisposalQuantity = try {
                hwDisposalQuantityString!!.toInt()
            } catch (e: Exception) {
                0
            }
        }
    @SerializedName("hw_disposal_date")
    @Expose
    var hwDisposalDate: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.hwDisposalDate)
        }
    @SerializedName("hw_disposal_quantity_unit")
    @Expose
    var hwDisposalQuantityUnit: Int? = null
}

class RoutineReportNonHazardousWaste : BaseObservable() {
    @SerializedName("nhw_waste_name")
    @Expose
    var nhwWasteName: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.nhwWasteName)
        }
    @SerializedName("nhw_quantity")
    @Expose
    var nhwQuantity: Int? = null
    var nhwQuantityString: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.nhwQuantityString)
            nhwQuantity = try {
                nhwQuantityString!!.toInt()
            } catch (e: Exception) {
                0
            }
        }
    @SerializedName("nhw_disposal_method")
    @Expose
    var nhwDisposalMethod: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.nhwDisposalMethod)
        }
    @SerializedName("nhw_disposal_date")
    @Expose
    var nhwDisposalDate: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.nhwDisposalDate)
        }
    @SerializedName("nhw_disposal_quantity")
    @Expose
    var nhwDisposalQuantity: Int? = null
    var nhwDisposalQuantityString: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.nhwDisposalQuantityString)
            nhwDisposalQuantity = try {
                nhwDisposalQuantityString!!.toInt()
            } catch (e: Exception) {
                0
            }
        }
    @SerializedName("nhw_actualdisposal")
    @Expose
    var nhwActualdisposal: Int? = null
    var nhwActualdisposalString: String? = null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.nhwActualdisposalString)
            nhwActualdisposal = try {
                nhwActualdisposalString!!.toInt()
            } catch (e: Exception) {
                0
            }
        }
    @SerializedName("nhw_disposal_quantity_unit")
    @Expose
    var nhwDisposalQuantityUnit: Int? = null
}