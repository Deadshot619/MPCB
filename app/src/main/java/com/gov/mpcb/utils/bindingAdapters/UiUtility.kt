package com.gov.mpcb.utils.bindingAdapters

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
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
fun bindVisitStatus(textView: AppCompatTextView, visitModel: MyVisitModel?){
    visitModel?.let {
        if (visitModel.visitStatus == "Visited"){
            textView.setBackgroundResource(R.drawable.shape_industry_background_green)
        }else{
            textView.setBackgroundResource(R.drawable.shape_industry_background_red)
        }
    }
}

/**
 * This adapter is used to setText to userassigned names text field in [R.layout.item_task]
 */
@BindingAdapter("userAssignedNames")
fun bindUserAssignedNames(textView: AppCompatTextView, userNames: List<UsersAssignedName>){
    if (userNames.isNotEmpty()){
        var username = ""
        for (i in userNames)
            username = username + i.nameOfOfficer + "\n"
        textView.text = username
    }else{
        textView.text = ""
    }
}

/**
 * This adapter shows/hides the progressBar depending on [LoadingStatus]
 */
@BindingAdapter("statusProgressBar")
fun bindProgressBar(progressBar: ProgressBar, status: LoadingStatus?){
    when(status){
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
 * If [isSurpriseInspection] is 1, then display text as 'Surprise Inspection' else 'Randomised Inspection'
 */
@BindingAdapter("isSurpriseInspection")
fun bindSurpriseInspection(textView: TextView, isSurpriseInspection: Int){
    if (isSurpriseInspection == 1)
        textView.text = "Surprise Inspection"
    else
        textView.text = "Randomised Inspection"
}

/**
 * If visited_on text is present, then display text as 'Yes' else 'No'
 */
@BindingAdapter("isVisitConducted")
fun bindVisitedOn(textView: TextView, visited_on: String?){
    if (visited_on.isNullOrEmpty())
        textView.text = "No"
    else
        textView.text = "Yes"

}

/**
 * If download link is available, show download button, else hide it
 */
@BindingAdapter("isDownloadLinkAvailable")
fun bindDownloadReportBtn(textView: TextView, downloadLink: String?){
    if (downloadLink.isNullOrEmpty())
        textView.visibility = View.GONE
    else
        textView.visibility = View.VISIBLE
}