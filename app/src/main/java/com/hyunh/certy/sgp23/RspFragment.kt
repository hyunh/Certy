package com.hyunh.certy.sgp23

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hyunh.certy.R
import com.hyunh.certy.databinding.FragmentRspBinding
import com.hyunh.certy.setSupportActionBar
import com.hyunh.certy.supportActionBar

class RspFragment : Fragment() {

    private val model: RspViewModel by viewModels()
    private val args by navArgs<RspFragmentArgs>()
    private var selectedTabPosition: Int = 0
    private val tabInfos by lazy {
        listOf(
                TabInfo(RspViewModel.ViewType.OPTION, getString(R.string.rsp_option)),
                TabInfo(RspViewModel.ViewType.CONDITION, getString(R.string.rsp_condition)),
                TabInfo(RspViewModel.ViewType.TESTCASE, getString(R.string.rsp_testcase))
        )
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) : View? {
        val binding = DataBindingUtil.inflate<FragmentRspBinding>(
                inflater, R.layout.fragment_rsp, container, false).apply {
            viewPager.adapter = RspPagerAdapter(this@RspFragment, tabInfos)
            toolbarTitle = "${args.name}  -  ${args.rel}"
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    model.reset()
                    selectedTabPosition = tab?.position ?: 0
                }
            })
        }
        model.setVersion(args.rel)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_rsp, menu)
        model.loadSelectedItems().observe(viewLifecycleOwner, Observer {
            menu.findItem(R.id.menu_confirm).isVisible = !it.isNullOrEmpty()
            menu.findItem(R.id.menu_more).isVisible = it.isNullOrEmpty()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            R.id.menu_search -> {
                true
            }
            R.id.menu_confirm -> {
                val action = RspFragmentDirections.actionToRspResult(
                        tabInfos[selectedTabPosition].viewType)
                findNavController().navigate(action)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    class RspPagerAdapter(
            fragment: Fragment,
            private val tabInfos: List<TabInfo>
    ) : FragmentStateAdapter(fragment) {

        override fun getItemCount() = tabInfos.size

        override fun createFragment(position: Int) =
                RspContentFragment(tabInfos[position].viewType)
    }
}