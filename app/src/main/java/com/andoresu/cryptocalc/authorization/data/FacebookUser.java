package com.andoresu.cryptocalc.authorization.data;

import android.net.Uri;

public class FacebookUser {
    public final Uri picture;
    public final String name;
    public final String id;
    public final String email;
    public final String permissions;

    public FacebookUser(Uri picture, String name,
                String id, String email, String permissions) {
        this.picture = picture;
        this.name = name;
        this.id = id;
        this.email = email;
        this.permissions = permissions;
    }

    public Uri getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPermissions() {
        return permissions;
    }
}
