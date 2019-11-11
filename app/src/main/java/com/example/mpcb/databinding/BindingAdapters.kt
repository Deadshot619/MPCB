package com.example.mpcb.databinding

import android.graphics.Typeface
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

@BindingAdapter(value = ["font"])
fun setFont(view: TextView, fontPath: String) {
    val typeface = Typeface.createFromAsset(view.context.assets, fontPath)
    view.typeface = typeface
}


@BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
fun bindSpinnerData(spinner: AppCompatSpinner, newSelectedValue: String?, newTextAttrChanged: InverseBindingListener?) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            newTextAttrChanged?.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>) {}
    }
    if (newSelectedValue != null) {
        val pos = (spinner.adapter as ArrayAdapter<String>).getPosition(newSelectedValue)
        spinner.setSelection(pos, true)
    }
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun captureSelectedValue(spinner: Spinner): String {
    return spinner.selectedItem as String
}
