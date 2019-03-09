package com.andoresu.cryptocalc.utils;

import android.support.v4.app.Fragment;

public abstract  class DeferredFragmentTransaction {

    private int contentFrameId;
    private BaseFragment replacingFragment;

    public abstract void commit();

    public int getContentFrameId() {
        return contentFrameId;
    }

    public void setContentFrameId(int contentFrameId) {
        this.contentFrameId = contentFrameId;
    }

    public BaseFragment getReplacingFragment() {
        return replacingFragment;
    }

    public void setReplacingFragment(BaseFragment replacingFragment) {
        this.replacingFragment = replacingFragment;
    }

}
