package com.gov.mpcb.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputLayout
import com.gov.mpcb.base.BaseActivity
import com.gov.mpcb.base.MPCBApp
import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat

var toast: Toast? = null

fun AppCompatActivity.showMessage(message: String) {
    if (toast != null){
        toast?.cancel()
    }
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast?.show()
}

fun Fragment.showMessage(message: String) {
    if (toast != null){
        toast?.cancel()
    }
    toast = Toast.makeText(this.activity, message, Toast.LENGTH_SHORT)
    toast?.show()
}


inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

fun Fragment.addFragment(fragment: Fragment, addToBackStack: Boolean, bundle: Bundle) {
    val activity = this.activity as BaseActivity<*, *>
    activity.addFragment(fragment, addToBackStack, bundle)
}

//Method to remove fragment from backstack
fun Fragment.removeFragment(fragment: Fragment){
    val activity = this.activity as BaseActivity<*, *>
    activity.removeFragment(fragment)
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

@BindingAdapter("parseDate")
fun parseDate(textView: AppCompatTextView, date: String?) {
    try {
        val date = SimpleDateFormat("MMM dd yyyy HH:mm:ss").parse(date)
        val newDate = SimpleDateFormat("dd/MM/yyyy").format(date)
        textView.text = newDate
    } catch (e: Exception) {
        textView.text = ""
    }
}

fun isNetworkAvailable(): Boolean {
    val cm = MPCBApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork?.isConnected ?: false
}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun String.parseToInt(): Int {
    return try {
        this.toInt()
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

/**
 * Method to parse a String into Double
 */
fun String.parseToDouble(): Double {
    return try {
        this.toDouble()
    } catch (e: Exception) {
        e.printStackTrace()
        0.0
    }
}