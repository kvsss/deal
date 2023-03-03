package com.deng.core.aspect;

import com.deng.core.annotation.Key;
import com.deng.core.annotation.Lock;
import com.deng.core.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.TimeUnit;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@Aspect
@Component
@RequiredArgsConstructor
public class LockAspect {
    private final RedissonClient redissonClient;

    private static final String KEY_PREFIX = "Lock";

    private static final String KEY_SEPARATOR = "::";

    @Around(value = "@annotation(com.deng.core.annotation.Lock)")
    @SneakyThrows
    public Object doAround(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        Lock lock = targetMethod.getAnnotation(Lock.class);
        String lockKey = KEY_PREFIX + buildLockKey(lock.prefix(), targetMethod,
                joinPoint.getArgs());
        RLock rLock = redissonClient.getLock(lockKey);
        if (lock.isWait() ? rLock.tryLock(lock.waitTime(), TimeUnit.SECONDS) : rLock.tryLock()) {
            try {
                return joinPoint.proceed();
            } finally {
                rLock.unlock();
            }
        }
        throw new ServiceException(lock.failCode());
    }

    private String buildLockKey(String prefix, Method method, Object[] args) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.hasText(prefix)) {
            builder.append(KEY_SEPARATOR).append(prefix);
        }
        Parameter[] parameters = method.getParameters();
        // 遍历每一个参数
        for (int i = 0; i < parameters.length; i++) {
            // 添加前缀
            builder.append(KEY_SEPARATOR);
            if (parameters[i].isAnnotationPresent(Key.class)) {
                Key key = parameters[i].getAnnotation(Key.class);
                builder.append(parseKeyExpr(key.expr(), args[i]));
            }
        }
        return builder.toString();
    }

    private String parseKeyExpr(String expr, Object arg) {
        if (!StringUtils.hasText(expr)) {
            return arg.toString();
        }
        // 表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(expr, new TemplateParserContext());
        return expression.getValue(arg, String.class);
    }
}
