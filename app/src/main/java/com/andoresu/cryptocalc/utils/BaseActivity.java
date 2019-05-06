package com.andoresu.cryptocalc.utils;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannedString;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.andoresu.cryptocalc.R;
import com.andoresu.cryptocalc.client.ErrorResponse;

import java.util.ArrayDeque;
import java.util.Queue;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.andoresu.cryptocalc.utils.BaseFragment.BASE_TAG;
import static com.andoresu.cryptocalc.utils.MyUtils.checkHasAllPermissions;
import static com.andoresu.cryptocalc.utils.MyUtils.removeTrailingLineFeed;
import static com.andoresu.cryptocalc.utils.MyUtils.showErrorDialog;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

@SuppressLint({"Registered", "LogNotTimber"})
public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    public String TAG = BASE_TAG + BaseActivity.class.getSimpleName();

    @Nullable
    @BindView(R.id.fragmentLayout)
    public View fragmentLayout;

    @Nullable
    @BindView(R.id.progressBar)
    public View progressBar;

    private Bundle mainBundle;

    public int MENU_GROUP_ID = 0;

    public final int ITEM_MENU_LOGOUT_ID = 1;

    public final int ITEM_MENU_DOWNLOAD_MAP = 2;

    private boolean bound = false;

    private Class<?> childClass;

    public boolean isRunning;

    private Queue<DeferredFragmentTransaction> deferredFragmentTransactions;

    private Queue<DeferredDialogFragmentTransaction> deferredDialogFragmentTransactions;


    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        while (!deferredFragmentTransactions.isEmpty()) {
            deferredFragmentTransactions.remove().commit();
        }

        while (!deferredDialogFragmentTransactions.isEmpty()) {
            deferredDialogFragmentTransactions.remove().commit();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        deferredFragmentTransactions = new ArrayDeque<>();
        deferredDialogFragmentTransactions = new ArrayDeque<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestActivityPermissions();
        }
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        handleView();
    }

    public abstract void handleView();

    @LayoutRes
    public abstract int getLayoutId();

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void restart(Class<?> cls, Bundle params){
        Intent intent = new Intent(this, cls);
        if(params != null){
            intent.putExtras(params);
        }
        startActivity(intent);
        finish();
    }

    public void setMainBundle(Bundle bundle){
        this.mainBundle = bundle;
    }


    public CharSequence getText(int id, Object... args) {
        for(int i = 0; i < args.length; ++i)
            args[i] = args[i] instanceof String? TextUtils.htmlEncode((String)args[i]) : args[i];
        return removeTrailingLineFeed(Html.fromHtml(String.format(Html.toHtml(new SpannedString(getText(id))), args)));
    }

    public void requestActivityPermissions(){
        boolean permissions = checkHasAllPermissions(this);
        if (!permissions) {
            MyUtils.requestPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(!checkHasAllPermissions(this)){
            showErrorDialog(this, getString(R.string.error_no_permission), (dialogInterface, i) -> requestActivityPermissions(), "Dar Permisos", (dialogInterface, i) -> finish(), "Cerrar");
        }
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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

    public void replaceFragmentWithQueue(@IdRes int contentFrameId, BaseFragment replacingFragment) {
        if (!isRunning) {
            DeferredFragmentTransaction deferredFragmentTransaction = new DeferredFragmentTransaction() {
                @Override
                public void commit() {
                    changeFragment(getReplacingFragment(), getContentFrameId());
                }
            };
            deferredFragmentTransaction.setContentFrameId(contentFrameId);
            deferredFragmentTransaction.setReplacingFragment(replacingFragment);

            deferredFragmentTransactions.add(deferredFragmentTransaction);
        } else {
            changeFragment(replacingFragment, contentFrameId);
        }
    }

    public void showDialogFragmentWithQueue(BaseDialogFragment dialogFragment) {
        if (!isRunning) {
            DeferredDialogFragmentTransaction deferredFragmentTransaction = new DeferredDialogFragmentTransaction() {
                @Override
                public void commit() {
                    getReplacingFragment().show(BaseActivity.this);
                }
            };
            deferredFragmentTransaction.setReplacingFragment(dialogFragment);
            deferredDialogFragmentTransactions.add(deferredFragmentTransaction);
        } else {
            dialogFragment.show(BaseActivity.this);
        }
    }

    public BaseFragment changeFragment(BaseFragment fragment, @IdRes int contentFrameId){
        FragmentManager fragmentManager = getSupportFragmentManager();
        BaseFragment currentFragment = (BaseFragment) fragmentManager.findFragmentById(contentFrameId);
        if(currentFragment != null && fragment.getClass().toString().equals(currentFragment.getTag())){
            return null;
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(contentFrameId, fragment, fragment.getClass().toString());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        return fragment;
    }

    public interface ServiceConnectionListener{

        void onServiceConnected();

        void onServiceDisconnected();
    }

}