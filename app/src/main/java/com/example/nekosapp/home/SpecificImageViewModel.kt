package com.example.nekosapp.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nekosapp.network.Item
import com.example.nekosapp.network.NekosApi
import kotlinx.coroutines.launch

enum class SpecificImageStatus { LOADING, DONE, ERROR }

class SpecificImageViewModel : ViewModel() {

    private val _status = MutableLiveData<SpecificImageStatus>()
    val status: LiveData<SpecificImageStatus>
        get() = _status

    private val _specificItem = MutableLiveData<Item>()
    val specificItem: LiveData<Item>
        get() = _specificItem

    fun retrieveSpecificImage(id: Int) {
        getSpecificItem(id)
    }

    private fun getSpecificItem(id: Int) {
        viewModelScope.launch {
            try {
                _specificItem.value = NekosApi.retrofitService.getImageById(id)
                _status.value = SpecificImageStatus.DONE
            } catch (e: Exception) {
                _status.value = SpecificImageStatus.ERROR
                Log.d(TAG, "Error: ${e.message}")
            }
        }
    }

    companion object {
        const val TAG = "SpecificImageViewModel"
    }
}