package com.leusoft.taskcore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.leusoft.taskcore.auth.WelcomeAuth
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(FirebaseAuth.getInstance().currentUser == null)
        {
            startActivity<WelcomeAuth>()
        }
        else
        {
            startActivity<DashboardActivity>()
        }

        finish()
    }
}