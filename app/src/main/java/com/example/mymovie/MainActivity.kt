package com.example.mymovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.mymovie.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPagerAdapter = AdapterViewPager(this)
        binding.viewPagerMain.adapter = viewPagerAdapter
        binding.viewPagerMain.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPagerMain.offscreenPageLimit = 4
        val mediator = TabLayoutMediator(binding.tabLayoutMain ,
            binding.viewPagerMain ,
            object :TabLayoutMediator.TabConfigurationStrategy{
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    when (position) {
                        0 -> {tab.setIcon(R.drawable.ic_home)}
                        1 -> {}
                        2 -> {}
                        3 -> {}
                    }
                }
            })
        mediator.attach()
    }
}