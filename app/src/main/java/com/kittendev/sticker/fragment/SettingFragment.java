package com.kittendev.sticker.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.kittendev.sticker.R;
import com.kittendev.sticker.adapter.SourceListViewAdapter;
import com.kittendev.sticker.util.IntentUtil;

public class SettingFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {

    private Context context;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_setting);
        Preference version = findPreference("version");
        Preference designer = findPreference("designer");
        Preference developer = findPreference("developer");
        Preference github = findPreference("github");
        Preference source = findPreference("source");
        Preference nanoiconpack = findPreference("nanoiconpack");
        Preference stickerGroup = findPreference("stickerGroup");
        Preference geekGroup = findPreference("geekGroup");
        Preference kittenGroup = findPreference("kittenGroup");
        Preference donate = findPreference("donate");
        nanoiconpack.setOnPreferenceClickListener(this);
        version.setOnPreferenceClickListener(this);
        designer.setOnPreferenceClickListener(this);
        developer.setOnPreferenceClickListener(this);
        github.setOnPreferenceClickListener(this);
        source.setOnPreferenceClickListener(this);
        stickerGroup.setOnPreferenceClickListener(this);
        geekGroup.setOnPreferenceClickListener(this);
        kittenGroup.setOnPreferenceClickListener(this);
        donate.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "version":
                IntentUtil.openApkPage(context, "com.kittendev.sticker");
                break;
            case "designer":
                IntentUtil.openUserPage(context, "531994");
                break;
            case "developer":
                IntentUtil.openUserPage(context, "926055");
                break;
            case "github":
                IntentUtil.openBrower(context, "https://github.com/kittendev/sticker");
                break;
            case "nanoiconpack":
                IntentUtil.openBrower(context, "https://github.com/by-syk/NanoIconPack");
                break;
            case "source":
                AlertDialog.Builder sourceBuilder = new AlertDialog.Builder(context);
                sourceBuilder.setTitle("");
                View view = LayoutInflater.from(context).inflate(R.layout.view_source, null, false);
                sourceBuilder.setView(view);
                ((ListView)view.findViewById(R.id.view_source_listView)).setAdapter(new SourceListViewAdapter(context));
                sourceBuilder.show();
                break;
            case "stickerGroup":
                IntentUtil.joinQQGroup(context, "CGpix9u02Mh-JASluqZCPkSOJ1wWUKA3");
                break;
            case "geekGroup":
                IntentUtil.joinQQGroup(context, "ZRS-2TuxnJ2MbK6k6thuydim0rGZuyJi");
                break;
            case "kittenGroup":
                IntentUtil.joinQQGroup(context, "vK1CryOYRqxfhbDRerai_YUQjH28Iau8");
                break;
            case "donate":
                AlertDialog.Builder donateBuilder = new AlertDialog.Builder(context);
                donateBuilder.setTitle("");
                donateBuilder.setView(LayoutInflater.from(context).inflate(R.layout.view_donate, null, false));
                donateBuilder.show();
                break;

        }
        return true;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
