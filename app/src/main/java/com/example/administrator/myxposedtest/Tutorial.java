package com.example.administrator.myxposedtest;

import android.graphics.Color;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 *
 * Created by Administrator on 2017/12/5.
 */

public class Tutorial implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("开始前");
        if (lpparam.packageName.equals("com.example.administrator.mytexttest")){
            XposedBridge.log("findAndHookMethod");

            XposedHelpers.findAndHookMethod(TextView.class, "setText", CharSequence.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.args[0] = "被修改前";
                    XposedBridge.log("beforeHookedMethod");
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("afterHookedMethod");
                    TextView tv = (TextView) param.thisObject;
                    tv.setTextColor(Color.RED);

                }
            });
        }

    }
}
