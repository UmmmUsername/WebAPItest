package com.example.webapitest.web.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("roleId")
    public final String roleId;
    public final String username;
    public final String email;
    public final List<String> permissions;

    public UserResponse(String roleId, String username, String email, List<String> permissions) {
        this.roleId = roleId;
        this.username = username;
        this.email = email;
        this.permissions = permissions;
    }
}
