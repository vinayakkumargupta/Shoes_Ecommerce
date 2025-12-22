package com.univ.shoes

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.univ.shoes.Helper.BaseActivity
import com.univ.shoes.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity(){
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }
    fun setupUi(){
        binding.apply {
            moveToHome.setOnClickListener {
                val intent = Intent(this@SplashActivity, HomeScreenActivity::class.java)
                startActivity(intent)
            }
        }
    }
}