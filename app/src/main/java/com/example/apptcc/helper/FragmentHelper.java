package com.example.apptcc.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentHelper {

    public static void show(AppCompatActivity activity, int containerId, Fragment fragment) {
        if (fragment == null || containerId < 0)
            return;

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerId, fragment);
        fragmentTransaction.commit();
    }

}
