package com.appkitten.animexa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.appkitten.animexa.R;
import com.appkitten.animexa.adapter.LanguagesAdapter;
import com.appkitten.animexa.database.prefs.AdsPref;
import com.appkitten.animexa.database.prefs.SharedPref;
import com.appkitten.animexa.databinding.ActivityLanguagesBinding;
import com.appkitten.animexa.model.LanguageModel;
import com.appkitten.animexa.model.Menu;
import com.appkitten.animexa.util.AdsManager;
import com.appkitten.animexa.util.Constant;
import com.appkitten.animexa.util.LocaleUtils;
import com.appkitten.animexa.util.OnItemClickListener;
import com.appkitten.animexa.util.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class LanguagesActivity extends AppCompatActivity implements OnItemClickListener {

    private ActivityLanguagesBinding binding;
    SharedPref sharedPref;
    private ArrayList<LanguageModel> arrayList;
    private String selectedLanguage = "default";
    private String selectedLanguageName = "default";
    private LanguagesAdapter adapter;
    AdsManager adsManager;
    AdsPref adsPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.getTheme(this);
        binding = ActivityLanguagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPref = new SharedPref(this);
        adsPref = new AdsPref(this);
        adsManager = new AdsManager(this);

        adsManager.initializeAd();
        adsManager.updateConsentStatus();
        adsManager.loadAppOpenAd(adsPref.getIsAppOpenAdOnResume());
        if (!sharedPref.getInstalled()){
            Tools.setNativeAdStyle(this, binding.nativeAdView, adsPref.getNativeAdStylePostList());
            adsManager.loadNativeAd(adsPref.getIsNativeAdExitDialog(), adsPref.getNativeAdStylePostList());
        }else{
            adsManager.loadBannerAd(adsPref.getIsBannerHome());
        }
        selectedLanguage = sharedPref.getLanguageCode();
        selectedLanguageName = sharedPref.getLanguage();

        arrayList = new ArrayList<>();
        populateList();
        setAdapter();
        if (sharedPref.getIsDarkTheme()){
            binding.back.setColorFilter(ContextCompat.getColor(this, R.color.color_white), PorterDuff.Mode.SRC_ATOP);
        }
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!sharedPref.getLanguageCode().equals(selectedLanguage)){
                    sharedPref.setLanguageCode(selectedLanguage);
                    sharedPref.setLanguage(selectedLanguageName);
                    if (!Objects.equals(selectedLanguage, "default")){
                        LocaleUtils.Companion.setLocale(LanguagesActivity.this,selectedLanguage,true);
                    }else{
                        Locale currentLocale = LocaleUtils.Companion.getCurrentLocale(LanguagesActivity.this);
                        LocaleUtils.Companion.setLocale(LanguagesActivity.this,currentLocale.getLanguage(),false);
                    }
                }
                List<Menu> menus = new ArrayList<>();
                addMenu(menus);
                sharedPref.saveMenuList(menus);
                if (!sharedPref.getInstalled()){
                    sharedPref.saveInstalled(true);
                    Tools.postDelayed(() -> {
                        Intent intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }, 250);
                    finish();
                }else{
                    Tools.postDelayed(() -> {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }, 250);
                    finish();
                }
            }
        });
    }

    private void addMenu(List<Menu> menus) {
        menus.add(new Menu(getString(R.string.tab_menu_recent), Constant.Order.RECENT, Constant.Filter.WALLPAPER, "0"));
        menus.add(new Menu(getString(R.string.tab_menu_featured), Constant.Order.FEATURED, Constant.Filter.BOTH, "0"));
        menus.add(new Menu(getString(R.string.tab_menu_popular), Constant.Order.POPULAR, Constant.Filter.WALLPAPER, "0"));
        menus.add(new Menu(getString(R.string.tab_menu_random), Constant.Order.RANDOM, Constant.Filter.WALLPAPER, "0"));
        menus.add(new Menu(getString(R.string.tab_menu_live), Constant.Order.RECENT, Constant.Filter.LIVE_WALLPAPER, "0"));
    }
    private void setAdapter() {
        adapter = new LanguagesAdapter(arrayList, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    private void populateList() {
        arrayList.add(new LanguageModel(false, "System language", "default", R.drawable.ic_system));
        arrayList.add(new LanguageModel(false, "English", "en", R.drawable.ic_english));
        arrayList.add(new LanguageModel(false, "German", "de", R.drawable.ic_german));
        arrayList.add(new LanguageModel(false, "Spanish (Spain)", "es", R.drawable.ic_spanish));
        arrayList.add(new LanguageModel(false, "Spanish (Latin America)", "es", R.drawable.ic_spanish_latin));
        arrayList.add(new LanguageModel(false, "French (France)", "fr", R.drawable.ic_french));
        arrayList.add(new LanguageModel(false, "Indonesian", "id", R.drawable.ic_indonesian));
        arrayList.add(new LanguageModel(false, "Italian", "it", R.drawable.ic_italian));
        arrayList.add(new LanguageModel(false, "Japanese", "ja", R.drawable.ic_japanese));
        arrayList.add(new LanguageModel(false, "Portuguese (Brazil)", "pt", R.drawable.ic_portuguese));
        arrayList.add(new LanguageModel(false, "Thai", "th", R.drawable.ic_thai));
        arrayList.add(new LanguageModel(false, "Turkish", "tr", R.drawable.ic_turkish));
        arrayList.add(new LanguageModel(false, "Vietnamese", "vi", R.drawable.ic_veitnamese));
        arrayList.add(new LanguageModel(false, "Chinese (Simplified)", "zh", R.drawable.ic_chinese));

        for (int i = 0;i<arrayList.size();i++){
            if (selectedLanguageName.equals(arrayList.get(i).getLanguageName())){
                arrayList.get(i).setSelected(true);
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        selectedLanguage = arrayList.get(position).getLanguageCode();
        selectedLanguageName = arrayList.get(position).getLanguageName();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).setSelected(i == position);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adsManager.resumeBannerAd(adsPref.getIsBannerHome());
    }
}