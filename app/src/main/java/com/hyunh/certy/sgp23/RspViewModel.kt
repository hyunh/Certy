package com.hyunh.certy.sgp23

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RspViewModel : ViewModel(), LifecycleObserver {

    enum class ViewType { OPTION, CONDITION, TESTCASE }
    val viewType: LiveData<ViewType> = MutableLiveData(ViewType.OPTION)

    fun reset() = Rsp.resetSelection()

    fun selectItem(position: Int) = Rsp.selectItem(position)

    fun setVersion(version: String) {
        Rsp.version = version
    }

    fun loadSelectedItems() = Rsp.loadSelectedItems()

    fun loadOptions() = Rsp.loadOptions()

    fun loadConditions() = Rsp.loadConditions()

    fun loadTestCases() = Rsp.loadTestCases()
}