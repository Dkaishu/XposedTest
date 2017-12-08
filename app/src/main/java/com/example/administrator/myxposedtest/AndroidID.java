package com.example.administrator.myxposedtest;

import android.content.ContentResolver;
import android.provider.Settings;

import java.util.Random;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by dks on 2017/12/6.
 */

public class AndroidID implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        /**
         * *******************AndroidID*************************************************************
         */
        XposedHelpers.findAndHookMethod(Settings.Secure.class, "getString",
                ContentResolver.class, String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        param.args[1] = "android_id";
                        String AndroidID = randomAndroidID();
                        param.setResult(AndroidID);
                        XposedBridge.log("AndroidID: " + AndroidID);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    }
                });
    }

    /**
     * AndroidID
     *
     * @return
     */
    public static String randomAndroidID() {
        String str = "0123456789abcdef";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 14; i++) {
            int num = random.nextInt(16);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }
}
