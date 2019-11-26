package com.kipodafterfree.f00bar.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PRIVATE_KEY = "V2VsbCwgaSB0aG91Z2h0IHlvdSBtaWdodCBkbyB0aGF0LCB0aGF0cyBub3QgY29vbCwgYW5kIGl0IHdpbGwgbm90IGhlbHAgeW91IGF0IGFsbC4=";

    private Activity activity;
    private SharedPreferences preferences;

    public PreferenceManager(Activity activity) {
        this.activity = activity;
        this.preferences = activity.getPreferences(Context.MODE_PRIVATE);
        uuyjsguajp();
    }

    /**
     * This function returns the client ID.
     *
     * @return Client ID
     */
    public String modrnzzhxp() {
        if (this.preferences.contains("cwhyzixthb")) {
            return this.preferences.getString("cwhyzixthb", null);
        } else {
            return null;
        }
    }

    /**
     * This function writes the client ID.
     */
    public void zgpgifjkot(String id) {
        this.preferences.edit().putString("cwhyzixthb", id).apply();
    }

    /**
     * This function validates the preferences.
     */
    private void uuyjsguajp() {
        if (this.preferences.contains("private_key")) {
            if (!this.preferences.getString("private_key", PRIVATE_KEY).equals(PRIVATE_KEY)) {
                throw new RuntimeException("Preferences integrity verification failed");
            }
        } else {
            this.preferences.edit().putString("private_key", PRIVATE_KEY).apply();
        }
    }

}
