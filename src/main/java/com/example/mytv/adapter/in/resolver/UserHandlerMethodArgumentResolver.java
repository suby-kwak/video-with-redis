package com.example.mytv.adapter.in.resolver;

import com.example.mytv.adapter.in.api.constant.HeaderAttribute;
import com.example.mytv.application.port.out.LoadUserPort;
import com.example.mytv.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired(required = false)
    private LoadUserPort loadUserPort;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        var userId = nativeWebRequest.getHeader(HeaderAttribute.X_AUTH_KEY);
        if (userId == null) {
            return null;
        }
        return loadUserPort.loadUser(userId).orElse(null);
    }
}