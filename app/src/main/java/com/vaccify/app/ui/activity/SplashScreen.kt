package com.vaccify.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.vaccify.app.R
import com.vaccify.app.interfaces.IPreferenceHelper
import com.vaccify.app.utils.PreferenceManager

class SplashScreen : AppCompatActivity() {
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // Using a handler to delay loading the MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            if (preferenceHelper.getLoggedIn()) {
                startActivity(Intent(this, LandingScreenActivity::class.java))
            } else {
                startActivity(Intent(this, LoginScreenActivity::class.java))
            }
            // Animate the loading of new activity
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            // Close this activity
            finish()

        }, 1000)


    }

}