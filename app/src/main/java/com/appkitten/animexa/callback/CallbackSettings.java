package com.appkitten.animexa.callback;

import com.appkitten.animexa.model.AdStatus;
import com.appkitten.animexa.model.Ads;
import com.appkitten.animexa.model.App;
import com.appkitten.animexa.model.Menu;
import com.appkitten.animexa.model.Placement;
import com.appkitten.animexa.model.Settings;

import java.util.ArrayList;
import java.util.List;

public class CallbackSettings {

    public String status;
    public App app = null;
    public List<Menu> menus = new ArrayList<>();
    public Settings settings = null;
    public Ads ads = null;
    public AdStatus ads_status = null;
    public Placement ads_placement = null;

}
