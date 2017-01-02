package com.zhuinden.examplegithubclient.util;

import android.os.Bundle;

/**
 * Created by Zhuinden on 2016.12.20..
 */

public class BundleFactory {
    public interface Provider {
        Bundle create();
    }

    static Provider provider = Bundle::new;

    public static Bundle create() {
        return provider.create();
    }
}
