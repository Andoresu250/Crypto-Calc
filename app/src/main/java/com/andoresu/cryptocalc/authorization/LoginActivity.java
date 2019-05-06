package com.andoresu.cryptocalc.authorization;

import android.content.Intent;
import android.util.Log;

import com.andoresu.cryptocalc.R;
import com.andoresu.cryptocalc.authorization.data.FacebookUser;
import com.andoresu.cryptocalc.core.MainActivity;
import com.andoresu.cryptocalc.utils.BaseActivity;
//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;

import butterknife.BindView;

import static com.andoresu.cryptocalc.utils.BaseFragment.BASE_TAG;

public class LoginActivity extends BaseActivity
//        implements GetUserCallback.IGetUserResponse, PhoneDialogFragment.PhoneDialogListener
    {

    public String TAG = BASE_TAG + LoginActivity.class.getSimpleName();

//    @BindView(R.id.loginButton)
//    LoginButton loginButton;
//
//    private CallbackManager callbackManager;

    @Override
    public void handleView() {

//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//
//        if(isLoggedIn){
//            goToMainActivity();
//            return;
//        }
//
//        callbackManager = CallbackManager.Factory.create();
//
//        loginButton.setReadPermissions("email", "public_profile", "user_friends");
//
//        // Callback registration
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
////                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                startActivity(intent);
////                finish();
//                PhoneDialogFragment phoneDialogFragment = PhoneDialogFragment.newInstance(LoginActivity.this);
//                showDialogFragmentWithQueue(phoneDialogFragment);
//
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//                Log.i(TAG, "onCancel: ");
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//                Log.e(TAG, "onError: " + exception.toString());
//            }
//        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    public void onCompleted(FacebookUser user) {
//
//    }
//
//    @Override
//    public void onClickPositiveButton(String phone) {
//        goToMainActivity();
//    }
//
//    @Override
//    public void onClickNegativeButton() {
//        LoginManager.getInstance().logOut();
//    }

    private void goToMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
