package com.xdroid.spring.util.androids.tool;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;


public class XDActivityUtils {

    /**
     *
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        transaction.add(frameId, Objects.requireNonNull(fragment));
        transaction.commit();
    }

}
