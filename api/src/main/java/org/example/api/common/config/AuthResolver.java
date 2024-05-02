package org.example.api.common.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.common.config.model.UserSession;
import org.example.core.common.exception.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.crypto.SecretKey;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final JwtConfig jwtConfig;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserSession.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = webRequest.getHeader("Authorization");
        if ("".equals(accessToken)) {
            throw new UnauthorizedException("인증되지 않은 사용자입니다.");
        }
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecretKey());
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);

        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(accessToken);
            return new UserSession(Long.parseLong(claims.getPayload().getSubject()));
        } catch (JwtException e) {
            log.error("JWT 토큰 검증 실패", e);
            throw new UnauthorizedException("유효하지 않은 JWT 토큰입니다.");
        }

    }
}
