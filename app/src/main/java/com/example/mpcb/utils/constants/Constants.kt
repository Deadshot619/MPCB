package com.example.mpcb.utils.constants

import android.content.Context
import com.example.mpcb.R
import java.text.SimpleDateFormat
import java.util.*

class Constants {
    companion object {

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

        const val VISIT_ID: String = "visit_id"
        const val INDUS_IMIS_ID: String = "indus_imis_id"

        val CATEGORY_LIST = arrayListOf(
            "Select one",
            "17 Category",
            "Common Facilities",
            "Red(LSI)",
            "Red(MSI)",
            "Red(SSI)",
            "Orange(LSI)",
            "Orange(MSI)",
            "Orange(SSI)",
            "Green(LSI)",
            "Green(MSI)",
            "Green(SSI)",
            "Duplicate",
            "Closed",
            "HCE > 200",
            "HCE(100-200)",
            "HCE(50-100)",
            "HCE < 50",
            "Not in my region",
            "Sugar with distillery",
            "Only distillery",
            "Common Facilities(CBMWTSDF)-Biomedical wastes",
            "CETP(Common Effluent Treatment Plant)",
            "Local Bodies(STP/MSW)",
            "HW-Recyclers/Re-processors"
        )

        val UNIT_LIST = arrayListOf(
            "Select uom",
            "--NA--",
            "Beam/M",
            "Box",
            "Brass/A",
            "Brass/D",
            "Brass/M",
            "Gel.",
            "Kg",
            "kg/Annum",
            "kg/Cycle",
            "Kg/Day",
            "Kg/H",
            "Kg/M",
            "KL/A",
            "KL/D",
            "KL/M",
            "KLtr.",
            "Lit/Day",
            "Ltr/A",
            "Ltr/Hr",
            "Ltr/M",
            "Ltrs",
            "M/Day",
            "m/month",
            "m3/day",
            "m3/hr",
            "m3/month",
            "mg/kg",
            "mg/l",
            "MLD",
            "MT",
            "MT/A",
            "MT/Day",
            "Mt/Hr",
            "MT/M",
            "Mtrs/Day",
            "Mtrs/M",
            "Mtrs/Y",
            "MW",
            "No.",
            "No/Cycl",
            "No/D",
            "No/M",
            "Nos./Y",
            "Pcs/A",
            "Pcs/M",
            "Pieces",
            "Qnt/M",
            "Qnt/Y",
            "Rim",
            "Rooms",
            "SqFeet/D",
            "SqFeet/M",
            "SqFeet/Y",
            "Ton/D",
            "Ton/M",
            "Ton/Y",
            "Yes.",
            "CMD",
            "Ton/Ton",
            "Mwh"
        )

        val AIR_POLLUTION_LIST =
            arrayListOf(
                "Boiler",
                "Thermopack",
                "Thermicfluid Heater",
                "Furnace process vent",
                "Any other"
            )

        val AMBIENT_AIR_PARAM_LIST =
            arrayListOf("TPM (mg/Nm3)", "SO2 (mg/m3)", "Acid Mist (mg/Nm3)")

        val JVS_PARAM_LIST =
            arrayListOf(
                "pH",
                "BOD (mg/l)",
                "COD (mg/l)",
                "SS (mg/l)",
                "O&G (mg/l)",
                "TDS (mg/l)",
                "Chloride (mg/l)",
                "Sulphate (mg/l)"
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
            val reportHashMap = mutableMapOf<Int, String>()
            reportHashMap.put(REPORT_1, context.getString(R.string.report_1))
            reportHashMap.put(REPORT_2, context.getString(R.string.report_2))
            reportHashMap.put(REPORT_3, context.getString(R.string.report_3))
            reportHashMap.put(REPORT_4, context.getString(R.string.report_4))
            reportHashMap.put(REPORT_5, context.getString(R.string.report_5))
            reportHashMap.put(REPORT_6, context.getString(R.string.report_6))
            reportHashMap.put(REPORT_7, context.getString(R.string.report_7))
            reportHashMap.put(REPORT_8, context.getString(R.string.report_8))
            reportHashMap.put(REPORT_9, context.getString(R.string.report_9))
            reportHashMap.put(REPORT_10, context.getString(R.string.report_10))
            reportHashMap.put(REPORT_11, context.getString(R.string.report_11))
            reportHashMap.put(REPORT_12, context.getString(R.string.report_12))
            reportHashMap.put(REPORT_13, context.getString(R.string.report_13))
            reportHashMap.put(REPORT_14, context.getString(R.string.report_14))
            reportHashMap.put(REPORT_15, context.getString(R.string.report_15))
            reportHashMap.put(REPORT_16, context.getString(R.string.report_16))
            reportHashMap.put(REPORT_17, context.getString(R.string.report_17))
            reportHashMap.put(REPORT_18, context.getString(R.string.add_info))

            return reportHashMap.get(reportKey)!!
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
    }


}