package com.hyunh.certy.sgp23

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hyunh.certy.R
import com.hyunh.certy.databinding.LayoutRspTestcaseBinding

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