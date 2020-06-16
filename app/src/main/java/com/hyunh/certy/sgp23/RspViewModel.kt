package com.hyunh.certy.sgp23

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RspViewModel : ViewModel(), LifecycleObserver {

    private val _selectedItems = mutableSetOf<Int>()
    val selectedItems: LiveData<Set<Int>> = MutableLiveData(_selectedItems)

    enum class ViewType { OPTION, CONDITION, TESTCASE }
    val viewType: LiveData<ViewType> = MutableLiveData(ViewType.OPTION)

    fun reset() {
        _selectedItems.clear()
        (selectedItems as MutableLiveData).postValue(_selectedItems)
    }

    fun selectItem(position: Int) {
        if (_selectedItems.contains(position)) {
            _selectedItems.remove(position)
        } else {
            _selectedItems.add(position)
        }
        (selectedItems as MutableLiveData).postValue(_selectedItems)
    }

    fun setVersion(version: String) {
        Rsp.version = version
    }

    fun loadOptions() = Rsp.loadOptions()

    fun loadConditions() = Rsp.loadConditions()

    fun loadTestCases() = Rsp.loadTestCases()
}