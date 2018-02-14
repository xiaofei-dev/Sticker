package com.kittendev.sticker.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtil {

    public static void openUserPage(Context context, String user) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.coolapk.market", "com.coolapk.market.view.AppLinkActivity");
        intent.setComponent(componentName);
        intent.setData(Uri.parse("https://coolapk.com/u/" + user));
        context.startActivity(intent);
    }

    public static void openApkPage(Context context, String apk) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.coolapk.market", "com.coolapk.market.view.AppLinkActivity");
        intent.setComponent(componentName);
        intent.setData(Uri.parse("https://coolapk.com/apk/" + apk));
        context.startActivity(intent);
    }

    public static void joinQQGroup(Context context, String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        try {
            context.startActivity(intent);
        } catch (Exception e) {

        }
    }

    public static void openBrower(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

}
