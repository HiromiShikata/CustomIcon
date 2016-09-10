package com.imorih.customicon.model;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public class App {

    private String mName;
    private String mPackageName;
    private Drawable mIcon;

    public App(
            ApplicationInfo ai,
            PackageManager pm) {
        mName = ai.loadLabel(pm).toString();
        mPackageName = ai.packageName;
        mIcon = ai.loadIcon(pm);

    }

    public String getName() {
        return mName;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public String getPackageName() {
        return mPackageName;
    }

}
