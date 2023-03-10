package com.deng.manage.redis;

import com.deng.core.common.util.ImgVerifyCodeUtils;
import com.deng.core.constant.CacheConstants;
import com.deng.core.time.TimeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

/**
 * @author :deng
 * @version :1.0
 * @description : 验证码管理类
 * @since :1.8
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class VerifyCodeManager {
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 生成图形验证码，并放入 Redis 中
     */
    public String genImgVerifyCode(String sessionId) throws IOException {
        String verifyCode = ImgVerifyCodeUtils.getRandomVerifyCode(4);
        String img = ImgVerifyCodeUtils.genVerifyCodeImg(verifyCode);

        // 1min过期
        stringRedisTemplate.opsForValue().set(CacheConstants.IMG_VERIFY_CODE_CACHE_KEY + sessionId,
                verifyCode, Duration.ofMinutes(TimeEnum.MINUTE_1.getTime()));
        return img;
    }

    /**
     * 校验图形验证码
     */
    public boolean imgVerifyCodeOk(String sessionId, String verifyCode) {
        return Objects.equals(stringRedisTemplate.opsForValue()
                .get(CacheConstants.IMG_VERIFY_CODE_CACHE_KEY + sessionId), verifyCode);
    }

    /**
     * 从 Redis 中删除验证码
     */
    public void removeImgVerifyCode(String sessionId) {
        stringRedisTemplate.delete(CacheConstants.IMG_VERIFY_CODE_CACHE_KEY + sessionId);
    }
}
