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

        if (commandApdu == null) {
            return Utils.hexStringToByteArray(STATUS_FAILED)
        }

        val hexCommandApdu = Utils.toHex(commandApdu)
        if (hexCommandApdu.length < MIN_APDU_LENGTH) {
            return Utils.hexStringToByteArray(STATUS_FAILED)
        }

        if (hexCommandApdu.substring(0, 2) != DEFAULT_CLA) {
            return Utils.hexStringToByteArray(CLA_NOT_SUPPORTED)
        }

        if (hexCommandApdu.substring(2, 4) != SELECT_INS) {
            return Utils.hexStringToByteArray(INS_NOT_SUPPORTED)
        }

        if (hexCommandApdu.substring(10, 24) == AID)  {
            return Utils.APDURESPONSE.toByteArray()
        } else {
            return Utils.hexStringToByteArray(STATUS_FAILED)
        }

    }

    // The `onDeactiveted` method will be called when the a different
    // AID has been selected or the NFC connection has been lost
    override fun onDeactivated(reason: Int) {
        Log.d(
            TAG, "onDeactivated(reason = ${
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
        Log.d(TAG, "MyHostApduService onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand intent: $intent, flags: $flags, startId: $startId")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MyHostApduService onDestroy()")
    }

    companion object {
        val TAG = "layon.f"
        val STATUS_SUCCESS = "9000"
        val STATUS_FAILED = "6F00"
        val CLA_NOT_SUPPORTED = "6E00"
        val INS_NOT_SUPPORTED = "6D00"
        val AID = "F0010203040506"
        val SELECT_INS = "A4"
        val DEFAULT_CLA = "00"
        val MIN_APDU_LENGTH = 12
    }

}