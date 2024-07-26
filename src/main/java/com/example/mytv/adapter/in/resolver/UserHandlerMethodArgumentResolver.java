package com.example.mytv.adapter.in.resolver;

import com.example.mytv.adapter.in.api.HeaderAttribute;
import com.example.mytv.application.port.out.LoadUserPort;
import com.example.mytv.application.port.out.UserSessionPort;
import com.example.mytv.domain.user.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private UserSessionPort userSessionPort;
    private LoadUserPort loadUserPort;

    // @Autowired(required = false) 와 동일 효과
    public UserHandlerMethodArgumentResolver(Optional<UserSessionPort> userSessionPort, Optional<LoadUserPort> loadUserPort) {
        userSessionPort.ifPresent(port -> this.userSessionPort = port);
        loadUserPort.ifPresent(port -> this.loadUserPort = port);
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        var authKey = nativeWebRequest.getHeader(HeaderAttribute.X_AUTH_KEY);
        if (authKey == null) {
            return null;
        }
        var userId = userSessionPort.getUserId(authKey);
        if (userId == null) {
            return null;
        }
        return loadUserPort.loadUser(userId).orElse(null);
    }
}