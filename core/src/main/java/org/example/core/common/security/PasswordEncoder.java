package org.example.core.common.security;

public interface PasswordEncoder {

    String encrypt(String rawPassword);
    boolean matches(String rawPassword, String encryptedPassword);
}
