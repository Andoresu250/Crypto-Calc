package com.andoresu.cryptocalc.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class BaseSlidePagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private List<T> fragments;

//    private BaseActivity activity;

    String TAG = "COMPARENDERA_" + BaseSlidePagerAdapter.class.getSimpleName();

//    public BaseSlidePagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull List<Fragment> fragments, BaseActivity activity) {
//        super(fragmentManager);
//        this.fragments = fragments;
//        this.activity = activity;
//    }

    public BaseSlidePagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull List<T> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
    }

    @Override
    public T getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Fragment fragment = fragments.get(position);
        if(fragment instanceof BaseFragment){
            return ((BaseFragment) fragment).getTitle();
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if(object instanceof BaseFragment){
            return ((BaseFragment) object).getViewPagerPosition();
        }
        return super.getItemPosition(object);
    }

    public void setFragments(List<T> fragments){
        this.fragments = fragments;
    }

}