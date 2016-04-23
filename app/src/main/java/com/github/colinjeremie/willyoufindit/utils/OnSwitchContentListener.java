package com.github.colinjeremie.willyoufindit.utils;

import android.support.v4.app.Fragment;

/**
 * Interface used by the fragments to change the content of the Fragment of the {@link com.github.colinjeremie.willyoufindit.activities.MainActivity}
 * Created by jerem_000 on 4/23/2016.
 */
public interface OnSwitchContentListener {
    void onSwitchContent(Fragment pFragment);
}
