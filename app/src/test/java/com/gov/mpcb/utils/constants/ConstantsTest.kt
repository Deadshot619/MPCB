package com.gov.mpcb.utils.constants

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.gov.mpcb.R
import com.gov.mpcb.utils.constants.Constants.Companion.getReportByNo
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ConstantsTest {

    //Variable to hold the application Context
    private val context = ApplicationProvider.getApplicationContext<Context>()

    //If the input is valid value i.e. it is between 1-18,
    //then the string returned should be equivalent to 1 of 18 reports
    @Test
    fun getReportByNo_validValue_returnsCorrectValue() {
        /*      Given/When/Then format      */
        //Given
        val reportNo = Constants.REPORT_18

        //When
        val result = getReportByNo(
            context,
            reportNo
        )

        //Then
        assertEquals(
            context.getString(R.string.add_info),
            result
        )
    }

    //If the input is invalid value i.e. it is not between 1-18,
    //then the string returned should be "Invalid Value"
    @Test
    fun getReportByNo_invalidValue_returnsInvalidValue() {
        val reportNo = 19

        val result = getReportByNo(
            context,
            reportNo
        )

        assertEquals(
            "Invalid Value",
            result
        )
    }
}