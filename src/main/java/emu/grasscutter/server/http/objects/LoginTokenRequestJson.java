package emu.grasscutter.server.http.objects;

import lombok.Builder;

/** This request object is used in both token-related authenticators. */
@Builder
public class LoginTokenRequestJson {
    public String uid;
    public String token;
}
