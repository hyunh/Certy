package com.hyunh.certy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hyunh.certy.databinding.LayoutRelBinding
import com.hyunh.certy.databinding.LayoutSpecBinding

class SpecAdapter : RecyclerView.Adapter<SpecAdapter.ViewHolder>() {

    private val items = mutableListOf<Spec>()

    fun updateItem(newItems: List<Spec>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.layout_spec, parent, false)
        )
    }

    class ViewHolder(
            private val binding: LayoutSpecBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setOnClickListener {
                binding.isRecyclerViewVisible = !binding.isRecyclerViewVisible
            }
        }

        fun bind(spec: Spec) {
            binding.spec = spec
            binding.recyclerview.apply {
                setHasFixedSize(true)
                adapter = RelAdapter(spec)
            }
        }
    }

    private class RelAdapter(
            private val spec: Spec
    ) : RecyclerView.Adapter<RelAdapter.ViewHolder>() {

        override fun getItemCount() = spec.rel.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(spec.name, spec.rel[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    R.layout.layout_rel, parent, false))
        }

        class ViewHolder(
                private val binding: LayoutRelBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(title: String, rel: String) {
                binding.rel = rel
                binding.setOnClickListener {
                    val action = SpecFragmentDirections.actionToRsp(title, rel)
                    it.findNavController().navigate(action)
                }
            }
        }
    }
}