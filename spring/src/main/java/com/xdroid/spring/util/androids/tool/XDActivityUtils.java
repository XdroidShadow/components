package com.xdroid.spring.util.androids.tool;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class XDActivityUtils {
    private static final String TAG = "XDActivityUtils";

    /**
     *
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        if (fragment.isAdded()) {
            XDLog.e(TAG, "已经添加过了，跳过");
            return;
        }
        FragmentTransaction transaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        transaction.add(frameId, Objects.requireNonNull(fragment));
        transaction.commit();
    }

    public static void removeFragmentFromActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        FragmentTransaction transaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        if (fragment.isAdded()) {
            transaction.remove(fragment);
        }
        transaction.commit();
    }

}
