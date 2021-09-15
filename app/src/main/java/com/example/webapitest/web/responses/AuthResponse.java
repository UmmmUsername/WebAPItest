package com.example.webapitest.web.responses;

public class AuthResponse {

    public final String accessToken;
    public final String tokenType;
    public final String refreshToken;
    public final int expiresIn;
    public final String scope;

    public AuthResponse(String accessToken, String tokenType, String refreshToken, int expiresIn, String scope) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.scope = scope;
    }
}
