package com.andoresu.cryptocalc.utils;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andoresu.cryptocalc.R;
import com.andoresu.cryptocalc.client.ErrorResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.andoresu.cryptocalc.utils.MyUtils.removeTrailingLineFeed;

public abstract class BaseFragment extends Fragment implements BaseView{

    public static final String BASE_TAG =  "CRYPTOCALC_";

    public static final String TAG = BASE_TAG + BaseFragment.class.getSimpleName();

    public View baseFragment;

    @Nullable
    @BindView(R.id.fragmentLayout)
    public View fragmentLayout;

    @Nullable
    @BindView(R.id.progressBar)
    public View progressBar;

    private int viewPagerPosition = 0;

    private Unbinder unbinder;

    private Bundle fragmentBundle;

    private boolean isFragmentCreated;

    private String title = null;

    public boolean autoTitle = false;

    public int getViewPagerPosition() {
        return viewPagerPosition;
    }

    public void setViewPagerPosition(int viewPagerPosition) {
        this.viewPagerPosition = viewPagerPosition;
    }

    public Unbinder getUnbinder() {
        return unbinder;
    }

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
//        setTouchListener();
    }

    public Bundle getFragmentBundle() {
        return fragmentBundle;
    }

    public void setFragmentBundle(Bundle fragmentBundle) {
        this.fragmentBundle = fragmentBundle;
    }

    public boolean isFragmentCreated() {
        return isFragmentCreated;
    }

    public void setFragmentCreated(boolean fragmentCreated) {
        isFragmentCreated = fragmentCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onResume() {
        super.onResume();
        setFragmentCreated(true);
        if(getActivity() != null && autoTitle && getTitle() != null){
//            getActivity().setTitle(getTitle());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        baseFragment = view;
        setUnbinder(ButterKnife.bind(this, view));
        handleView();
        return view;
    }

    public abstract void handleView();

    @LayoutRes
    public abstract int getLayoutId();

    public CharSequence getText(int id, Object... args) {
        for(int i = 0; i < args.length; ++i)
            args[i] = args[i] instanceof String ? TextUtils.htmlEncode((String)args[i]) : args[i];
        return removeTrailingLineFeed(Html.fromHtml(String.format(Html.toHtml(new SpannedString(this.getText(id))), args)));
    }

    public Bundle getInstanceBundle(){
        Bundle bundle = new Bundle();
        return bundle;
    }

    @Override
    public void showProgressIndicator(boolean active) {
        if(fragmentLayout != null && progressBar != null){
            if(active){
                fragmentLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }else{
                fragmentLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showGlobalError(ErrorResponse errorResponse) {

    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogoutFinish() {

    }
}