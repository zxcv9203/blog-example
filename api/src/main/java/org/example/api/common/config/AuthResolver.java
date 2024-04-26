package org.example.api.common.config;

import lombok.RequiredArgsConstructor;
import org.example.api.common.config.model.UserSession;
import org.example.core.common.exception.UnauthorizedException;
import org.example.core.domain.auth.Session;
import org.example.core.domain.auth.SessionRepository;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

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

        Session session = sessionRepository.findByAccessToken(accessToken);

        return new UserSession(session.getId());
    }
}
