package moe.xzr.oplusnovpnnotification;

import android.app.PendingIntent;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Entry implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if ("android".equals(lpparam.packageName)) {
            Class<?> vpnConfigClass = XposedHelpers.findClass("com.android.internal.net.VpnConfig", lpparam.classLoader);
            XposedHelpers.findAndHookMethod(
                    "com.android.server.connectivity.VpnExtImpl",
                    lpparam.classLoader,
                    "showNotification",
                    String.class, int.class, int.class, String.class, PendingIntent.class, vpnConfigClass,
                    XC_MethodReplacement.returnConstant(null));
        } else if ("com.android.systemui".equals(lpparam.packageName)) {
            XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy.SecurityControllerImpl",
                    lpparam.classLoader, "getPrimaryVpnName", XC_MethodReplacement.returnConstant(null));
        }
    }
}
