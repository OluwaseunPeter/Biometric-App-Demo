package com.joseph.biometriclogin

import android.content.Context
import android.preference.PreferenceManager

class PreferenceManager (val context: Context){

    companion object {
        val LOGIN_PIN = "LOGIN_PIN"
        val LOGIN_PIN_TRIES = "LOGIN_PIN_TRIES"
    }

    fun saveStringToPreference(key:String , value:String){
        ///In a production app , Value should be encrypted before save
        val settingPref = PreferenceManager.getDefaultSharedPreferences(this.context).edit()
        settingPref.putString(key, value)
        settingPref.apply()
    }

    fun getStringToPreference(key:String):String?{
        ///In a production app , Value should be encrypted before save
        val settingPref = PreferenceManager.getDefaultSharedPreferences(this.context)
        return settingPref.getString(key , "")
    }

    fun removeFromPreference(key:String){
        ///In a production app , Value should be encrypted before save
        val settingPref = PreferenceManager.getDefaultSharedPreferences(this.context).edit()
        settingPref.remove(key)
        settingPref.apply()
    }

    fun saveIntToPreference(key:String , value:Int){
        ///In a production app , Value should be encrypted before save
        val settingPref = PreferenceManager.getDefaultSharedPreferences(this.context).edit()
        settingPref.putInt(key, value)
        settingPref.apply()
    }

    fun getIntToPreference(key:String):Int?{
        ///In a production app , Value should be encrypted before save
        val settingPref = PreferenceManager.getDefaultSharedPreferences(this.context)
        return settingPref.getInt(key , -1)
    }



}