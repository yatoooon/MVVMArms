package com.common.core.parser;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.common.core.base.ibase.IComponentApp;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

public class AppManifestParser {
    @NotNull
    private final Context context;

    public final List<IComponentApp> parse() {
        Timber.d("Loading IComponentApps");
        ArrayList<IComponentApp> componentApps = new ArrayList<>();

        try {
            ApplicationInfo var10000 = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Intrinsics.checkNotNullExpressionValue(var10000, "context.packageManager\n …ageManager.GET_META_DATA)");
            if (var10000.metaData == null) {
                Timber.d("Got null app info metadata");
                return componentApps;
            }

            Timber.v("Got app info metadata: " + var10000.metaData);

            for (String key : var10000.metaData.keySet()) {
                if (Intrinsics.areEqual("ComponentApp", var10000.metaData.get(key))) {
                    Intrinsics.checkNotNullExpressionValue(key, "key");
                    componentApps.add(AppManifestParser.parse(key));
                    Timber.d("Loaded IComponentApp: " + key);
                }
            }
        } catch (PackageManager.NameNotFoundException var5) {
            throw new RuntimeException("Unable to find metadata to parse IComponentApps",  var5);
        }

        Timber.d("Finished loading IComponentApps");
        return componentApps;
    }

    @NotNull
    public final Context getContext() {
        return this.context;
    }

    public AppManifestParser(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }


    private static IComponentApp parse(String className) {
        Class var3;
        try {
            var3 = Class.forName(className);
        } catch (ClassNotFoundException var8) {
            throw new IllegalArgumentException("Unable to find IComponentApp implementation",  var8);
        }

        Class clazz = var3;

        Object var4;
        try {
            var4 = clazz.newInstance();
        } catch (InstantiationException var6) {
            throw new RuntimeException("Unable to instantiate IComponentApp implementation for " + var3,  var6);
        } catch (IllegalAccessException var7) {
            throw new RuntimeException("Unable to instantiate IComponentApp implementation for " + var3,  var7);
        }

        if (!(var4 instanceof IComponentApp)) {
            throw new RuntimeException("Expected instance of IComponentApp, but found: " + var4);
        } else {
            return (IComponentApp) var4;
        }
    }
}

