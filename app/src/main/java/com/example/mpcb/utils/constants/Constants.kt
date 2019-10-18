package com.example.mpcb.utils.constants

import android.content.Context
import com.example.mpcb.R

class Constants {
    companion object {

        //Shared Preference Constants
        const val myPrefK: String = "PreferenceMPCB"

        const val mloginKey: String = "login_key"
        const val mloginUserKey: String = "user_login_name_key"
        const val USER: String = "user"

        const val REPORTS_PAGE_KEY: String = "reports_page_key"
        const val VISIT_ITEM_KEY: String = "visit_item_key"
        const val VISIT_REPORT_ID: String = "visit_report_id"

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
    }


}