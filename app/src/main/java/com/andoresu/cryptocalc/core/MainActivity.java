package com.andoresu.cryptocalc.core;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andoresu.cryptocalc.R;
import com.andoresu.cryptocalc.authorization.GetUserCallback;
import com.andoresu.cryptocalc.authorization.LoginActivity;
import com.andoresu.cryptocalc.authorization.UserRequest;
import com.andoresu.cryptocalc.authorization.data.FacebookUser;
import com.andoresu.cryptocalc.client.ErrorResponse;
import com.andoresu.cryptocalc.core.calculator.CalculatorFragment;
import com.andoresu.cryptocalc.core.contact.ContactModel;
import com.andoresu.cryptocalc.utils.BaseActivity;
import com.andoresu.cryptocalc.utils.BaseFragment;
import com.andoresu.cryptocalc.core.percentage.PercentageFragment;
import com.andoresu.cryptocalc.utils.DeferredFragmentTransaction;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
//import com.facebook.login.LoginManager;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.mirrajabi.rxcontacts.Contact;
import ir.mirrajabi.rxcontacts.RxContacts;

import static com.andoresu.cryptocalc.utils.MyUtils.glideRequestOptions;
import static com.andoresu.cryptocalc.utils.MyUtils.showErrorDialog;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {
//        GetUserCallback.IGetUserResponse


    private MainContract.ActionsListener actionsListener;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private HeaderViewHolder headerViewHolder;

    private BaseFragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionsListener = new MainPresenter(this, this, "");
    }

    @SuppressLint("CheckResult")
    @Override
    public void handleView() {

        setSupportActionBar(toolbar);
        setTitle("      " + getString(R.string.app_name));

        headerViewHolder = new HeaderViewHolder(navigationView.getHeaderView(0));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        headerViewHolder.navHeaderEmailTextView.setText("");
        headerViewHolder.navHeaderNameTextView.setText("");
        setCalculatorFragment();
//        UserRequest.makeUserRequest(new GetUserCallback(this).getCallback());
        RxContacts.fetch(this)
                .filter(m->m.getInVisibleGroup() == 1)
                .toSortedList(Contact::compareTo)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(contacts -> {
                    List<ContactModel> contactModels = new ArrayList<>();
                    for(Contact contact : contacts){
                        for(String number : contact.getPhoneNumbers()){
                            contactModels.add(new ContactModel(contact.getDisplayName(), number));
                        }
                    }
                    if(!contactModels.isEmpty()){
                        actionsListener.sendContact(contactModels);
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            int fragments = fragmentManager.getBackStackEntryCount();
            if (fragments >= 1) {
                showErrorDialog(this, "Alerta", "Desea salir de la aplicacion?", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    finish();
                }, (dialogInterface, i) -> dialogInterface.dismiss());
            } else if (fragmentManager.getBackStackEntryCount() > 1) {
                Log.i(TAG, "onBackPressed: 1");
                Log.i(TAG, "onBackPressed: B " + currentFragment.getTag());
                fragmentManager.popBackStackImmediate();
                currentFragment = (BaseFragment) fragmentManager.findFragmentById(R.id.mainFragment);
                Log.i(TAG, "onBackPressed: A " + currentFragment.getTag());
                checkItemMenu(currentFragment);
            } else {
                Log.i(TAG, "onBackPressed: 2");
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.navCalculator:
                setCalculatorFragment();
                break;
            case R.id.navPercentage:
                setPercentageFragment();
                break;
            case R.id.navLogout:
                showErrorDialog(this, "Alerta", "Desea cerrar sesion?", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
//                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    checkItemMenu(currentFragment);
                });
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showProgressIndicator(boolean active) {

    }

    @Override
    public void showGlobalError(ErrorResponse errorResponse) {

    }

    @Override
    public void onLogoutFinish() {

    }

    public void setCalculatorFragment(){
        CalculatorFragment fragment = CalculatorFragment.newInstance();
        changeFragment(fragment);
    }

    public void setPercentageFragment(){
        PercentageFragment fragment = PercentageFragment.newInstance();
        changeFragment(fragment);
    }

    private void changeFragment(BaseFragment fragment){
        BaseFragment newFragment = changeFragment(fragment, R.id.mainFragment);
        if(newFragment != null){
            currentFragment = newFragment;
        }
        checkItemMenu(currentFragment);
    }

//    @Override
//    public void onCompleted(FacebookUser user) {
//        Glide.with(this)
//                .load(user.picture)
//                .apply(glideRequestOptions(this))
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                .into(headerViewHolder.profilePhoto);
//
//        headerViewHolder.navHeaderEmailTextView.setText(user.email);
//        headerViewHolder.navHeaderNameTextView.setText(user.name);
//    }

    private void checkItemMenu(@NonNull BaseFragment baseFragment){
        if(navigationView == null){
            return;
        }
        if(baseFragment instanceof CalculatorFragment){
            navigationView.setCheckedItem(R.id.navCalculator);
            return;
        }
        if(baseFragment instanceof PercentageFragment){
            navigationView.setCheckedItem(R.id.navPercentage);
            return;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    static class HeaderViewHolder {

        @BindView(R.id.navHeaderNameTextView)
        TextView navHeaderNameTextView;

        @BindView(R.id.navHeaderEmailTextView)
        TextView navHeaderEmailTextView;

        @BindView(R.id.imageView)
        ImageView profilePhoto;

        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
