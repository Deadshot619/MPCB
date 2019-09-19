package com.example.mpcb.utils.shared_prefrence

import android.content.Context
import android.content.SharedPreferences
import com.example.mpcb.base.MPCBApp
import com.example.mpcb.utils.constants.Constants


object PreferencesHelper {

    private var mSharedPreferences: SharedPreferences? = null
//    private var mEditor: SharedPreferences.Editor? = null


    private val mEditor  by lazy {
        mSharedPreferences = MPCBApp.instance.getSharedPreferences(Constants.myPrefK,Context.MODE_PRIVATE)
       return@lazy mSharedPreferences?.edit()
    }

    //Generic methods for Shared Preference

    fun deletAll() {
        mEditor?.clear()!!.apply()
    }

    /*------*-*------ Default Generice methods* ------*-*------ */

    fun setStringPreference(key:String, value:String){
        mEditor?.apply {
         putString(key,value)
         apply()
        }

    }

    fun setIntPreference(key:String, value:Int){
        mEditor?.apply {
        putInt(key,value)
        apply()
        }
    }

    fun setBooleanPreference(key:String, value:Boolean){
        mEditor?.apply {
        putBoolean(key,value)
        apply()
        }
    }


    fun getStringPreference(key:String, value:String=""):String?{
        return mSharedPreferences!!.getString(key,value)
    }

    fun getIntPreference(key:String, value:Int=0):Int{
        return mSharedPreferences!!.getInt(key,value)
    }

    fun getBooleanPreference(key:String, value:Boolean=false):Boolean{
        return mSharedPreferences!!.getBoolean(key,value)
    }


    fun setPreferences(key:String, value:Any){

        when(value){
            is String -> setStringPreference(key,value)
            is Int -> setIntPreference(key,value)
            is Boolean -> setBooleanPreference(key,value)
        }
    }

    fun getPreferences(key:String, value:Any):Any?{

        when(value){
            is String -> return getStringPreference(key,value)
            is Int -> return getIntPreference(key,value)
            is Boolean -> return getBooleanPreference(key,value)
        }
        return ""
    }


    //Module wise Preference Helper

    fun isLogin(defValue:Boolean=false) = getBooleanPreference(Constants.mloginKey,defValue)
    fun setLogin(value: Boolean) = setBooleanPreference(Constants.mloginKey,value)








}