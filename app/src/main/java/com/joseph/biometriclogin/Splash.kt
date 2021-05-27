package com.joseph.biometriclogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        //check if the user is already logged in
        //in production a server call will be made to very user token
        //but for now lets use some value stored inside shared preference
        //in production also those value whould need to be decrypted first
        //before use

        val pin =  PreferenceManager(this)
                    .getStringToPreference(PreferenceManager.LOGIN_PIN)
        if(pin != null && pin.length == 4){
            val intent = Intent(this, PinEnterActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}