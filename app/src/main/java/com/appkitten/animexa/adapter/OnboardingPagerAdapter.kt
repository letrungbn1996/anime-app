package com.appkitten.animexa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.appkitten.animexa.databinding.OnboardingItemViewBinding
import com.appkitten.animexa.model.OnboardingItem

class OnboardingPagerAdapter(
    private val context: Context,
    private val onboardingItems: List<OnboardingItem>
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = OnboardingItemViewBinding.inflate(LayoutInflater.from(context), container, false)
        val currentItem = onboardingItems[position]

        // Set data to the views using ViewBinding
        binding.backgroundImageView.setImageResource(currentItem.imageResId)
        binding.title.text = currentItem.title
        binding.desc.text = currentItem.description

        container.addView(binding.root)
        return binding.root
    }

    override fun getCount(): Int = onboardingItems.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
