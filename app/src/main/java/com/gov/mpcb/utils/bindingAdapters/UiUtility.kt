package com.gov.mpcb.utils.bindingAdapters

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.gov.mpcb.R
import com.gov.mpcb.network.response.MyVisitModel
import com.gov.mpcb.network.response.UsersAssignedName
import com.gov.mpcb.utils.LoadingStatus

/**
 * This adapter is used to set background drawable in Visit Status textview in
 * [R.layout.item_visit].
 * If VisitStatus is Visited the background set to green, otherwise red.
 */
@BindingAdapter("visitStatus")
fun bindVisitStatus(textView: AppCompatTextView, visitModel: MyVisitModel?) {
    visitModel?.let {
        if (visitModel.visitStatus == "Visited") {
            textView.setBackgroundResource(R.drawable.shape_industry_background_green)
        } else {
            textView.setBackgroundResource(R.drawable.shape_industry_background_red)
        }
    }
}

/**
 * This adapter is used to setText to userassigned names text field in [R.layout.item_task]
 */
@BindingAdapter("userAssignedNames")
fun bindUserAssignedNames(textView: AppCompatTextView, userNames: List<UsersAssignedName>) {
    if (userNames.isNotEmpty()) {
        var username = ""
        for (i in userNames)
            username = username + i.nameOfOfficer + "\n"
        textView.text = username
    } else {
        textView.text = ""
    }
}

/**
 * This adapter shows/hides the progressBar depending on [LoadingStatus]
 */
@BindingAdapter("statusProgressBar")
fun bindProgressBar(progressBar: ProgressBar, status: LoadingStatus?) {
    when (status) {
        LoadingStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
        LoadingStatus.ERROR -> {
            progressBar.visibility = View.GONE
        }
        LoadingStatus.DONE -> {
            progressBar.visibility = View.GONE
        }
    }
}

/**
 * This binding adapter will take [LoadingStatus] & a List as input.
 * When the [LoadingStatus] is [LoadingStatus.DONE] & List is empty or its [LoadingStatus.ERROR], it will show the error text
 */
@BindingAdapter("statusProgress", "isListEmpty")
fun bindIsListEmpty(textView: TextView, status: LoadingStatus, list: List<Any>?) {
    when (status) {
        LoadingStatus.LOADING -> {
            textView.visibility = View.GONE
        }
        LoadingStatus.DONE -> {
            if (list.isNullOrEmpty())
                textView.visibility = View.VISIBLE
        }
        LoadingStatus.ERROR -> {
            textView.visibility = View.VISIBLE
        }
    }
}

/**
 * This method will extract the string before the last occurrence of space("") i.e. extract date from date & time string.
 */
@BindingAdapter("extractDateRemoveTime")
fun bindextractDateRemoveTime(textView: TextView, string: String) {
    textView.text = string.substringBeforeLast(" ")
}


/**
 * If [isSurpriseInspection] is 1, then display text as 'Surprise Inspection' else 'Randomised Inspection'
 */
@BindingAdapter("isSurpriseInspection")
fun bindSurpriseInspection(textView: TextView, isSurpriseInspection: Int) {
    if (isSurpriseInspection == 1)
        textView.text = "Surprise Inspection"
    else
        textView.text = "Randomised Inspection"
}

/**
 * If visited_on text is present, then display text as 'Yes' else 'No'
 */
@BindingAdapter("isVisitConducted")
fun bindVisitedOn(textView: TextView, visited_on: String?) {
    if (visited_on.isNullOrEmpty())
        textView.text = "No"
    else
        textView.text = "Yes"

}

/**
 * If link is available, show button, else hide it
 */
@BindingAdapter("isLinkAvailable")
fun bindIsLinkAvailable(view: View, downloadLink: String?) {
    if (downloadLink.isNullOrEmpty())
        view.visibility = View.GONE
    else
        view.visibility = View.VISIBLE
}

/**
 * This adapter shows/hides the progressBar depending on [LoadingStatus]
 */
@BindingAdapter("layoutVisibility")
fun bindLayoutVisibility(view: View, status: LoadingStatus?) {
    when (status) {
        LoadingStatus.LOADING -> {
            view.visibility = View.INVISIBLE
        }
        LoadingStatus.ERROR -> {
            view.visibility = View.VISIBLE
        }
        LoadingStatus.DONE -> {
            view.visibility = View.VISIBLE
        }
    }
}

/**
 * This binding adapter will be used in [R.layout.item_id_consent] to set the consent payment status of it
 */
@BindingAdapter("idConsentPaymentStatus")
fun bindIdConsentPaymentStatus(chip: Chip, paymentFor: Int){
    chip.text = when(paymentFor){
        0 -> "Consent to Establish"
        1 -> "Consent to Operate"
        else -> "Consent to Renewal"
    }
}

/**
 * This binding adapter will be used in [R.layout.item_id_consent] to set the status of it
 */
@BindingAdapter("idConsentStatus")
fun bindIdConsentStatus(textView: TextView, status: Int){
    textView.text = when(status){
        1 -> "Rejected"
        2 -> "Approved"
        else -> "In Process"
    }
}

/**
 * This binding adapter will be used in [R.layout.item_id_consent] to set the status of it
 */
@BindingAdapter("idAuthStatus")
fun bindIdAuthStatus(textView: TextView, status: Int){
    textView.text = when(status){
        2 -> "Rejected"
        1 -> "Approved"
        else -> "In Process"
    }
}
