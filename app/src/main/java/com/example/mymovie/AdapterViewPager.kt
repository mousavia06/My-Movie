package com.example.mymovie

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class AdapterViewPager(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return FragmentHome()
            }
            1 -> {
                return FragmentHome()
            }
            2 -> {
                return FragmentHome()
            }
            3 -> {
                return FragmentHome()
            }
            else -> {
               return Fragment()
            }
        }
    }
}