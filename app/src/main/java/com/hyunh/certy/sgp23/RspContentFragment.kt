package com.hyunh.certy.sgp23

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.hyunh.certy.R
import com.hyunh.certy.databinding.FragmentRspContentBinding

class RspContentFragment(viewType: RspViewModel.ViewType) : Fragment() {

    private val model: RspViewModel by viewModels()
    private var menu: Menu? = null
    private val adapter by lazy { loadAdapter(viewType) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding = DataBindingUtil.inflate<FragmentRspContentBinding>(
                inflater, R.layout.fragment_rsp_content, container, false).apply {
            vm = model
            lifecycleOwner = viewLifecycleOwner
            layoutRoundList.recyclerview.setHasFixedSize(true)
            layoutRoundList.recyclerview.adapter = adapter
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.reset()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_confirm, menu)
        this.menu = menu
    }

    private fun loadAdapter(viewType: RspViewModel.ViewType): RecyclerView.Adapter<*> {
        return when (viewType) {
            RspViewModel.ViewType.OPTION -> {
                OptionRvAdapter(model).apply {
                    model.loadOptions().observe(viewLifecycleOwner, Observer {
                        update(it)
                    })
                    model.selectedItems.observe(viewLifecycleOwner, Observer {
                        updateSelectedItems(it)
                        menu?.findItem(R.id.menu_confirm)?.isVisible = !it.isNullOrEmpty()
                    })
                }
            }
            RspViewModel.ViewType.CONDITION -> {
                ConditionRvAdapter(model).apply {
                    model.loadConditions().observe(viewLifecycleOwner, Observer {
                        update(it)
                    })
                    model.selectedItems.observe(viewLifecycleOwner, Observer {
                        updateSelectedItems(it)
                        menu?.findItem(R.id.menu_confirm)?.isVisible = !it.isNullOrEmpty()
                    })
                }
            }
            RspViewModel.ViewType.TESTCASE -> {
                TestCaseRvAdapter(model).apply {
                    model.loadTestCases().observe(viewLifecycleOwner, Observer {
                        update(it)
                    })
                    model.selectedItems.observe(viewLifecycleOwner, Observer {
                        updateSelectedItems(it)
                        menu?.findItem(R.id.menu_confirm)?.isVisible = !it.isNullOrEmpty()
                    })
                }
            }
        }
    }
}