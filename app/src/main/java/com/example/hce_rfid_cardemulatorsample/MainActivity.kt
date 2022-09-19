package com.example.hce_rfid_cardemulatorsample

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.hce_rfid_cardemulatorsample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var hostApduServiceIntent: Intent
    private var messageObserver : Observer<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hostApduServiceIntent = Intent(this@MainActivity, MyHostApduService::class.java)
        checkIfDeviceCanEmulateHostNFCTag()
        binding.editTextApduResponse.setText(Utils.APDURESPONSE)
        setupApduTextChangedListener()
        setMessageObserver()
    }

    override fun onResume() {
        super.onResume()
        val pm = packageManager

        pm.setComponentEnabledSetting(
            ComponentName(this@MainActivity, "com.example.hce_rfid_cardemulatorsample.MyHostApduService"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    override fun onPause() {
        super.onPause()
        val pm = packageManager

        pm.setComponentEnabledSetting(
            ComponentName(this@MainActivity, "com.example.hce_rfid_cardemulatorsample.MyHostApduService"),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }


    private fun setMessageObserver() {
        messageObserver = Observer { msg ->
            Log.d("layon.f", "MainActivity setMessageObserver $msg")
            runOnUiThread {
                binding.logtextView.append(msg)
            }
        }
        messageObserver?.let {
            LiveDataManager.messageLiveData().observe(this, it)
        }
    }

    private fun setupApduTextChangedListener() {
        binding.editTextApduResponse.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Utils.APDURESPONSE = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
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
            startBlinkEffect(binding.emulating)
        } else {
            binding.emulating.text = "Your device can NOT emulate NFC cards"
        }
    }

}