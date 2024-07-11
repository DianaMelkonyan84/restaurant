package org.example.chating;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {

    private static final SecureRandom secureRandom=new SecureRandom();
    public static String generateToken(){
        byte[] tokenBytes=new byte[32];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}
