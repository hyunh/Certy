package com.hyunh.certy.sgp23

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.hyunh.certy.R
import com.hyunh.certy.databinding.FragmentRspResultBinding
import com.hyunh.certy.setSupportActionBar
import com.hyunh.certy.supportActionBar

class RspResultFragment : Fragment() {

    private val model: RspViewModel by viewModels()

    private val args by navArgs<RspResultFragmentArgs>()
    private val tabInfos by lazy {
        when (args.viewType) {
            RspViewModel.ViewType.OPTION -> {
                listOf(
                        TabInfo(RspViewModel.ViewType.CONDITION, getString(R.string.rsp_condition)),
                        TabInfo(RspViewModel.ViewType.TESTCASE, getString(R.string.rsp_testcase))
                )
            }
            RspViewModel.ViewType.CONDITION -> {
                listOf(
                        TabInfo(RspViewModel.ViewType.OPTION, getString(R.string.rsp_option)),
                        TabInfo(RspViewModel.ViewType.TESTCASE, getString(R.string.rsp_testcase))
                )
            }
            RspViewModel.ViewType.TESTCASE -> {
                listOf(
                        TabInfo(RspViewModel.ViewType.OPTION, getString(R.string.rsp_option)),
                        TabInfo(RspViewModel.ViewType.CONDITION, getString(R.string.rsp_condition))
                )
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRspResultBinding>(
                inflater, R.layout.fragment_rsp_result, container, false).apply {
            viewPager.adapter = RspPagerAdapter(this@RspResultFragment, args.viewType, tabInfos)
            toolbarTitle = args.viewType.name
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.customView = inflater.inflate(R.layout.layout_tab_text, container, false).also {
                if (it is TextView) {
                    it.text = tabInfos[position].title
                    it.setTextColor(resources.getColorStateList(R.color.tab_text_color,
                            requireContext().theme))
                    it.setBackgroundColor(android.R.attr.selectableItemBackground)
                }
            }
        }.attach()

        setHasOptionsMenu(true)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        model.reset()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    class RspPagerAdapter(
            fragment: Fragment,
            private val viewType: RspViewModel.ViewType,
            private val tabInfos: List<TabInfo>
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount() = tabInfos.size

        override fun createFragment(position: Int) =
                RspResultContentFragment(viewType, tabInfos[position].viewType)
    }
}