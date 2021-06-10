package com.developer.bshapp.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.developer.bshapp.API.RetrofitApi
import com.developer.bshapp.R
import com.developer.bshapp.classes.URLs
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.truecaller.android.sdk.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Retrofit
import java.util.HashMap

class UserAccount : AppCompatActivity() {

    val TAG : String = "TrueCaller_Login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_account)

        findViewById<TextView>(R.id.btnTruecaller).setOnClickListener {
            if (TruecallerSDK.getInstance().isUsable) {
                TruecallerSDK.getInstance().getUserProfile(this@UserAccount)
            } else {
                val dialogBuilder = android.app.AlertDialog.Builder(this@UserAccount)
                dialogBuilder.setMessage("Truecaller App not installed.")

                dialogBuilder.setPositiveButton(
                    "OK"
                ) { dialog: DialogInterface, which: Int ->
                    Log.d(TAG, "onClick: Closing dialog")
                    dialog.dismiss()
                }

                dialogBuilder.setIcon(R.drawable.com_truecaller_icon)
                dialogBuilder.setTitle(" ")

                val alertDialog = dialogBuilder.create()
                alertDialog.show()

            }
        }

        val trueScope: TruecallerSdkScope = TruecallerSdkScope.Builder(this, object :
            ITrueCallback {
            override fun onSuccessProfileShared(trueProfile: TrueProfile) {
                Log.i(TAG, trueProfile.firstName.toString() + " " + trueProfile.lastName)
                launchHome(trueProfile)
            }

            override fun onFailureProfileShared(trueError: TrueError) {
                Log.i(TAG, trueError.toString())
            }

            override fun onVerificationRequired() {
                Log.i(TAG, "onVerificationRequired")
            }
        })
            .consentMode(TruecallerSdkScope.CONSENT_MODE_POPUP)
            .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_VERIFY)
            .footerType(TruecallerSdkScope.FOOTER_TYPE_SKIP).build()
        TruecallerSDK.init(trueScope)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        TruecallerSDK.getInstance().onActivityResultObtained(this, resultCode, data)
    }

    private fun launchHome(trueProfile: TrueProfile) {
        val name = trueProfile.firstName.toString() + " " + trueProfile.lastName
        val number = trueProfile.phoneNumber
        val email = trueProfile.email

        TrueLogin(name,number,email)
    }

    fun TrueLogin(name : String, number : String, email : String){

        val retrofit = Retrofit.Builder()
            .baseUrl(URLs.BASE_URL) // as we are sending data in json format so
            .build()

        // Create Service
        val service = retrofit.create(RetrofitApi::class.java)

        val params = HashMap<String?, String?>()
        params[""] = name
        params[""] = number
        params[""] = email

        CoroutineScope(Dispatchers.IO).launch {

            // Do the POST request and get response
            val response = service.uploadNews(params)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser().parse(response.body()?.string()))

                    Log.d("Pretty Printed JSON :", prettyJson)

                    val jsonObject : JSONObject = JSONObject(prettyJson)
                    if(jsonObject.getBoolean("")){

                    } else {

                    }
                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }

    }

}