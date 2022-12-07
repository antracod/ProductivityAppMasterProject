package com.leusoft.taskcore.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.leusoft.taskcore.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerEmailET = findViewById<EditText>(R.id.registerEditTextEmail)
        val registerPasswordET = findViewById<EditText>(R.id.registerEditTextPassword)
        val registerSubmitButton = findViewById<Button>(R.id.registerSubmitButton)
        registerSubmitButton.setOnClickListener {
            var registerEmailText = registerEmailET.text
            val registerPasswordText = registerPasswordET.text

        }


    }
}