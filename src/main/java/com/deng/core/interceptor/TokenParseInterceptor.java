package com.deng.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.deng.core.auth.UserHolder;
import com.deng.core.common.resp.RestResp;
import com.deng.core.constant.SystemConfigConstants;
import com.deng.core.constant.api.FrontApiRouterConstants;
import com.deng.core.util.JwtUtils;
import com.deng.dao.entity.GoodsRole;
import com.deng.service.GoodsRoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.objects.NativeJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

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

    private final String GET = "GET";

    @Resource
    private final GoodsRoleService goodsRoleService;

    // 应该在所有以用户名义进行操作的地方进行token拦截解析
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 资源get请求不用进过token解析
        // 但是要对管理员的请求进行拦截
        // 开启token解析
        if (!request.getRequestURI().startsWith(FrontApiRouterConstants.BEHIND_URL_PREFIX) && Objects.equals(request.getMethod(), GET)) {
            return true;
        }
        // 使用jwt来防止csrf攻击

        // 登录时获取JWT
        // 放入到线程本地中
        String token = request.getHeader(SystemConfigConstants.HTTP_AUTH_HEADER_NAME);
        if (StringUtils.hasText(token)) {
            // 解析失败会返回null
            Long uid = jwtUtils.parseToken(token, SystemConfigConstants.DEAL_FRONT_KEY);
            if (uid != null) {
                //判断request.getRequestURI() 是否是管理员的请求 是否包含FrontApiRouterConstants.BEHIND_URL_PREFIX 前缀
                if (request.getRequestURI().startsWith(FrontApiRouterConstants.BEHIND_URL_PREFIX)) {
                    if (!"1".equals(goodsRoleService.getRoleByUid(uid))) {
                        String result = new ObjectMapper().writeValueAsString(RestResp.fail("请先登录"));
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        String jsonResult = JSON.toJSONString(RestResp.fail("请先登录"));
                        response.getWriter().write(jsonResult);
                        //String jsonResult = JSON.stringify(result);
                        //String jsonResult = JSON.stringify(result);
                        //response.getWriter().write(jsonResult);
                        return false;
                    }
                }
                UserHolder.setUserId(uid);
                return true;
            }

        } else {
            log.error(request.getRequestURI());
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
