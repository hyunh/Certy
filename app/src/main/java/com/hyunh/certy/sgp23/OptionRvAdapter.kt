package com.hyunh.certy.sgp23

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hyunh.certy.R
import com.hyunh.certy.databinding.LayoutRspOptionBinding

class OptionRvAdapter(
        private val model: RspViewModel
): RecyclerView.Adapter<OptionRvAdapter.ViewHolder>() {

    private val items: List<Rsp.Option> = mutableListOf()
    private val selectedItems: Set<Int> = mutableSetOf()

    fun update(new: List<Rsp.Option>) {
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

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position, selectedItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<LayoutRspOptionBinding>(
                LayoutInflater.from(parent.context), R.layout.layout_rsp_option, parent, false)
        binding.vm = model
        return ViewHolder(binding)
    }

    class ViewHolder(
            private val binding: LayoutRspOptionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Rsp.Option, position: Int, selectedItems: Set<Int>) {
            binding.tvOptionMnemonic.text = item.mnemonic
            binding.tvOptionType.text = binding.root.resources.getString(
                    R.string.format_brackets, item.type)
            binding.tvOptionDescription.text = item.option
            binding.root.setBackgroundResource(if (selectedItems.contains(position)) {
                R.color.colorItemSelected
            } else {
                R.color.colorForeground
            })
            binding.position = position
        }
    }
}