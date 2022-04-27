package emu.grasscutter.utils;

import emu.grasscutter.Grasscutter;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Locale;

public final class Authentication {
    public static HashMap<String,String> tokenStorage = new HashMap<String,String>();
    private static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static SecretKey getKey() {
        return key;
    }

    public static String generateHash(String password) {
        String hashtype = Grasscutter.getConfig().getDispatchOptions().AuthHash.toLowerCase();
        return switch (hashtype) {
            case "argon2" -> new Argon2PasswordEncoder().encode(password);
            case "scrypt" -> new SCryptPasswordEncoder().encode(password);
            case "bcrypt" -> new BCryptPasswordEncoder().encode(password);
            default -> new BCryptPasswordEncoder().encode(password);
        };
    }

    public static boolean verifyHash(String password, String hash) {
        String hashtype = Grasscutter.getConfig().getDispatchOptions().AuthHash.toLowerCase();
        return switch (hashtype) {
            case "argon2" -> new Argon2PasswordEncoder().matches(password, hash);
            case "scrypt" -> new SCryptPasswordEncoder().matches(password, hash);
            case "bcrypt" -> new BCryptPasswordEncoder().matches(password, hash);
            default -> new BCryptPasswordEncoder().matches(password, hash);
        };
    }
}
