package com.appkitten.animexa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import com.appkitten.animexa.R;
import com.appkitten.animexa.adapter.OnboardingPagerAdapter;
import com.appkitten.animexa.database.prefs.AdsPref;
import com.appkitten.animexa.databinding.ActivityOnBoardingBinding;
import com.appkitten.animexa.model.OnboardingItem;
import com.appkitten.animexa.util.AdsManager;
import com.appkitten.animexa.util.Tools;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {
    private ActivityOnBoardingBinding binding;
    private int pagerPosition;
    AdsManager adsManager;
    AdsPref adsPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adsPref = new AdsPref(this);
        adsManager = new AdsManager(this);

        adsManager.initializeAd();
        adsManager.updateConsentStatus();
        adsManager.loadAppOpenAd(adsPref.getIsAppOpenAdOnResume());
        Tools.setNativeAdStyle(this, binding.nativeAdView, adsPref.getNativeAdStylePostList());
        adsManager.loadNativeAd(adsPref.getIsNativeAdExitDialog(), adsPref.getNativeAdStylePostList());

        List<OnboardingItem> onboardingList = new ArrayList<>();
        onboardingList.add(new OnboardingItem(R.drawable.onboarding_1, getString(R.string.personalize_with_anime_wallpapers), getString(R.string.explore_a_huge_collection_of_top_tier_anime_wallpapers_for_any_screen)));
        onboardingList.add(new OnboardingItem(R.drawable.onboarding_2, getString(R.string.live_wallpapers_anime_magic), getString(R.string.dive_into_the_dynamic_anime_world_with_animated_wallpapers)));
        onboardingList.add(new OnboardingItem(R.drawable.onboarding_3, getString(R.string.your_favorites_your_way), getString(R.string.craft_a_personalized_collection_of_anime_favorites_with_animexa)));

        OnboardingPagerAdapter adapter = new OnboardingPagerAdapter(this, onboardingList);
        binding.viewPager.setAdapter(adapter);

        hideSystemUI();
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pagerPosition = position;
                if (position==0){
                    binding.one.setColorFilter(getResources().getColor(R.color.navigator_selector), PorterDuff.Mode.SRC_IN);
                    binding.two.setColorFilter(getResources().getColor(R.color.navigator_selector_gray), PorterDuff.Mode.SRC_IN);
                    binding.three.setColorFilter(getResources().getColor(R.color.navigator_selector_gray), PorterDuff.Mode.SRC_IN);
                    binding.next.setText(getString(R.string.next));
                }else if(position==1){
                    binding.one.setColorFilter(getResources().getColor(R.color.navigator_selector_gray), PorterDuff.Mode.SRC_IN);
                    binding.two.setColorFilter(getResources().getColor(R.color.navigator_selector), PorterDuff.Mode.SRC_IN);
                    binding.three.setColorFilter(getResources().getColor(R.color.navigator_selector_gray), PorterDuff.Mode.SRC_IN);
                    binding.next.setText(getString(R.string.next));
                }else{
                    binding.one.setColorFilter(getResources().getColor(R.color.navigator_selector_gray), PorterDuff.Mode.SRC_IN);
                    binding.two.setColorFilter(getResources().getColor(R.color.navigator_selector_gray), PorterDuff.Mode.SRC_IN);
                    binding.three.setColorFilter(getResources().getColor(R.color.navigator_selector), PorterDuff.Mode.SRC_IN);
                    binding.next.setText(getString(R.string.get_started));
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.next.getText().equals(getString(R.string.next))){
                    binding.viewPager.setCurrentItem(pagerPosition++);
                }else{
                    startActivity(new Intent(OnBoardingActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void hideSystemUI() {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}