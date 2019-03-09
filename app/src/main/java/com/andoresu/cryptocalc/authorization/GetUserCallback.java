package com.andoresu.cryptocalc.authorization;

import android.net.Uri;
import android.util.Log;

import com.andoresu.cryptocalc.authorization.data.FacebookUser;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetUserCallback {

    public interface IGetUserResponse {
        void onCompleted(FacebookUser user);
    }

    private IGetUserResponse mGetUserResponse;
    private GraphRequest.Callback mCallback;

    public GetUserCallback(final IGetUserResponse getUserResponse) {

        mGetUserResponse = getUserResponse;
        mCallback = response -> {
            FacebookUser user = null;
            try {
                JSONObject userObj = response.getJSONObject();
                if (userObj == null) {
                    return;
                }
                user = jsonToUser(userObj);
            } catch (Exception e) {
                // Handle exception ...
            }

            // Handled by ProfileActivity
            mGetUserResponse.onCompleted(user);
        };
    }

    private FacebookUser jsonToUser(JSONObject user) throws JSONException {
        Uri picture = Uri.parse(user.getJSONObject("picture").getJSONObject("data").getString
                ("url"));
        String name = user.getString("name");
        String id = user.getString("id");
        String email = null;
        if (user.has("email")) {
            email = user.getString("email");
        }

        // Build permissions display string
        StringBuilder builder = new StringBuilder();
        JSONArray perms = user.getJSONObject("permissions").getJSONArray("data");
        builder.append("Permissions:\n");
        for (int i = 0; i < perms.length(); i++) {
            builder.append(perms.getJSONObject(i).get("permission")).append(": ").append(perms
                    .getJSONObject(i).get("status")).append("\n");
        }
        String permissions = builder.toString();

        return new FacebookUser(picture, name, id, email, permissions);
    }

    public GraphRequest.Callback getCallback() {
        return mCallback;
    }
}
