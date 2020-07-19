package com.hyunh.certy.sgp23

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RspViewModel : ViewModel(), LifecycleObserver {

    enum class ViewType { OPTION, CONDITION, TESTCASE }

    val viewType: LiveData<ViewType> = MutableLiveData(ViewType.OPTION)

    val hideMandatory = Rsp.hideMandatoryTestCase

    fun reset() = Rsp.resetSelection()

    fun selectItem(position: Int) = Rsp.selectItem(position)

    fun setVersion(version: String) {
        Rsp.version = version
    }

    fun sgp22Version() = Rsp.sgp22Version

    fun hideMandatory(hide: Boolean) {
        Rsp.hideMandatoryTestCase(hide)
    }

    fun loadSelectedItems() = Rsp.loadSelectedItems()

    fun loadOptions() = Rsp.loadOptions()

    fun loadResultOptions(viewType: ViewType) = Rsp.loadResultOptions(convertType(viewType))

    fun loadConditions() = Rsp.loadConditions()

    fun loadResultConditions(viewType: ViewType) = Rsp.loadResultConditions(convertType(viewType))

    fun loadTestCases() = Rsp.loadTestCases()

    fun loadResultTestCases(viewType: ViewType) = Rsp.loadResultTestcases(convertType(viewType))

    private fun convertType(viewType: ViewType) = when (viewType) {
        ViewType.CONDITION -> Rsp.CONDITION
        ViewType.TESTCASE -> Rsp.TESTCASE
        ViewType.OPTION -> Rsp.OPTION
    }
}