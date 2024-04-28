package org.example.api.common.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.common.config.model.UserSession;
import org.example.core.common.exception.UnauthorizedException;
import org.example.core.domain.auth.Session;
import org.example.core.domain.auth.SessionRepository;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserSession.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            log.error("servlet request is null");
            throw new UnauthorizedException("인증되지 않은 사용자입니다.");
        }

        Cookie[] cookies = request.getCookies();
        if (cookies.length == 0) {
            log.error("cookie is empty");
            throw new UnauthorizedException("인증되지 않은 사용자입니다.");
        }

        String accessToken = cookies[0].getValue();
        Session session = sessionRepository.findByAccessToken(accessToken);

        return new UserSession(session.getId());
    }
}
