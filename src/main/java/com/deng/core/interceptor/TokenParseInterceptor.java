package com.deng.core.interceptor;

import com.deng.core.auth.UserHolder;
import com.deng.core.constant.SystemConfigConstants;
import com.deng.core.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author :deng
 * @version :1.0
 * @description :token拦截器
 * @since :1.8
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TokenParseInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    // 应该在所有以用户名义进行操作的地方进行token拦截解析
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 使用jwt来防止csrf攻击

        // 登录时获取JWT
        // 放入到线程本地中
        String token = request.getHeader(SystemConfigConstants.HTTP_AUTH_HEADER_NAME);
        if (StringUtils.hasText(token)) {
            Long uid = jwtUtils.parseToken(token, SystemConfigConstants.DEAL_FRONT_KEY);
            if (uid != null) {
                UserHolder.setUserId(uid);
                return true;
            }
        }
        // 拦截
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserHolder.clear();
        // 清理线程中线程保存的用户数据
        UserHolder.clear();
    }
}
