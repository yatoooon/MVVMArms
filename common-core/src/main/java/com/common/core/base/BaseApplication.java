package com.common.core.base;

import android.app.Application;

import com.common.core.base.ibase.IComponentApp;
import com.common.core.config.ManifestParser;
import com.common.core.parser.AppManifestParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class BaseApplication extends Application {

    private List<IComponentApp> componentApps;
    private Map<String, Integer> componentMap = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        initComponentApp();
    }

    private void initComponentApp() {
        componentApps = new AppManifestParser(this).parse();
        if (componentApps.size() > 0) {
            for (IComponentApp iComponentApp : componentApps) {
                iComponentApp.onCreate(this);
            }
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (componentApps != null && componentApps.size() > 0) {
            for (IComponentApp iComponentApp : componentApps) {
                iComponentApp.onTerminate();
            }
        }
    }


    public <T extends IComponentApp> T getComponentApp(Class<T> cls) {
        if (componentApps != null && componentApps.size() > 0) {
            Integer index = componentMap.get(cls.getCanonicalName());
            if (index != null) {
                return (T) componentApps.get(index);
            }
            for (int i = 0; i < componentApps.size(); i++) {
                if (cls.isInstance(componentApps.get(i))) {
                    componentMap.put(cls.getCanonicalName(), i);
                    return (T) componentApps.get(i);
                }
            }
        }
        return null;
    }

}
