package com.joseph.biometriclogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

class LoginActivity : AppCompatActivity() {

    @BindView(R.id.user_name)
    lateinit  var userNameView: AppCompatEditText

    @BindView(R.id.password)
    lateinit  var passwordView: AppCompatEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.login_btn)
    fun loginAction(){

        var username = userNameView.text?.trim().toString()
        var password = passwordView.text?.trim().toString()

        //For demo , we are comparing against hard coded login details
        //in our case , we are using admin , password1

        if(!"admin".equals(username) || !"password1".equals(password)){
            Toast.makeText(this , "Wrong Username and Password" , Toast.LENGTH_SHORT).show()
            return;
        }

        //now new launch pin set to complete login
        var intent =  Intent(this , PinSetActivity::class.java )
        startActivity(intent)
        finish()
    }
}