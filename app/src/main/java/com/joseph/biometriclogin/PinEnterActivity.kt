package com.joseph.biometriclogin

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

class PinEnterActivity : AppCompatActivity() {

    @BindView(R.id.pin_set)
    lateinit var pinSetEd: AppCompatEditText

    @BindView(R.id.pin_set_btn)
    lateinit var pinSetBtn: AppCompatButton

    @BindView(R.id.finger_print)
    lateinit var bioAuthButton:ImageButton

    private var biometricPrompt: BiometricPrompt? = null
    private var promptInfo: PromptInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_enter)
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

        //get pin from shared preference
        val manager = PreferenceManager(this)
        val pin = manager.getStringToPreference(PreferenceManager.LOGIN_PIN)
        var tries = manager.getIntToPreference(PreferenceManager.LOGIN_PIN_TRIES)

        if(thisPin.equals(pin)){
            pinSuccess()
            return
        }

        tries = tries?.minus(1)

        if(tries != null && tries < 1) {
            Toast.makeText(this , "Maximum Pin Attempt Reached" , Toast.LENGTH_SHORT).show()
            gotoLogin()
            return;
        }else{
            Toast.makeText(this , "Incorrect Pin! $tries attempt remaining" , Toast.LENGTH_SHORT).show()
        }

        pinSetEd.text?.clear()

        manager.saveIntToPreference(PreferenceManager.LOGIN_PIN_TRIES , tries!!)
    }

    @OnClick(R.id.forgot_pin)
    fun gotoLogin(){
        val manager = PreferenceManager(this)
        manager.removeFromPreference(PreferenceManager.LOGIN_PIN_TRIES)
        manager.removeFromPreference(PreferenceManager.LOGIN_PIN)
        val login  = Intent(this , LoginActivity::class.java)
        startActivity(login)
        finish()
    }

    fun pinSuccess(){
        //reset retries
        val manager = PreferenceManager(this)
        manager.saveIntToPreference(PreferenceManager.LOGIN_PIN_TRIES , 5)
        val dashboard  = Intent(this , Dashboard::class.java)
        startActivity(dashboard)
    }

    override fun onResume() {
        super.onResume()
        if (BiometricUtils.isBiometricPromptEnabled() &&
            BiometricUtils.isFingerprintAvailable(this) &&
            BiometricUtils.isHardwareSupported(this) &&
            BiometricUtils.isPermissionGranted(this)
        ) {
            configureBioAuth()
            displayBiometricPrompt()
        } else {
            bioAuthButton.visibility = View.GONE
        }
    }

    @TargetApi(Build.VERSION_CODES.P)
    private fun configureBioAuth() {
        biometricPrompt = BiometricPrompt(
            this,
            ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    Log.e("PINEnter", String.format("Authentication error (%s): %s", errorCode, errString))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    pinSuccess()
                }

                override fun onAuthenticationFailed() {
                    Log.e("PINEnter" , "Biometric authentication failed")
                }
            })
        promptInfo = PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account pin")
            .build()
        bioAuthButton.setOnClickListener(View.OnClickListener { v: View? -> displayBiometricPrompt() })
    }

    @TargetApi(Build.VERSION_CODES.P)
    private fun displayBiometricPrompt() {
        biometricPrompt?.authenticate(promptInfo!!)
    }
}