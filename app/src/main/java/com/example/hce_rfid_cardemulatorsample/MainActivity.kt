package com.example.hce_rfid_cardemulatorsample

import android.content.Intent
import android.content.pm.PackageManager
import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hce_rfid_cardemulatorsample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var hostApduServiceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("layon.f", "MainActivity - onCreate()")
        hostApduServiceIntent = Intent(this@MainActivity, MyHostApduService::class.java)
        checkIfDeviceCanEmulateHostNFCTag()
    }

    fun startBlinkEffect(text : TextView) {
        text.text = "Emulating..."
        val animation = AlphaAnimation(0.1f, 1.0f).also {
            it.duration = 250
            it.repeatMode = Animation.REVERSE
            it.repeatCount = Animation.INFINITE
        }
        text.startAnimation(animation)
    }

    fun stopBlinkEffect(text : TextView) {
        text.text = "Not emulating"
        text.clearAnimation()
    }

    private fun checkIfDeviceCanEmulateHostNFCTag(){
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION)){
            setupButtonsClickListenter()
            binding.buttonStart.performClick()
        } else {
            binding.emulating.text = "Your device can NOT emulate NFC cards"
        }
    }

    private fun setupButtonsClickListenter() {
        binding.apply {
            buttonStart.setOnClickListener {
                Log.d("layon.f", "MainActivity - buttonStart clicked")
                startService(hostApduServiceIntent)
                startBlinkEffect(binding.emulating)
            }

            buttonStop.setOnClickListener {
                Log.d("layon.f", "MainActivity - buttonStop clicked")
                stopService(hostApduServiceIntent)
                stopBlinkEffect(binding.emulating)
            }
        }
    }

}