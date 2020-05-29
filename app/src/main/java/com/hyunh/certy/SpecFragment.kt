package com.hyunh.certy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.hyunh.certy.databinding.FragmentSpecBinding

class SpecFragment : Fragment() {

    private val viewModel: SpecViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val adapter = SpecAdapter()
        val binding = DataBindingUtil.inflate<FragmentSpecBinding>(
                inflater, R.layout.fragment_spec, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            layoutRoundList.recyclerview.adapter = adapter
        }
        viewModel.loadSupportedSpec().observe(viewLifecycleOwner, Observer {
            adapter.updateItem(it)
        })

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        return binding.root
    }
}