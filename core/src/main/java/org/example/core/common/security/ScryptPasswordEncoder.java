package org.example.core.common.security;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class ScryptPasswordEncoder implements PasswordEncoder {

    private static final SCryptPasswordEncoder passwordEncoder = new SCryptPasswordEncoder(16, 8, 1, 32, 64);

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
