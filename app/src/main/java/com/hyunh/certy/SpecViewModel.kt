package com.hyunh.certy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SpecViewModel : ViewModel() {

    private val specList = MutableLiveData<List<Spec>>()

    fun loadSupportedSpec(): LiveData<List<Spec>> {
        specList.postValue(SpecController.load())
        return specList
    }
}