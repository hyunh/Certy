package com.hyunh.certy.sgp23

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.hyunh.certy.R
import com.hyunh.certy.databinding.FragmentRspContentBinding

class RspResultContentFragment(
        private val target: RspViewModel.ViewType,
        viewType: RspViewModel.ViewType
) : Fragment() {

    private val model: RspViewModel by viewModels()
    private val adapter by lazy {
        loadAdapter(viewType)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding = DataBindingUtil.inflate<FragmentRspContentBinding>(
                inflater, R.layout.fragment_rsp_content, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            layoutRoundList.recyclerview.setHasFixedSize(true)
            layoutRoundList.recyclerview.adapter = adapter
        }
        return binding.root
    }

    private fun loadAdapter(viewType: RspViewModel.ViewType): RecyclerView.Adapter<*> {
        return when (viewType) {
            RspViewModel.ViewType.OPTION -> {
                OptionRvAdapter(model).apply {
                    update(model.loadResultOptions(target))
                }
            }
            RspViewModel.ViewType.CONDITION -> {
                ConditionRvAdapter(model).apply {
                    update(model.loadResultConditions(target))
                }
            }
            RspViewModel.ViewType.TESTCASE -> {
                TestCaseRvAdapter(model).apply {
                    model.hideMandatory.observe(viewLifecycleOwner, Observer { hide ->
                        if (hide == true) {
                            update(model.loadResultTestCases(target).filter { testCase ->
                                testCase.mocs.find { pair ->
                                    pair.second == model.sgp22Version()
                                }?.first != "M"
                            })
                        } else {
                            update(model.loadResultTestCases(target))
                        }
                    })
                }
            }
        }
    }
}