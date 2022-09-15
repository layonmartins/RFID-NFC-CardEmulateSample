package com.example.hce_rfid_cardemulatorsample

import android.content.Intent
import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log

class MyHostApduService : HostApduService() {

    // The `processCommandApdu` method will be called every time a card
    // reader sends an APDU command that is filtered by our manifest filter.
    override fun processCommandApdu(commandApdu: ByteArray, extras: Bundle?): ByteArray {
        Log.d("layon.f", "processCommandApdu(commandApdu = $commandApdu, extras = $extras)")

        return commandApdu
    }

    // The `onDeactiveted` method will be called when the a different
    // AID has been selected or the NFC connection has been lost
    override fun onDeactivated(reason: Int) {
        Log.d(
            "layon.f", "onDeactivated(reason = ${
                when (reason) {
                    0 -> "DEACTIVATION_LINK_LOSS"
                    1 -> "DEACTIVATION_DESELECTED"
                    else -> "UNKNOWN"
                }
            }"
        )
    }



    override fun onCreate() {
        super.onCreate()
        Log.d("layon.f", "MyHostApduService onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("layon.f", "onStartCommand intent: $intent, flags: $flags, startId: $startId")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("layon.f", "MyHostApduService onDestroy()")
    }

}