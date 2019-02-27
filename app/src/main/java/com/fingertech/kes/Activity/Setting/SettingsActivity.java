package com.fingertech.kes.Activity.Setting;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.fingertech.kes.R;
import com.fingertech.kes.Service.DBHelper;

public class  SettingsActivity extends  PreferenceFragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }


//    @Override protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//
//        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainSettingPragment()).commit();
//    }
//
//    public static class MainSettingPragment extends PreferenceFragment{
//
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.preference);
//            bindPreferenceSummaryToValue(findPreference(getString(R.string.Key_SW_Language)));
//            bindPreferenceSummaryToValue(findPreference(getString(R.string.key_notifications_new_message_ringtone)));
//
//        }
//    }
//
//    private static void bindPreferenceSummaryToValue(Preference preference) {
//        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
//        if (preference instanceof ListPreference
//                || preference instanceof EditTextPreference) {
//            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
//                    PreferenceManager
//                            .getDefaultSharedPreferences(preference.getContext())
//                            .getString(preference.getKey(), ""));
//        } else if (preference instanceof SwitchPreference) {
//            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
//                    PreferenceManager
//                            .getDefaultSharedPreferences(preference.getContext())
//                            .getBoolean(preference.getKey(),true));
//        }
//    }
//
//
//
//
//
//    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
//        @Override
//        public boolean onPreferenceChange(Preference preference, Object newValue) {
//            String stringValue = newValue.toString();
//
//            if (preference instanceof ListPreference) {
//                // For list preferences, look up the correct display value in
//                // the preference's 'entries' list.
//                ListPreference listPreference = (ListPreference) preference;
//                int index = listPreference.findIndexOfValue(stringValue);
//
//                // Set the summary to reflect the new value.
//                preference.setSummary(
//                        index >= 0
//                                ? listPreference.getEntries()[index]
//                                : null);
//
//
//            } else if (preference instanceof RingtonePreference) {
//                // For ringtone preferences, look up the correct display value
//                // using RingtoneManager.
//                if (TextUtils.isEmpty(stringValue)) {
//                    // Empty values correspond to 'silent' (no ringtone).
//                    preference.setSummary(R.string.pref_ringtone_silent);
//
//                } else {
//                    Ringtone ringtone = RingtoneManager.getRingtone(
//                            preference.getContext(), Uri.parse(stringValue));
//
//                    if (ringtone == null) {
//                        // Clear the summary if there was a lookup error.
//                        preference.setSummary(R.string.summary_choose_ringtone);
//                    } else {
//                        // Set the summary to reflect the new ringtone display
//                        // name.
//                        String name = ringtone.getTitle(preference.getContext());
//                        preference.setSummary(name);
//                    }
//                }
//
//            } else if (preference instanceof SwitchPreference) {
//                preference.setDefaultValue((stringValue.contains("t")));
//            } else {
//                // For all other preferences, set the summary to the value's
//                // simple string representation.
//                preference.setSummary(R.string.Tv_english);
//            }
//            return true;
//        }
//    };
//
//
//
////    /**
////     * Email client intent to send support mail
////     * Appends the necessary device information to email body
////     * useful when providing support
////     */
////    public static void sendFeedback(Context context) {
////        String body = null;
////        try {
////            body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
////            body = "\n\n-----------------------------\nPlease don't remove this information\n Device OS: Android \n Device OS version: " +
////                    Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
////                    "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
////        } catch (PackageManager.NameNotFoundException e) {
////        }
////        Intent intent = new Intent(Intent.ACTION_SEND);
////        intent.setType("message/rfc822");
////        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"kes.developer@gmail.com"});
////        intent.putExtra(Intent.EXTRA_SUBJECT, "Query from android app");
////        intent.putExtra(Intent.EXTRA_TEXT, body);
////
////    }




}