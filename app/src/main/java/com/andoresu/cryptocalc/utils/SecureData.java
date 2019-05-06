package com.andoresu.cryptocalc.utils;

import android.annotation.SuppressLint;

import de.adorsys.android.securestoragelibrary.SecurePreferences;

@SuppressLint("LogNotTimber")
public class SecureData {

    private final static String PHONES_SAVED = "PHONES_SAVED";

    public static void savePhones(boolean saved) {
        if (SecurePreferences.contains(PHONES_SAVED)) {
            SecurePreferences.removeValue(PHONES_SAVED);
        }
        SecurePreferences.setValue(PHONES_SAVED, saved);
    }


    public static boolean isSaved() {
        if (!SecurePreferences.contains(PHONES_SAVED)) {
            return false;
        }
        return SecurePreferences.getBooleanValue(PHONES_SAVED, false);
    }
}