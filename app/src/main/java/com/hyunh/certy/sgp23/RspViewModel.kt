package com.hyunh.certy.sgp23

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel

class RspViewModel : ViewModel(), LifecycleObserver {

    fun setVersion(version: String) {
        Rsp.version = version
    }

    fun loadOptions() = Rsp.loadOptions()

    fun loadConditions() = Rsp.loadConditions()

    fun loadTestCases() = Rsp.loadTestCases()
}