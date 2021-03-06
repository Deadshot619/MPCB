package com.gov.mpcb.utils.validations

import java.util.regex.Pattern

/**
 * This File contains methods to validates the input fields
 */

/**
 * Method to check if email is valid
 */
fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

/**
 * Method to Check if password is valid
 */
fun isPasswordValid(password: String?, confirmPassword: String?): Boolean {
    return password.equals(confirmPassword)
}

/**
 * Method to Check if Mobile Number is valid
 */
fun isValidMobile(phone: String): Boolean{
    return Pattern.matches("[0-9]+", phone) && phone.length in 10..10
}

/**
 * This method checks if the input value is a proper Decimal value
 */
fun isDecimal(value: String): Boolean{
    return try {
        value.toDouble()
        true
    }catch (e: Exception){
        false
    }

}