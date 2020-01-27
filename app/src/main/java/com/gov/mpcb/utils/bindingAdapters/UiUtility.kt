package com.gov.mpcb.utils.bindingAdapters

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.gov.mpcb.R
import com.gov.mpcb.network.response.MyVisitModel
import com.gov.mpcb.network.response.UsersAssignedName

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