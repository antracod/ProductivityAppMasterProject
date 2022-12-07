package com.leusoft.taskcore.auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.leusoft.taskcore.R
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask


class WelcomeAuth : AppCompatActivity() {

    private val RC_SIGN_IN = 1

    private val signInProviders = listOf(AuthUI.IdpConfig.EmailBuilder()
        .setAllowNewAccounts(true)
        .setRequireName(true)
        .build())




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_auth)

        var welcomeLoginButton = findViewById<Button>(R.id.welcomeLoginButton)
        welcomeLoginButton.setOnClickListener {
            val intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(signInProviders)
                .build()
            startActivityForResult(intent,RC_SIGN_IN)


            //val intent = Intent(this, LoginActivity::class.java)
            //startActivity(intent)
        }

        var welcomeRegisterButton = findViewById<Button>(R.id.welcomeRegisterButton)
        welcomeRegisterButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN)
        {
            val response = IdpResponse.fromResultIntent(data)

            if(resultCode == Activity.RESULT_OK)
            {
                val progressDialog = indeterminateProgressDialog("Setting up your account")

                FirestoreUtil.initCurrentUserIfFirstTime {
                    startActivity(intentFor<DashboardActivity>().newTask().clearTask())
                    progressDialog.dismiss()
                }


            }
            else if(resultCode == Activity.RESULT_CANCELED)
            {
                if(response == null) return

                when(response.error?.errorCode)
                {
                    ErrorCodes.NO_NETWORK ->
                        Toast.makeText(applicationContext,"No Network", Toast.LENGTH_SHORT).show()
                    ErrorCodes.UNKNOWN_ERROR ->
                        Toast.makeText(applicationContext,"No Network", Toast.LENGTH_SHORT).show()

                }
            }

        }
    }
}