package com.abhijeet.ganpatiapp.modelclass;

import android.content.Context;
import android.content.pm.PackageManager;

public class PackageChecker {

    public static boolean isPackageInstalled(Context context, String packageName){
        PackageManager packageManager = context.getPackageManager();

        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        }

        catch (Exception e){
            return false;
        }
    }

}
