package com.hanyun.barcodescanner;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Created by Hanyun-05 on 2016/7/26.
 */
public class CameraParameters {
    public static final String KEY_DECODE_1D_PRODUCT = "preferences_decode_1D_product";
    public static final String KEY_DECODE_1D_INDUSTRIAL = "preferences_decode_1D_industrial";
    public static final String KEY_DECODE_QR = "preferences_decode_QR";
    public static final String KEY_DECODE_DATA_MATRIX = "preferences_decode_Data_Matrix";
    public static final String KEY_DECODE_AZTEC = "preferences_decode_Aztec";
    public static final String KEY_DECODE_PDF417 = "preferences_decode_PDF417";

    public static final String KEY_PLAY_BEEP = "preferences_play_beep";
    public static final String KEY_VIBRATE = "preferences_vibrate";
    public static final String KEY_FRONT_LIGHT_MODE = "preferences_front_light_mode";
    public static final String KEY_BULK_MODE = "preferences_bulk_mode";
    public static final String KEY_AUTO_FOCUS = "preferences_auto_focus";
    public static final String KEY_INVERT_SCAN = "preferences_invert_scan";
    public static final String KEY_BULK_MODE_SCAN_DELAY_MS = "preferences_bulk_mode_scan_delay";

    private SharedPreferences mPrefs;
    private Editor mPrefsEditor;

    public CameraParameters(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefsEditor = mPrefs.edit();
    }

    public void setPlayBeep(boolean playbeep) {
        mPrefsEditor.putBoolean(KEY_PLAY_BEEP, playbeep);
        mPrefsEditor.commit();
    }

    public void setVibrate(boolean vibrate) {
        mPrefsEditor.putBoolean(KEY_VIBRATE, vibrate);
        mPrefsEditor.commit();
    }

    public void setTorchOn(boolean torchon) {
        mPrefsEditor.putBoolean(KEY_FRONT_LIGHT_MODE, torchon);
        mPrefsEditor.commit();
    }

    public void setAutoFocus(boolean auto) {
        mPrefsEditor.putBoolean(KEY_AUTO_FOCUS, auto);
        mPrefsEditor.commit();
    }

    public void setInvertScan(boolean invertscan) {
        mPrefsEditor.putBoolean(KEY_INVERT_SCAN, invertscan);
        mPrefsEditor.commit();
    }

    public void setBulkScan(boolean bulkscan) {
        mPrefsEditor.putBoolean(KEY_BULK_MODE, bulkscan);
        mPrefsEditor.commit();
    }

    public void setBulkScanDelay(long millisecond) {
        mPrefsEditor.putLong(KEY_BULK_MODE_SCAN_DELAY_MS, millisecond);
        mPrefsEditor.commit();
    }

    public void setDecode1DProduct(boolean decode1dproduct) {
        mPrefsEditor.putBoolean(KEY_DECODE_1D_PRODUCT, decode1dproduct);
        mPrefsEditor.commit();
    }

    public void setDecode1DIndustrial(boolean decode1dindustrial) {
        mPrefsEditor.putBoolean(KEY_DECODE_1D_INDUSTRIAL, decode1dindustrial);
        mPrefsEditor.commit();
    }

    public void setDecodeQr(boolean decodeqr) {
        mPrefsEditor.putBoolean(KEY_DECODE_QR, decodeqr);
        mPrefsEditor.commit();
    }

    public void setDecodeDataMatrix(boolean decodedatamatrix) {
        mPrefsEditor.putBoolean(KEY_DECODE_DATA_MATRIX, decodedatamatrix);
        mPrefsEditor.commit();
    }

    public void setDecodeAztec(boolean decodeaztec) {
        mPrefsEditor.putBoolean(KEY_DECODE_AZTEC, decodeaztec);
        mPrefsEditor.commit();
    }

    public void setDecodePdf417(boolean decodepdf417) {
        mPrefsEditor.putBoolean(KEY_DECODE_PDF417, decodepdf417);
        mPrefsEditor.commit();
    }

    public boolean isPlayBeep() {
        return mPrefs.getBoolean(KEY_PLAY_BEEP, false);
    }

    public boolean isVibrate() {
        return mPrefs.getBoolean(KEY_VIBRATE, false);
    }

    public boolean isTorchOn() {
        return mPrefs.getBoolean(KEY_FRONT_LIGHT_MODE, false);
    }

    public boolean isAutoFocus() {
        return mPrefs.getBoolean(KEY_AUTO_FOCUS, true);
    }

    public boolean isInvertScan() {
        return mPrefs.getBoolean(KEY_INVERT_SCAN, false);
    }

    public boolean isBulkScan() {
        return mPrefs.getBoolean(KEY_BULK_MODE, false);
    }

    public long getBulkScanDelay() {
        return mPrefs.getLong(KEY_BULK_MODE_SCAN_DELAY_MS, 1000L);
    }

    public boolean isDecode1DProduct() {
        return mPrefs.getBoolean(KEY_DECODE_1D_PRODUCT, true);
    }

    public boolean isDecode1DIndustrial() {
        return mPrefs.getBoolean(KEY_DECODE_1D_INDUSTRIAL, true);
    }

    public boolean isDecodeQr() {
        return mPrefs.getBoolean(KEY_DECODE_QR, true);
    }

    public boolean isDecodeDataMatrix() {
        return mPrefs.getBoolean(KEY_DECODE_DATA_MATRIX, true);
    }

    public boolean isDecodeAztec() {
        return mPrefs.getBoolean(KEY_DECODE_AZTEC, false);
    }

    public boolean isDecodePdf417() {
        return mPrefs.getBoolean(KEY_DECODE_PDF417, false);
    }
}
