package com.example.hce_rfid_cardemulatorsample

import androidx.lifecycle.LiveData

class MessageLiveData : LiveData<String>() {

    fun setMessage(message: String) {
        postValue(message)
    }

}

class LiveDataManager {

    companion object {

        private val messageLiveData = MessageLiveData()

        fun updateMessage(message : String) {
            messageLiveData.setMessage(message)
        }

        fun messageLiveData() = messageLiveData
    }

}