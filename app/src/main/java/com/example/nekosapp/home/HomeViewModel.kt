package com.example.nekosapp.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nekosapp.network.Item
import com.example.nekosapp.network.NekosApi
import kotlinx.coroutines.launch

enum class RandomImageStatus { LOADING, DONE, ERROR }

data class HomeModel(
    var status: RandomImageStatus?,
    var itemList: List<Item>?
)

class HomeViewModel : ViewModel() {

    companion object {
        const val TAG = "HomeViewModel"
    }

    /** Backing properties: */

//    private val _status = MutableLiveData<RandomImageStatus>()
//    val status: LiveData<RandomImageStatus>
//        get() = _status
//
//    private val _itemList = MutableLiveData<List<Item>>()
//    val itemList: LiveData<List<Item>>
//        get() = _itemList

    private val _homeViewModelProps = MutableLiveData<HomeModel>()
    val homeViewModelProps: LiveData<HomeModel>
        get() = _homeViewModelProps

    init {
        getItemList()
    }

    private fun getItemList() {
        viewModelScope.launch {
            _homeViewModelProps.value = HomeModel(RandomImageStatus.LOADING, listOf())
            try {
                _homeViewModelProps.value = HomeModel(
                    RandomImageStatus.DONE,
                    NekosApi.retrofitService.getRandomImages().items
                )
            } catch (e: Exception) {
                _homeViewModelProps.value = HomeModel(RandomImageStatus.ERROR, listOf())
                Log.d(TAG, "Error: $e")
            }
        }
    }
}