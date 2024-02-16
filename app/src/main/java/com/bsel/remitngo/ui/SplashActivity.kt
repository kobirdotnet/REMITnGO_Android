package com.bsel.remitngo.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bsel.remitngo.R
import com.bsel.remitngo.databinding.ActivitySplashBinding
import com.bsel.remitngo.presentation.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imgAnim = AnimationUtils.loadAnimation(
            applicationContext, R.anim.slide_fade_in
        )
        binding.apply {
            splashImage.startAnimation(imgAnim)
        }
        Handler().postDelayed({
            val i = Intent(applicationContext, LoginActivity::class.java)
            startActivity(i)
            finish()
        }, 1000)
    }


}