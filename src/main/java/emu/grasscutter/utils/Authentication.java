package emu.grasscutter.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.HashMap;

public final class Authentication {
    public static HashMap<String,String> tokenStorage = new HashMap<String,String>();
    private static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static SecretKey getKey() {
        return key;
    }
}
