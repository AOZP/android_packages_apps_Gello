/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.browser.preferences;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.android.browser.BrowserSettings;
import com.android.browser.PreferenceKeys;
import com.android.browser.R;

import java.text.NumberFormat;

public class AccessibilityPreferencesFragment
        implements Preference.OnPreferenceChangeListener {

    NumberFormat mFormat;
    PreferenceFragment mFragment;

    AccessibilityPreferencesFragment(PreferenceFragment fragment) {
        mFragment = fragment;
        BrowserSettings settings = BrowserSettings.getInstance();
        mFormat = NumberFormat.getPercentInstance();

        Preference e = mFragment.findPreference(PreferenceKeys.PREF_MIN_FONT_SIZE);
        e.setOnPreferenceChangeListener(this);
        updateMinFontSummary(e, settings.getMinimumFontSize());
        e = mFragment.findPreference(PreferenceKeys.PREF_TEXT_ZOOM);
        e.setOnPreferenceChangeListener(this);
        updateTextZoomSummary(e, settings.getTextZoom());
        /* SWE: Comment out double tap zoom feature
        e = findPreference(PreferenceKeys.PREF_DOUBLE_TAP_ZOOM);
        e.setOnPreferenceChangeListener(this);
        updateDoubleTapZoomSummary(e, settings.getDoubleTapZoom());
        */
        /*
         * SWE_TODO: Commented out functionality for inverted rendering
         * (as well as corresponding sections below)
        e = findPreference(PreferenceKeys.PREF_INVERTED_CONTRAST);
        e.setOnPreferenceChangeListener(this);
        updateInvertedContrastSummary(e, (int) (settings.getInvertedContrast() * 100));
        */
    }

    void updateMinFontSummary(Preference pref, int minFontSize) {
        Context c = mFragment.getActivity();
        pref.setSummary(c.getString(R.string.pref_min_font_size_value, minFontSize));
    }

    void updateTextZoomSummary(Preference pref, int textZoom) {
        pref.setSummary(mFormat.format(textZoom / 100.0));
    }

    void updateDoubleTapZoomSummary(Preference pref, int doubleTapZoom) {
        pref.setSummary(mFormat.format(doubleTapZoom / 100.0));
    }

    /*
    void updateInvertedContrastSummary(Preference pref, int contrast) {
        pref.setSummary(mFormat.format(contrast / 100.0));
    }
    */

    @Override
    public boolean onPreferenceChange(Preference pref, Object objValue) {
        if (mFragment.getActivity() == null) {
            // We aren't attached, so don't accept preferences changes from the
            // invisible UI.
            return false;
        }

        if (PreferenceKeys.PREF_MIN_FONT_SIZE.equals(pref.getKey())) {
            updateMinFontSummary(pref, BrowserSettings
                    .getAdjustedMinimumFontSize((Integer) objValue));
        }
        if (PreferenceKeys.PREF_TEXT_ZOOM.equals(pref.getKey())) {
            BrowserSettings settings = BrowserSettings.getInstance();
            updateTextZoomSummary(pref, settings
                    .getAdjustedTextZoom((Integer) objValue));
        }
        if (PreferenceKeys.PREF_DOUBLE_TAP_ZOOM.equals(pref.getKey())) {
            BrowserSettings settings = BrowserSettings.getInstance();
            updateDoubleTapZoomSummary(pref, settings
                    .getAdjustedDoubleTapZoom((Integer) objValue));
        }
        /*
        if (PreferenceKeys.PREF_INVERTED_CONTRAST.equals(pref.getKey())) {
            updateInvertedContrastSummary(pref,
                    (int) ((10 + (Integer) objValue) * 10));
        }
        */

        return true;
    }

}
