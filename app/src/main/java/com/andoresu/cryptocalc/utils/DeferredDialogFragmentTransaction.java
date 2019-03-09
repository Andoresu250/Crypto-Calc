package com.andoresu.cryptocalc.utils;

import android.support.v4.app.DialogFragment;

public abstract class DeferredDialogFragmentTransaction {
    private BaseDialogFragment replacingFragment;

    public abstract void commit();

    public BaseDialogFragment getReplacingFragment() {
        return replacingFragment;
    }

    public void setReplacingFragment(BaseDialogFragment replacingFragment) {
        this.replacingFragment = replacingFragment;
    }

}
