package com.example.mpcb.utils

import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.example.mpcb.base.MPCBApp
import com.google.android.material.textfield.TextInputLayout


fun AppCompatActivity.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showMessage(message: String) {
    Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    view?.let {
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}

@BindingAdapter("fieldName")
fun TextInputLayout.hideError(fieldName: String) {
    if (!TextUtils.isEmpty(fieldName)) {
        this.error = null
    }
}

@BindingAdapter("visibleGone")
fun View.showHide(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

fun isNetworkAvailable(): Boolean {
    val cm = MPCBApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork.isConnected
}