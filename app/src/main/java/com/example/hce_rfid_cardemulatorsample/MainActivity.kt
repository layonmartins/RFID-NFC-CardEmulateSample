package com.example.hce_rfid_cardemulatorsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import com.example.hce_rfid_cardemulatorsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        startBlinkEffect(binding.emulating)
    }

    fun startBlinkEffect(text : TextView) {
        val animation = AlphaAnimation(0.1f, 1.0f).also {
            it.duration = 250
            it.repeatMode = Animation.REVERSE
            it.repeatCount = Animation.INFINITE
        }
        text.startAnimation(animation)
    }
}