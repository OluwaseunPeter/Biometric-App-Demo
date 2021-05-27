package com.joseph.biometriclogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

class PinSetActivity : AppCompatActivity() {

    @BindView(R.id.pin_set_text)
    lateinit var pinSetTV: TextView

    @BindView(R.id.pin_set)
    lateinit var pinSetEd: AppCompatEditText

    @BindView(R.id.pin_set_btn)
    lateinit var pinSetBtn: AppCompatButton

    var firstPin = ""
    var mode = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_set)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.pin_set_btn)
    fun pinSetAction(){
        var thisPin = pinSetEd.text.toString();

        //Confirm if pin length is 4
        if(thisPin.length != 4){
            Toast.makeText(this , "Please Enter 4 Digit Pin" , Toast.LENGTH_SHORT).show()
            return;
        }

        if(mode == 0 ){
            firstPin = thisPin;
            mode = 1
            updateView()
            return
        }

        //Else we compare pin
        var confirmPin = thisPin;
        if(!firstPin.equals(confirmPin)){
            mode = 0
            Toast.makeText(this , "Pin Does not match" , Toast.LENGTH_SHORT).show()
            updateView()
            return;
        }

        //Our Pin Match, we need to save it into Shared preference
        //In a real world app Pin Should be Encrypted Before Saving
        //After saving We proceed to Dashboard
        val pref = PreferenceManager(this)
        pref.saveStringToPreference(PreferenceManager.LOGIN_PIN , thisPin)
        pref.saveIntToPreference(PreferenceManager.LOGIN_PIN_TRIES , 5)

        var intent = Intent(this , Dashboard::class.java)
        startActivity(intent)
        finish()
    }

    fun updateView(){
        if(mode == 0 ){
            pinSetTV.text = "Please Set 4 Digit Pin Number"
            pinSetEd.text?.clear()
            pinSetBtn.text = "Set Pin"
        }else{
            pinSetTV.text = "Please Confirm 4 Digit Pin Number"
            pinSetEd.text?.clear()
            pinSetBtn.text = "Confirm Pin"
        }
    }
}