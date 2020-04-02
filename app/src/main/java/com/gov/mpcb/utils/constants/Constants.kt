package com.gov.mpcb.utils.constants

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.gov.mpcb.R
import com.gov.mpcb.databinding.ToolbarBinding
import java.text.SimpleDateFormat
import java.util.*

class Constants {
    companion object {

        //this value will be used to check if the app is launched for first time or not
        const val IS_FIRST_TIME = "is_first_time"

        const val RELOAD_KEY = "reload_key"
        const val RELOAD_VALUE = "reload_value"

        const val IMAGE_PATH: String = "IMAGE_PATH"
        const val LAT_VALUE: String = "LAT_VALUE"
        const val LONG_VALUE: String = "LONG_VALUE"
        //Shared Preference Constants
        const val myPrefK: String = "PreferenceMPCB"

        const val mloginKey: String = "login_key"
        const val USER: String = "user"
        const val REPORT_KEY: String = "report_key"

        const val REPORTS_PAGE_KEY: String = "reports_page_key"
        const val VISIT_ITEM_KEY: String = "visit_item_key"
        const val VISIT_REPORT_ID: String = "visit_report_id"
        //Constant to maintain state of whether the Visit status if Visited or not
        const val VISIT_STATUS: String = "visit_status"
        //Constant to maintain state of whether the user submitted the form successfully
        const val FORM_COMPLETE_STATUS: String = "form_complete_status"

        //Constant to maintain firebase notification token
        const val FIREBASE_TOKEN = "firebase_notification_token"

        //Constant for Additional Info Text
        const val ADDITIONAL_INFO_TEXT = "additional_info_text"

    /*  Surprise Inspection   */
        //SI data
        const val SI_DATA = "si_data"

        //added by me data
        const val ADDED_BY_ME = "added_by_me_data"

        //Verified surprise inspection data
        const val VERIFIED_SURPRISE_INSPECTION_DATA = "verified_surprise_inspection_data"

        //This value will be used to send industry data from 'IndustryList Fragment' to
        //'ApplyForSurpriseInspectionFragment'
        const val VIEW_AVAILABLE_INDUSTRY_DATA = "view_available_industries_data"

        /**
         * This value is used to save report visit data in shared pref temporarily when
         * already submitted Visit report is to be viewed.
         */
        const val TEMP_VISIT_REPORT_DATA: String = "temp_visit_report_data"


        const val VISIT_ID: String = "visit_id"
        const val INDUS_IMIS_ID: String = "indus_imis_id"

        //These values will be used in MonthYearPickerDialog
        enum class CalendarConstant {
            DASHBOARD, MY_VISIT
        }

        val CATEGORY_LIST = mapOf(
            1 to "17 Category",
            2 to "Common Facilities",
            6 to "Red(LSI)",
            7 to "Red(MSI)",
            8 to "Red(SSI)",
            9 to "Orange(LSI)",
            10 to "Orange(MSI)",
            11 to "Orange(SSI)",
            12 to "Green(LSI)",
            13 to "Green(MSI)",
            14 to "Green(SSI)",
            15 to "Duplicate",
            16 to "Closed",
            17 to "HCE > 200",
            18 to "HCE(100-200)",
            19 to "HCE(50-100)",
            20 to "HCE < 50",
            21 to "Not in my region",
            22 to "Sugar with distillery",
            23 to "Only distillery",
            24 to "Common Facilities(CBMWTSDF)-Biomedical wastes",
            25 to "CETP(Common Effluent Treatment Plant)",
            26 to "Local Bodies(STP/MSW)",
            27 to "HW-Recyclers/Re-processors"
        )

        /**
         * This field is used to map dropdown data according to its position
         */
        val UNIT_LIST = mapOf(
            0 to "Select uom",
            1 to "--NA--",
            2 to "Beam/M",
            3 to "Box",
            4 to "Brass/A",
            5 to "Brass/D",
            6 to "Brass/M",
            7 to "Gel.",
            8 to "Kg",
            9 to "kg/Annum",
            10 to "kg/Cycle",
            11 to "Kg/Day",
            12 to "Kg/Hr",
            13 to "Kg/M",
            14 to "KL/A",
            15 to "KL/D",
            16 to "KL/M",
            17 to "KLtr.",
            18 to "Lit/Day",
            19 to "Ltr/A",
            20 to "Ltr/Hr",
            21 to "Ltr/M",
            22 to "Ltrs",
            23 to "M/Day",
            24 to "m/month",
            25 to "m3/day",
            26 to "m3/hr",
            27 to "m3/month",
            28 to "mg/kg",
            29 to "mg/l",
            30 to "MLD",
            31 to "MT",
            32 to "MT/A",
            33 to "MT/Day",
            34 to "Mt/Hr",
            35 to "MT/M",
            36 to "Mtrs/Day",
            37 to "Mtrs/M",
            38 to "Mtrs/Y",
            39 to "MW",
            40 to "No.",
            41 to "No/Cycl",
            42 to "No/D",
            43 to "No/M",
            44 to "Nos./Y",
            45 to "Pcs/A",
            46 to "Pcs/M",
            47 to "Pieces",
            48 to "Qnt/M",
            49 to "Qnt/Y",
            50 to "Rim",
            51 to "Rooms",
            52 to "SqFeet/D",
            53 to "SqFeet/M",
            54 to "SqFeet/Y",
            55 to "Ton/D",
            56 to "Ton/M",
            57 to "Ton/Y",
            58 to "Yes.",
            59 to "CMD",
            60 to "Ton/Ton",
            61 to "Mwh"
        )

        /**
         * This field, maps the values of Unit_List to the actual keys of that values.
         * These keys will then be sent to server
         */
        val UNIT_LIST1 = mapOf(
            "0" to "Select uom",
            "1" to "--NA--",
            "00000055" to "Beam/M",
            "00000006" to "Box",
            "00000035" to "Brass/A",
            "00000039" to "Brass/D",
            "00000034" to "Brass/M",
            "00000004" to "Gel.",
            "00000032" to "Kg",
            "00000033" to "kg/Annum",
            "00000056" to "kg/Cycle",
            "00000018" to "Kg/Day",
            "00000052" to "Kg/Hr",
            "00000020" to "Kg/M",
            "00000013" to "KL/A",
            "00000012" to "KL/D",
            "00000015" to "KL/M",
            "00000002" to "KLtr.",
            "00000026" to "Lit/Day",
            "00000028" to "Ltr/A",
            "00000053" to "Ltr/Hr",
            "00000027" to "Ltr/M",
            "00000010" to "Ltrs",
            "00000022" to "M/Day",
            "00000024" to "m/month",
            "00000037" to "m3/day",
            "00000036" to "m3/hr",
            "00000038" to "m3/month",
            "00000008" to "mg/kg",
            "00000009" to "mg/l",
            "00000019" to "MLD",
            "00000007" to "MT",
            "00000014" to "MT/A",
            "00000023" to "MT/Day",
            "00000054" to "Mt/Hr",
            "00000011" to "MT/M",
            "00000043" to "Mtrs/Day",
            "00000044" to "Mtrs/M",
            "00000045" to "Mtrs/Y",
            "00000021" to "MW",
            "00000001" to "No.",
            "00000057" to "No/Cycl",
            "00000017" to "No/D",
            "00000016" to "No/M",
            "00000051" to "Nos./Y",
            "00000030" to "Pcs/A",
            "00000029" to "Pcs/M",
            "00000031" to "Pieces",
            "00000046" to "Qnt/M",
            "00000047" to "Qnt/Y",
            "00000005" to "Rim",
            "00000025" to "Rooms",
            "00000040" to "SqFeet/D",
            "00000041" to "SqFeet/M",
            "00000042" to "SqFeet/Y",
            "00000049" to "Ton/D",
            "00000048" to "Ton/M",
            "00000050" to "Ton/Y",
            "00000003" to "Yes.",
            "00010035" to "CMD",
            "10000013" to "Ton/Ton",
            "20000013" to "Mwh"
        )

        val UNIT_LIST2 = mapOf(
            0 to mapOf("0" to "Select uom"),
            1 to mapOf("1" to "--NA--"),
            2 to mapOf("00000055" to "Beam/M"),
            3 to mapOf("00000006" to "Box"),
            4 to mapOf("00000035" to "Brass/A"),
            5 to mapOf("00000039" to "Brass/D"),
            6 to mapOf("00000034" to "Brass/M"),
            7 to mapOf("00000004" to "Gel."),
            8 to mapOf("00000032" to "Kg"),
            9 to mapOf("00000033" to "kg/Annum"),
            10 to mapOf("00000056" to "kg/Cycle"),
            11 to mapOf("00000018" to "Kg/Day"),
            12 to mapOf("00000052" to "Kg/Hr"),
            13 to mapOf("00000020" to "Kg/M"),
            14 to mapOf("00000013" to "KL/A"),
            15 to mapOf("00000012" to "KL/D"),
            16 to mapOf("00000015" to "KL/M"),
            17 to mapOf("00000002" to "KLtr."),
            18 to mapOf("00000026" to "Lit/Day"),
            19 to mapOf("00000028" to "Ltr/A"),
            20 to mapOf("00000053" to "Ltr/Hr"),
            21 to mapOf("00000027" to "Ltr/M"),
            22 to mapOf("00000010" to "Ltrs"),
            23 to mapOf("00000022" to "M/Day"),
            24 to mapOf("00000024" to "m/month"),
            25 to mapOf("00000037" to "m3/day"),
            26 to mapOf("00000036" to "m3/hr"),
            27 to mapOf("00000038" to "m3/month"),
            28 to mapOf("00000008" to "mg/kg"),
            29 to mapOf("00000009" to "mg/l"),
            30 to mapOf("00000019" to "MLD"),
            31 to mapOf("00000007" to "MT"),
            32 to mapOf("00000014" to "MT/A"),
            33 to mapOf("00000023" to "MT/Day"),
            34 to mapOf("00000054" to "Mt/Hr"),
            35 to mapOf("00000011" to "MT/M"),
            36 to mapOf("00000043" to "Mtrs/Day"),
            37 to mapOf("00000044" to "Mtrs/M"),
            38 to mapOf("00000045" to "Mtrs/Y"),
            39 to mapOf("00000021" to "MW"),
            40 to mapOf("00000001" to "No."),
            41 to mapOf("00000057" to "No/Cycl"),
            42 to mapOf("00000017" to "No/D"),
            43 to mapOf("00000016" to "No/M"),
            44 to mapOf("00000051" to "Nos./Y"),
            45 to mapOf("00000030" to "Pcs/A"),
            46 to mapOf("00000029" to "Pcs/M"),
            47 to mapOf("00000031" to "Pieces"),
            48 to mapOf("00000046" to "Qnt/M"),
            49 to mapOf("00000047" to "Qnt/Y"),
            50 to mapOf("00000005" to "Rim"),
            51 to mapOf("00000025" to "Rooms"),
            52 to mapOf("00000040" to "SqFeet/D"),
            53 to mapOf("00000041" to "SqFeet/M"),
            54 to mapOf("00000042" to "SqFeet/Y"),
            55 to mapOf("00000049" to "Ton/D"),
            56 to mapOf("00000048" to "Ton/M"),
            57 to mapOf("00000050" to "Ton/Y"),
            58 to mapOf("00000003" to "Yes."),
            59 to mapOf("00010035" to "CMD"),
            60 to mapOf("10000013" to "Ton/Ton"),
            61 to mapOf("20000013" to "Mwh")
        )

        val AIR_POLLUTION_LIST = mapOf(
            0 to "Boiler",
            1 to "Thermopack",
            2 to "Thermicfluid Heater",
            3 to "Furnace process vent",
            4 to "Any other"
        )

        val AMBIENT_AIR_PARAM_LIST = mapOf(
            0 to "TPM(mg/Nm3)",
            1 to "SO2(mg/m3)",
            2 to "Acid Mist(mg/Nm3)"
        )

        val JVS_PARAM_LIST = mapOf(
            0 to "pH",
            1 to "BOD(mg/l)",
            2 to "COD(mg/l)",
            3 to "SS(mg/l)",
            4 to "O&G(mg/l)",
            5 to "TDS(mg/l)",
            6 to "Chloride(mg/l)",
            7 to "Sulphate(mg/l)"
        )


        // reports page value
        const val REPORT_1 = 1
        const val REPORT_2 = 2
        const val REPORT_3 = 3
        const val REPORT_4 = 4
        const val REPORT_5 = 5
        const val REPORT_6 = 6
        const val REPORT_7 = 7
        const val REPORT_8 = 8
        const val REPORT_9 = 9
        const val REPORT_10 = 10
        const val REPORT_11 = 11
        const val REPORT_12 = 12
        const val REPORT_13 = 13
        const val REPORT_14 = 14
        const val REPORT_15 = 15
        const val REPORT_16 = 16
        const val REPORT_17 = 17
        const val REPORT_18 = 18


        fun getReportByNo(context: Context, reportKey: Int): String {
            return if(reportKey in 1..18) {
                val reportHashMap = mutableMapOf<Int, String>()
                reportHashMap[REPORT_1] = context.getString(R.string.report_1)
                reportHashMap[REPORT_2] = context.getString(R.string.report_2)
                reportHashMap[REPORT_3] = context.getString(R.string.report_3)
                reportHashMap[REPORT_4] = context.getString(R.string.report_4)
                reportHashMap[REPORT_5] = context.getString(R.string.report_5)
                reportHashMap[REPORT_6] = context.getString(R.string.report_6)
                reportHashMap[REPORT_7] = context.getString(R.string.report_7)
                reportHashMap[REPORT_8] = context.getString(R.string.report_8)
                reportHashMap[REPORT_9] = context.getString(R.string.report_9)
                reportHashMap[REPORT_10] = context.getString(R.string.report_10)
                reportHashMap[REPORT_11] = context.getString(R.string.report_11)
                reportHashMap[REPORT_12] = context.getString(R.string.report_12)
                reportHashMap[REPORT_13] = context.getString(R.string.report_13)
                reportHashMap[REPORT_14] = context.getString(R.string.report_14)
                reportHashMap[REPORT_15] = context.getString(R.string.report_15)
                reportHashMap[REPORT_16] = context.getString(R.string.report_16)
                reportHashMap[REPORT_17] = context.getString(R.string.report_17)
                reportHashMap[REPORT_18] = context.getString(R.string.add_info)

                reportHashMap[reportKey]!!
            } else {
                "Invalid Value"
            }
        }

        /**
         * Method to return a string 'FLAG' that will be used to store flag's status in
         * shared preference
         */
        fun getReportFlag(reportKey: Int): String {
            return when (reportKey) {
                1 -> "FLAG_$REPORT_1"
                2 -> "FLAG_$REPORT_2"
                3 -> "FLAG_$REPORT_3"
                4 -> "FLAG_$REPORT_4"
                5 -> "FLAG_$REPORT_5"
                6 -> "FLAG_$REPORT_6"
                7 -> "FLAG_$REPORT_7"
                8 -> "FLAG_$REPORT_8"
                9 -> "FLAG_$REPORT_9"
                10 -> "FLAG_$REPORT_10"
                11 -> "FLAG_$REPORT_11"
                12 -> "FLAG_$REPORT_12"
                13 -> "FLAG_$REPORT_13"
                14 -> "FLAG_$REPORT_14"
                15 -> "FLAG_$REPORT_15"
                16 -> "FLAG_$REPORT_16"
                17 -> "FLAG_$REPORT_17"
                18 -> "FLAG_$REPORT_18"
                else -> "FLAG_NOT_DEFINED"
            }
        }

        fun getCurrentDate(dateFormat: String): String {
            return try {
                val date = Date()
                SimpleDateFormat(dateFormat, Locale.US).format(date)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        /**
         * This method Enables/Disables the views in  a viewGroup.
         */
        fun disableEnableControls(enable: Boolean, vg: ViewGroup) {
            for (i in 0 until vg.childCount) {
                val child = vg.getChildAt(i)

                //Make TextInputLayput 'not focusable' instead of 'not enable' to make it readable
                if (child.javaClass.name.contains("TextInputLayout"))
                    child.isFocusable = enable
                //need 'Next' & 'Previous' button to be enabled
                else if (child.id != R.id.btnNext && child.id != R.id.btnPrevious)
                    child.isEnabled = enable


                //Hide Add more & Delete button
                if (child.id == R.id.tvAddMore || child.id == R.id.imgDelete)
                    child.visibility = View.GONE

                if (child is ViewGroup) {
                    disableEnableControls(enable, child)
                }
            }
        }

        fun setToolbar(
            toolbarBinding: ToolbarBinding,
            title: String,
            showSearchBar: Boolean = false,
            showCalendar: Boolean = false,
            showBackButton: Boolean = false
        ) {
            toolbarBinding.run {

                //Toolbar title
                txtToolbarTitle.text = title

                //if true, then Display search icon & add click listeners to it
                if (showSearchBar) {
                    //Show Search Icon
                    imgSearch.visibility = View.VISIBLE

                    //Search icon click listener
                    imgSearch.setOnClickListener {
                        //Hide main toolbar
                        mainToolbar.visibility = View.GONE
                        //show searchbar
                        searchbarLayout.visibility = View.VISIBLE

                        //set focus on search bar programmatically
                        searchBar.isIconified = false
                    }

                    //SearchBar click listener
                    searchBar.setOnCloseListener {
                        //show main toolbar
                        mainToolbar.visibility = View.VISIBLE
                        //hide searchbar & clear focus form it
                        searchBar.clearFocus()
                        searchbarLayout.visibility = View.GONE
                        true
                    }
                }

                //If true, show Calendar, else hide it
                if (showCalendar) {
                    imgCalendar.visibility = View.VISIBLE
                } else {
                    imgCalendar.visibility = View.GONE
                }

                //If true, show Back Button, else hide it
                if (showBackButton) {
                    imgBack.visibility = View.VISIBLE
                } else {
                    imgBack.visibility = View.GONE
                }
            }
        }
    }
}