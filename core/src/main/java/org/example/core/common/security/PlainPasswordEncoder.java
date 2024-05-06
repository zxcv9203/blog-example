package org.example.core.common.security;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class PlainPasswordEncoder implements PasswordEncoder {
    @Override
    public String encrypt(String rawPassword) {
        return rawPassword;
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return encryptedPassword.equals(rawPassword);
    }
}
