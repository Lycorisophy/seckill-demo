package cn.lysoy.chatting.interceptor;

import cn.lysoy.chatting.pojo.User;
import cn.lysoy.chatting.service.UserService;
import cn.lysoy.chatting.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类描述：自定义用户参数
 *
 * @author: lysoy
 * @email: s2295938761@163.com
 * @date: 2022/02/24
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        final Class<?> parameterType = parameter.getParameterType();
        return parameterType == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String ticket = CookieUtil.getCookieValue(request, "userTicket");
        if (!StringUtils.hasLength(ticket)) {
            return null;
        }
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        return userService.getUserByTicket(ticket, request, response);
    }
}
