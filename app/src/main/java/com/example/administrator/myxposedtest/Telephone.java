package com.example.administrator.myxposedtest;

import android.content.ContentResolver;
import android.net.wifi.WifiInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.Random;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by dks on 2017/12/6.
 */

public class Telephone implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        /**
         * ************************IMEI**************************************************************
         */
        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getDeviceId", int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        String mIMEI = randomIMEI();
                        param.setResult(mIMEI);
                        XposedBridge.log("mIMEI; " + mIMEI);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    }
                });
        /**
         * *******************AndroidID***********************************************************************
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


        /**
         * 待完善 其他方式
         * ******************* wifi mac ***********************************************************************
         */
        XposedHelpers.findAndHookMethod(WifiInfo.class, "getMacAddress",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        String MacAddress = randomMacAddress();
                        param.setResult(MacAddress);
                        XposedBridge.log("AndroidID: " + MacAddress);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    }
                });


        /**
         * wifi de 名称 ，不重要
         ************************* wifi ssid *****************************************************************
         */
        XposedHelpers.findAndHookMethod(WifiInfo.class, "getSSID",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        String SSID = randomMacgetSSID();
                        param.setResult(SSID);
                        XposedBridge.log("getSSID: " + SSID);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    }
                });


        /**
         * 手机号 其他方式待补充
         ************************* phone number *****************************************************
         */
        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getLine1Number",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        String number = randomPhoneNumber();
                        param.setResult(number);
                        XposedBridge.log("phone number: " + number);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    }
                });

        /**
         *sim 卡
         ************************* ICCID *****************************************************
         */
        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getSimSerialNumber",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        String ICCID = randomICCID();
                        param.setResult(ICCID);
                        XposedBridge.log("ICCID: " + ICCID);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    }
                });

        /**
         * sim 卡
         ************************* IMSI *****************************************************
         */
        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getSubscriberId",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        String IMSI = randomIMSI();
                        param.setResult(IMSI);
                        XposedBridge.log("IMSI: " + IMSI);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    }
                });
    }

//


    /**
     * 产生一个随机的数字串
     * IMEI为TAC + FAC + SNR + SP
     * 其组成为:
     * 1.前6位数(TAC)是“型号核准号码”，一般代表机型。
     * 2.接着的2位数(FAC)是”最后装配号”，一般代表产地。
     * 3.之后的6位数(SNR)是“串号”，一般代表生产顺序号。
     * 4.最后1位数(SP)通常是”0”，为检验码，目前暂备用。
     */
    public static String randomIMEI() {
        String str = "0123456789";
        String TAC_MiNote = "867451";
        String FAC_MiNote = "02";//02 03 04
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        buf.append(TAC_MiNote).append(FAC_MiNote);
        for (int i = 0; i < 7; i++) {
            int num = random.nextInt(10);
            buf.append(str.charAt(num));
        }
        return buf.toString();
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

    /**
     * MacAddress
     * XX:XX:XX:XX:XX:XX
     *
     * @return
     */
    public static String randomMacAddress() {
        String str = "0123456789abcdef";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                int num = random.nextInt(16);
                buf.append(str.charAt(num));
            }
            if (i < 5) buf.append(":");
        }
        return buf.toString();
    }

    /**
     * wifi ssid
     * wifi 名称
     *
     * @return
     */
    public static String randomMacgetSSID() {
        String str = "0000123456789abcdefghigklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            int num = random.nextInt(36);
            buf.append(str.charAt(num));
            buf.append(str.charAt(num + 1));
            buf.append(str.charAt(num + 2));
        }
        return buf.toString();
    }


    static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    String currentTelFirst;

    /**
     * 随机手机号
     *
     * @return
     */
    public String randomPhoneNumber() {
        String first = telFirst[getRandomNumBetween(0, telFirst.length - 1)];
        currentTelFirst = first;
        String second = String.valueOf(getRandomNumBetween(1, 888) + 10000).substring(1);
        String third = String.valueOf(getRandomNumBetween(1, 9100) + 10000).substring(1);
        return first + second + third;
    }

    public static int getRandomNumBetween(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    /**
     * randomICCID
     * ICCID：Integrate circuit card identity 集成电路卡识别码即SIM卡卡号，相当于手机号码的身份证。
     * ICCID为IC卡的唯一识别号码，共有20位数字组成，其编码格式为：XXXXXX 0MFSS YYGXX XXXX。
     * 分别介绍如下： 前六位运营商代码：中国移动的为：898600；898602 ，中国联通的为：898601、898609，中国电信898603，898606。
     * <p>
     * 中国移动编码格式
     * 89860 0MFSS YYGXX XXXXP
     * 中国联通编码格式
     * 89860 1YYMH AAAXX XXXXP
     * 中国电信编码格式
     * 89860 3MYYH HHXXX XXXXX
     * <p>
     * <p>
     * 中国联通
     * 898601 YY 8 SS XXXXXXXX P
     * 89： 国际编号
     * 86： 国家编号，86：中国
     * 01： 运营商编号，01：中国联通
     * YY： 编制ICCID时年号的后两位
     * 8： 中国联通ICCID默认此位为8
     * SS：2位省份编码
     * 10内蒙古 11北京 13天津 17山东 18河北 19山西 30安徽 31上海 34江苏
     * 36浙江 38福建 50海南 51广东 59广西 70青海 71湖北 74湖南 75江西
     * 76河南 79西藏 81四川 83重庆 84陕西 85贵州 86云南 87甘肃 88宁夏
     * 89新疆 90吉林 91辽宁 97黑龙江
     * X…X： 卡商生产的顺序编码
     * P： 校验位
     *
     * @return
     */
    static String[] years = "08,09,10,11,12,13,14,15,16,17".split(",");
    static String[] provences = "10,11,13,17,18,19,30,31,34,36,38,50,51,59,70,71,74,75,76,79,81,83,84,85,86,87,88,89,90,91,97,".split(",");

    public String randomICCID() {

        //只是联通的
        String chainaUnite = "898601";
        String year = years[getRandomNumBetween(0, years.length - 1)];
        String provence = provences[getRandomNumBetween(0, provences.length - 1)];
        String defaultStr = "8";

        String str8 = "0123456789";
        Random random = new Random();
        StringBuffer buf8 = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int num = random.nextInt(10);
            buf8.append(str8.charAt(num));
        }
        String strP = "6N";
        int numP = random.nextInt(2);
        char P = str8.charAt(numP);
        String random8andP = buf8.append(P).toString();

        return chainaUnite + year + provence + defaultStr + random8andP;

    }


    /**
     * IMSI是15位的十进制数。其结构如下：
     * MCC+MNC+MSIN
     * <p>
     * MCC（Mobile Country Code，移动国家码）：MCC的资源由国际电信联盟（ITU）在全世界范围内统一分配和管理，唯一识别移动用户所属的国家，共3位，中国为460。
     * <p>
     * MNC（Mobile Network Code，移动网络号码）：用于识别移动用户所归属的移动通信网，2~3位。
     * 在同一个国家内，如果有多个PLMN（Public Land Mobile Network，公共陆地移动网，一般某个国家的一个运营商对应一个PLMN），可以通过MNC来进行区别，即每一个PLMN都要分配唯一的MNC。
     * 中国移动系统使用00、02、04、07，中国联通GSM系统使用01、06、09，中国电信CDMA系统使用03、05、电信4G使用11，中国铁通系统使用20。
     * <p>
     * MSIN（Mobile Subscriber Identification Number，移动用户识别号码）：用以识别某一移动通信网中的移动用户。共有10位，其结构如下：
     * EF+M0M1M2M3+ABCD
     * 其中，EF由运营商分配；M0M1M2M3和MDN（Mobile Directory Number，移动用户号码簿号码）中的H0H1H2H3可存在对应关系；ABCD：四位，自由分配。
     *
     * @return
     */
    static String[] MNC_chinaUnite = "01,06,09".split(",");

    public String randomIMSI() {
        //仅生成联通
        String MCC_china = "046";
        String MNC_U = MNC_chinaUnite[getRandomNumBetween(0, MNC_chinaUnite.length - 1)];
        //MSIN 10位 规则不明 随机生成
        String str8 = "0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            int num = random.nextInt(10);
            buf.append(str8.charAt(num));
        }
        String MSIN = buf.toString();

        return MCC_china + MNC_U + MSIN;
    }
}
