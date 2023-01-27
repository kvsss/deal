package com.deng.core.aspect;

import com.deng.dto.req.UserRegisterReqDTO;
import com.deng.manage.redis.VerifyCodeManager;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author :deng
 * @version :1.0
 * @description : 用户服务切面
 * @since :1.8
 */
@Aspect
@Component
@RequiredArgsConstructor
public class UserServiceAspect {


    private final VerifyCodeManager verifyCodeManager;

    @After("execution(* com.deng.service.impl.UserServiceImpl.register(..)) && args(dto)")
    public void removeImgVerifyCode(UserRegisterReqDTO dto) {
        // 移除
        verifyCodeManager.removeImgVerifyCode(dto.getSessionId());
    }


}
