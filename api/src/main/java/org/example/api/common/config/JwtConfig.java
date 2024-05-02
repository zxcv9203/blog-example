package org.example.api.common.config;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Setter
@Getter
public class JwtConfig {

    private SecretKey secretKey;

    public void setSecretKey(String secretKey) {
        byte[] decode = Base64.getDecoder().decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(decode);
    }
}
