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
import com.hyunh.certy.databinding.LayoutRspTestcaseBinding

class TestCaseFragment : Fragment() {

    private val model: RspViewModel by viewModels()
    private var menu: Menu? = null

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
            layoutRoundList.recyclerview.adapter = TestCaseRvAdapter(model).apply {
                model.loadTestCases().observe(viewLifecycleOwner, Observer {
                    update(it)
                })
                model.selectedItems.observe(viewLifecycleOwner, Observer {
                    updateSelectedItems(it)
                    menu?.findItem(R.id.menu_confirm)?.isVisible = !it.isNullOrEmpty()
                })
            }
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

    class TestCaseRvAdapter(
            private val model: RspViewModel
    ): RecyclerView.Adapter<TestCaseRvAdapter.ViewHolder>() {

        private val items: List<Rsp.TestCase> = mutableListOf()
        private val selectedItems: Set<Int> = mutableSetOf()

        fun update(new: List<Rsp.TestCase>) {
            if (items is MutableList) {
                items.clear()
                items.addAll(new)
                notifyDataSetChanged()
            }
        }

        fun updateSelectedItems(new: Set<Int>) {
            if (selectedItems is MutableSet) {
                selectedItems.clear()
                selectedItems.addAll(new)
                notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = DataBindingUtil.inflate<LayoutRspTestcaseBinding>(
                    LayoutInflater.from(parent.context), R.layout.layout_rsp_testcase, parent, false)
            binding.vm = model
            return ViewHolder(binding)
        }

        override fun getItemCount() = items.size

        override fun getItemViewType(position: Int) = position

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position], position, selectedItems)
        }

        class ViewHolder(
                private val binding: LayoutRspTestcaseBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Rsp.TestCase, position: Int, selectedItems: Set<Int>) {
                binding.tvTestcaseId.text = item.id
                binding.tvTestcaseName.text = item.name
                binding.root.setBackgroundResource(if (selectedItems.contains(position)) {
                    R.color.colorItemSelected
                } else {
                    R.color.colorForeground
                })
                binding.position = position
            }
        }
    }
}