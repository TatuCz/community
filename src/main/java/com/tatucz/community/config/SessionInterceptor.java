package com.tatucz.community.config;

import com.tatucz.community.dto.UserDTO;
import com.tatucz.community.mapper.UserMapper;
import com.tatucz.community.util.CookieUtil;
import com.tatucz.community.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通过cookie的token设置session的拦截器
 * Created on 19-9-7.
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (CookieUtil.TOKEN.equals(cookie.getName())) {
                    String token = cookie.getValue();
                    if (!StringUtils.isEmpty(token)) {
                        UserDTO user = userMapper.findByToken(token);
                        if (user != null && !user.equals(request.getSession().getAttribute(SessionUtil.USER_ATTRIBUTE))) {
                            request.getSession().setAttribute(SessionUtil.USER_ATTRIBUTE, user);
                            System.out.println("种session.uid=" + user.getId());
                        }
                        break;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
