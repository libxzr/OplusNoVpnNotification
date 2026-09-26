package moe.xzr.oplusnovpnnotification;

import android.app.PendingIntent;

import java.lang.reflect.Method;

import io.github.libxposed.api.XposedModule;
import io.github.libxposed.api.XposedModuleInterface;

public class Entry extends XposedModule {
    @Override
    public void onSystemServerStarting(XposedModuleInterface.SystemServerStartingParam param) {
        ClassLoader classLoader = param.getClassLoader();
        Class<?> vpnConfigClass = findClass("com.android.internal.net.VpnConfig", classLoader);
        Method showNotification = findMethod(
                "com.android.server.connectivity.VpnExtImpl",
                classLoader,
                "showNotification",
                String.class, int.class, int.class, String.class, PendingIntent.class, vpnConfigClass);
        hook(showNotification).intercept(chain -> null);
    }


    private static Class<?> findClass(String name, ClassLoader classLoader) {
        try {
            return Class.forName(name, false, classLoader);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Class not found: " + name, e);
        }
    }

    private static Method findMethod(String className, ClassLoader classLoader, String methodName,
                                     Class<?>... parameterTypes) {
        try {
            return findClass(className, classLoader).getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Method not found: " + className + "." + methodName, e);
        }
    }
}
