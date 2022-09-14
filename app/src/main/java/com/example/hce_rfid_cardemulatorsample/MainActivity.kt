package com.example.hce_rfid_cardemulatorsample

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        checkIfDeviceCanEmulateHostNFCTag()
    }

    fun startBlinkEffect(text : TextView) {
        val animation = AlphaAnimation(0.1f, 1.0f).also {
            it.duration = 250
            it.repeatMode = Animation.REVERSE
            it.repeatCount = Animation.INFINITE
        }
        text.startAnimation(animation)
    }

    fun checkIfDeviceCanEmulateHostNFCTag(){
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION)){
            startBlinkEffect(binding.emulating)
        } else {
            binding.emulating.text = "Your device can NOT emulate NFC cards"
        }
    }
}