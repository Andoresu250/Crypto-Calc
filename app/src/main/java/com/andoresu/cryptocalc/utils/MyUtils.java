package com.andoresu.cryptocalc.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.andoresu.cryptocalc.R;
import com.andoresu.cryptocalc.client.ErrorResponse;
import com.bumptech.glide.request.RequestOptions;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;

import static com.andoresu.cryptocalc.utils.BaseFragment.BASE_TAG;

@SuppressLint("LogNotTimber")
public class MyUtils {

    private static final String TAG = BASE_TAG + MyUtils.class.getSimpleName();

    public static boolean checkNullEmpty(String s) {
        return null == s || s.trim().isEmpty();
    }

    public static boolean checkNumeric(String s) {
        Log.i(TAG, "checkNumeric: " + s + " isNumeric? " + s.matches("\\d"));
        return s.matches("\\d+");
    }

    public static boolean checkLength(String s, int length) {
        return s.length() == length;
    }

    public static boolean checkOnlyLetters(String s, boolean onlyUpperCase) {
        String regex = onlyUpperCase ? "[A-Z]+" : "[a-zA-Z]+";
        return s.matches(regex);
    }

    public static Intent phoneIntent(String number){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        return intent;
    }

    @SuppressLint("CheckResult")
    public static RequestOptions glideRequestOptions(Context context){
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loading_image);
        requestOptions.error(R.drawable.imagen_disponible);

        return requestOptions;
    }

    public static <T> T getFirst(List<T> list){
        try {
            return list.get(0);
        }catch (Exception e){
            return null;
        }
    }


    public static String stringToUri(String s){

//        s = s.replaceAll("%", "%" + (int) "%".charAt(0) );
        s = s.replaceAll("#", "%23" );
//        s = s.replaceAll("[$]", "%" + (int) "$".charAt(0) );
//        s = s.replaceAll("&", "%" + (int) "&".charAt(0) );
//        s = s.replaceAll("'", "%" + (int) "'".charAt(0) );
//        s = s.replaceAll("[(]", "%" + (int) "(".charAt(0) );
//        s = s.replaceAll("[)]", "%" + (int) ")".charAt(0) );
//        s = s.replaceAll("[*]", "%" + (int) "*".charAt(0) );
//        s = s.replaceAll("[+]", "%" + (int) "+".charAt(0) );
//        s = s.replaceAll(",", "%" + (int) ",".charAt(0) );
//        s = s.replaceAll("/", "%" + (int) "/".charAt(0) );
//        s = s.replaceAll(":", "%" + (int) ":".charAt(0) );
//        s = s.replaceAll(";", "%" + (int) ";".charAt(0) );
//        s = s.replaceAll("=", "%" + (int) "=".charAt(0) );
//        s = s.replaceAll("[?]", "%" + (int) "?".charAt(0) );
//        s = s.replaceAll("@", "%" + (int) "@".charAt(0) );
//        s = s.replaceAll("\\[", "%" + (int) "[".charAt(0) );
//        s = s.replaceAll("]", "%" + (int) "]".charAt(0) );
//        s = s.replaceAll("\"", "%" + (int) "\"".charAt(0) );
        s = s.replaceAll(" ", "%20");
        s = s.replaceAll("-", "%2D" );
//        s = s.replaceAll("\\.", "%" + (int) ".".charAt(0) );
//        s = s.replaceAll("<", "%" + (int) "<".charAt(0) );
//        s = s.replaceAll(">", "%" + (int) ">".charAt(0) );
//        s = s.replaceAll("\\\\", "%" + (int) "\\".charAt(0) );
//        s = s.replaceAll("\\^", "%" + (int) "^".charAt(0) );
//        s = s.replaceAll("_", "%" + (int) "_".charAt(0) );
//        s = s.replaceAll("`", "%" + (int) "`".charAt(0) );
//        s = s.replaceAll("[{]", "%" + (int) "{".charAt(0) );
//        s = s.replaceAll("[|]", "%" + (int) "|".charAt(0) );
//        s = s.replaceAll("[}]", "%" + (int) "}".charAt(0) );
//        s = s.replaceAll("~", "%" + (int) "~".charAt(0) );
//        s = s.replaceAll("´", "%" + (int) "´".charAt(0) );
//        s = s.replaceAll("!", "%" + (int) "!".charAt(0) );
        return s;
    }


    public static void showDialog(@NonNull Context context, @NonNull String title, @NonNull String message, Drawable icon, @ColorRes Integer iconColor) {
        showDialog(context, title, message, icon, iconColor, null, null);
    }

    public static void showDialog(@NonNull Context context, @NonNull String title, @NonNull String message, Drawable icon, @ColorRes Integer iconColor,
                                  DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);

        if (positiveListener == null) {
            builder.setPositiveButton(R.string.error_dialog_ok, (dialogInterface, i) -> dialogInterface.dismiss());
        } else {
            builder.setPositiveButton(R.string.error_dialog_ok, positiveListener);
        }

        if (negativeListener == null) {
            builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss());
        } else {
            builder.setNegativeButton(R.string.cancel, negativeListener);
        }

        if (icon != null) {
            if (iconColor != null) {
                icon = DrawableCompat.wrap(icon);
                DrawableCompat.setTint(icon, context.getResources().getColor(iconColor));
            }
            builder.setIcon(icon);
        }

        builder.show();
    }

    public static void showDialog(@NonNull Context context, @NonNull String title, @NonNull String message, Integer icon, @ColorRes Integer iconColor,
                                  DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        Drawable i = context.getDrawable(icon);
        showDialog(context, title, message, i, iconColor, positiveListener, negativeListener);
    }

    public static void showDialog(@NonNull Context context, @StringRes int title, @StringRes int message, @DrawableRes Integer icon, @ColorRes Integer iconColor) {
        String t = context.getString(title);
        String m = context.getString(message);
        Drawable i = context.getDrawable(icon);
        showDialog(context, t, m, i, iconColor);
    }

    public static void showDialog(@NonNull Context context, @StringRes int title, @NonNull String message, @DrawableRes Integer icon, @ColorRes Integer iconColor) {
        String t = context.getString(title);
        Drawable i = context.getDrawable(icon);
        showDialog(context, t, message, i, iconColor);
    }

    public static void showDialog(@NonNull Context context, @StringRes int title, @NonNull String message, @DrawableRes Integer icon, @ColorRes Integer iconColor,
                                  DialogInterface.OnClickListener positiveListener) {
        String t = context.getString(title);
        Drawable i = context.getDrawable(icon);
        showDialog(context, t, message, i, iconColor, positiveListener, null);
    }

    public static void showDialog(@NonNull Context context, @StringRes int title, @NonNull String message, @DrawableRes Integer icon, @ColorRes Integer iconColor,
                                  DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        String t = context.getString(title);
        Drawable i = context.getDrawable(icon);
        showDialog(context, t, message, i, iconColor, positiveListener, negativeListener);
    }

    public static void showDialog(@NonNull Context context, @StringRes int title, @StringRes int message, @DrawableRes Integer icon) {
        String t = context.getString(title);
        String m = context.getString(message);
        Drawable i = context.getDrawable(icon);
        showDialog(context, t, m, i, null);
    }

    public static void showDialog(@NonNull Context context, @StringRes int title, @NonNull String message, @DrawableRes Integer icon) {
        String t = context.getString(title);
        Drawable i = context.getDrawable(icon);
        showDialog(context, t, message, i, null);
    }

    public static void showErrorDialog(@NonNull Context context, String error) {
        showDialog(context, R.string.error_dialog_title, error, R.drawable.ic_error, R.color.colorAlertError);
    }

    public static void showErrorDialog(@NonNull Context context, String[] errors) {
        StringBuilder error = new StringBuilder();
        for (String e : errors) {
            error.append(e).append("\n");
        }
        showDialog(context, R.string.error_dialog_title, error.toString(), R.drawable.ic_error, R.color.colorAlertError);
    }

    public static void showErrorDialog(@NonNull Context context, String error, DialogInterface.OnClickListener positiveListener) {
        showDialog(context, R.string.error_dialog_title, error, R.drawable.ic_error, R.color.colorAlertError, positiveListener);
    }

    public static void showErrorDialog(@NonNull Context context, String title, String error, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        showDialog(context, title, error, R.drawable.ic_error, R.color.colorAlertError, positiveListener, negativeListener);
    }

    public static void showErrorDialog(@NonNull Context context, String title, String error, DialogInterface.OnClickListener positiveListener) {
        showDialog(context, title, error, R.drawable.ic_error, R.color.colorAlertError, positiveListener, null);
    }

    public static void showErrorDialog(@NonNull Context context, String error, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        showDialog(context, R.string.error_dialog_title, error, R.drawable.ic_error, R.color.colorAlertError, positiveListener, negativeListener);
    }

    public static void showErrorDialog(@NonNull Context context, ErrorResponse errorResponse) {
        showErrorDialog(context, errorResponse.toString());
    }

    public static void showSuccessDialog(@NonNull Context context, String title, String msg) {
        showDialog(context, title, msg, context.getDrawable(R.drawable.ic_check), R.color.colorAccent);
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    public static String fillSpaces(char fillChar, int length, String s) {
        if (s.length() >= length) {
            return s;
        } else {
            int spaces = length - s.length();
            return s + new String(new char[spaces]).replace('\0', fillChar);
        }
    }

    public static void closeKeyboard(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static ButterKnife.Action<View> REMOVE_ERRORS = (view, index) -> {
        if (view instanceof EditText) {
            ((EditText) view).setError(null);
        } else if (view instanceof TextInputLayout) {
            ((TextInputLayout) view).setError(null);
        }

    };

    public static ButterKnife.Action<View> TOGGLE_VIEW_ENABLE_STATE = (view, index) -> {
        if(view.isEnabled()){
            view.setEnabled(false);
        } else {
            view .setEnabled(true);
        }
    };

    public static ButterKnife.Action<View> ENABLE_VIEW = (view, index) -> view .setEnabled(true);

    public static ButterKnife.Action<View> DISABLE_VIEW = (view, index) -> view.setEnabled(false);

    public static ButterKnife.Action<View> NON_FOCUSABLE_VIEW = (view, index) -> view.setFocusable(false);

    public static ButterKnife.Action<View> HIDE_VIEW = (view, index) -> view.setVisibility(View.GONE);

    public static boolean isNightMode(){
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    }

    public static String getMetadata(Context context, String name) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                return appInfo.metaData.getString(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String formatTime(Integer secs) {
        if(secs == null){
            return "00:00:00";
        }
        return String.format("%02d:%02d:%02d", (secs % 86400) / 3600, (secs % 3600) / 60, secs % 60);
    }

    public static String TIME_REGEX = "^\\d\\d:\\d\\d";


    public static boolean checkTimeInterval(String time, String startTime, String endTime){

        Log.i(TAG, "checkTimeInterval: time " + time);
        Log.i(TAG, "checkTimeInterval: startTime " + startTime);
        Log.i(TAG, "checkTimeInterval: endTime " + endTime);

        if(!time.matches(TIME_REGEX)){
            Log.e(TAG, "checkTimeInterval: time its not a time");
            return false;
        }
        if(!startTime.matches(TIME_REGEX)){
            Log.e(TAG, "checkTimeInterval: startTime its not a time");
            return false;
        }
        if(!endTime.matches(TIME_REGEX)){
            Log.e(TAG, "checkTimeInterval: endTime its not a time");
            return false;
        }

        int timeHour = Integer.parseInt(time.split(":")[0]);
        int timeMinutes = Integer.parseInt(time.split(":")[1]);

        int startTimeHour = Integer.parseInt(startTime.split(":")[0]);
        int startTimeMinutes = Integer.parseInt(startTime.split(":")[1]);

        int endTimeHour = Integer.parseInt(endTime.split(":")[0]);
        int endTimeMinutes = Integer.parseInt(endTime.split(":")[1]);

        Log.i(TAG, "checkTimeInterval: timeHour  " + timeHour );
        Log.i(TAG, "checkTimeInterval: timeMinutes  " + timeMinutes );

        Log.i(TAG, "checkTimeInterval: startTimeHour  " + startTimeHour );
        Log.i(TAG, "checkTimeInterval: startTimeMinutes  " + startTimeMinutes );

        Log.i(TAG, "checkTimeInterval: endTimeHour  " + endTimeHour );
        Log.i(TAG, "checkTimeInterval: endTimeMinutes  " + endTimeMinutes );

        if(compareTimes(startTime, endTime) > 0){
            if(timeHour == startTimeHour){
                return timeMinutes >= startTimeMinutes;
            }else if(timeHour == endTimeHour){
                return timeMinutes <= endTimeMinutes;
            }else{
                return (timeHour >= startTimeHour && timeHour <= 23) || (timeHour >= 0 && timeHour <= endTimeHour);
            }
        }else{
            if(timeHour == startTimeHour){
                return timeMinutes >= startTimeMinutes;
            }else if(timeHour == endTimeHour){
                return timeMinutes <= endTimeMinutes;
            }else{
                return timeHour >= startTimeHour && timeHour <= endTimeHour;
            }
        }

    }

    public static boolean checkTimeInterval(Calendar calendar, String startTime, String endTime){

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String hourString = (hour < 10 ? "0" : "") + hour;
        String minuteString = (minute < 10 ? "0" : "") + minute;
        String time = hourString + ":" + minuteString;
        return checkTimeInterval(time, startTime, endTime);

    }

    public static int compareTimes(String time1, String time2){

        int timeHour1 = Integer.parseInt(time1.split(":")[0]);
        int timeMinutes1 = Integer.parseInt(time1.split(":")[1]);

        int timeHour2 = Integer.parseInt(time2.split(":")[0]);
        int timeMinutes2 = Integer.parseInt(time2.split(":")[1]);

        if(timeHour1 == timeHour2){
            if(timeMinutes1 == timeMinutes2){
                return 0;
            }else if(timeMinutes1 < timeMinutes2){
                return -1;
            }else{
                return 1;
            }
        }else if(timeHour1 < timeHour2){
            return -1;
        }else {
            return 1;
        }

    }

    public static void pairTextViewWithView(TextView textView, View editText, Context context){
        editText.setOnFocusChangeListener((view, hasFocus) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView.setTextColor(hasFocus ? context.getColor(R.color.colorAccent): context.getColor(R.color.colorLabelText));
            }else{
                textView.setTextColor(hasFocus ? ContextCompat.getColor(context, R.color.colorAccent): ContextCompat.getColor(context, R.color.colorLabelText));
            }
        });
    }

    @SuppressLint("DefaultLocale")
    public static String toMoney(Double price){
        if(price == null){
            return "";
        }
        return ("$ " + String.format("%,.2f", price));
//        return ("$ " + String.format("%,.2f", price)).replaceAll(",00", "");
    }

    @SuppressLint("DefaultLocale")
    public static String toMoney(Integer price){
        if(price == null){
            return "";
        }
        return ("$ " + String.format("%,.2f", price));
//        return ("$ " + String.format("%,.2f", price)).replaceAll(",00", "");
    }

    public static String toDistance(double meters){
        return DistanceFormatter.format((int) meters);
    }

    @SuppressLint("SimpleDateFormat")
    public static String toTime(int seconds){
        return TimeFormatter.format(seconds);
    }


    public static Bitmap drawableToBitmap(int drawable, Resources res, int color){
        Bitmap bitmap = BitmapFactory.decodeResource(res, drawable);
        Bitmap resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() - 1, bitmap.getHeight() - 1);

        ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);

        Paint markerPaint = new Paint();
        markerPaint.setColorFilter(filter);

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, markerPaint);

        return resultBitmap;

    }

    public static Bitmap drawableToBitmap(int drawable, Resources res){

        return drawableToBitmap(drawable, res, res.getColor(R.color.colorAccent));

    }

    public static boolean checkRedConnectivity(Context context){
        try{
            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;
    public static final int NETWORK_STATUS_NOT_CONNECTED = 0;
    public static final int NETWORK_STATUS_WIFI = 1;
    public static final int NETWORK_STATUS_MOBILE = 2;

    public static int getConnectivityStatus(Context context) {
        try{
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
            if (null != activeNetwork) {
                if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return TYPE_NOT_CONNECTED;
    }

    public static int getConnectivityStatusCode(Context context) {
        int conn = getConnectivityStatus(context);
        int status = 0;
        if (conn == TYPE_WIFI) {
            status = NETWORK_STATUS_WIFI;
        } else if (conn == TYPE_MOBILE) {
            status  = NETWORK_STATUS_MOBILE;
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = NETWORK_STATUS_NOT_CONNECTED;
        }
        return status;
    }

    public static String getConnectivityStatusString(int status){
        switch (status){
            case TYPE_WIFI:
                return "WIFI";
            case TYPE_MOBILE:
                return "MOBILE";
            default:
                return "NOT CONNECTED";
        }
    }

    public static String getConnectivityStatusString(Context context){
        return getConnectivityStatusString(getConnectivityStatusCode(context));
    }


    public static CharSequence removeTrailingLineFeed(@NonNull CharSequence text) {
        while (text.charAt(text.length() - 1) == '\n') {
            text = text.subSequence(0, text.length() - 1);
        }
        return text;
    }

    public static boolean checkHasAllPermissions(Context context){
        boolean res = true;
        for(String per : Constants.permissions){
            if (ContextCompat.checkSelfPermission(context, per) != PackageManager.PERMISSION_GRANTED){
                res = false;
                break;
            }
        }
        return res;
    }

    public static void requestPermissions(Activity activity){
        ActivityCompat.requestPermissions(activity, Constants.permissions, Constants.REQUEST_PERMISSIONS);
    }

}
