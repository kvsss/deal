package com.deng.core.interceptor;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.deng.core.common.constant.CodeEnum;
import com.deng.core.common.resp.RestResp;
import com.deng.core.util.IpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description :流量限制拦截器
 * @since :1.8
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FlowLimitInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;

    /**
     * deal 项目所有的资源
     */
    private static final String DEAL_RESOURCE = "dealResource";

    static {
        // 接口限流规则：所有的请求，限制每秒最多只能通过 2000 个，超出限制匀速排队
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource(DEAL_RESOURCE);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 2000.
        rule1.setCount(2000);
        rule1.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER);
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);

        // 接口防刷规则 1：所有的请求，限制每个 IP 每秒最多只能通过 50 个，超出限制直接拒绝
        ParamFlowRule rule2 = new ParamFlowRule(DEAL_RESOURCE)
                .setParamIdx(0)
                .setCount(50);
        // 接口防刷规则 2：所有的请求，限制每个 IP 每分钟最多只能通过 1000 个，超出限制直接拒绝
        ParamFlowRule rule3 = new ParamFlowRule(DEAL_RESOURCE)
                .setParamIdx(0)
                .setCount(1000)
                .setDurationInSec(60);
        ParamFlowRuleManager.loadRules(Arrays.asList(rule2, rule3));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String ip = IpUtils.getRealIp(request);
        Entry entry = null;
        try {
            // 若需要配置例外项，则传入的参数只支持基本类型。
            // EntryType 代表流量类型，其中系统规则只对 IN 类型的埋点生效
            // count 大多数情况都填 1，代表统计为一次调用。
            entry = SphU.entry(DEAL_RESOURCE, EntryType.IN, 1, ip);
            // Your logic here.
            return true;
        } catch (BlockException ex) {
            // Handle request rejection.
            log.info("IP:{}被限流了！", ip);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter()
                    .write(objectMapper.writeValueAsString(RestResp.fail(CodeEnum.USER_REQUEST_LIMIT)));
        } finally {
            // 注意：exit 的时候也一定要带上对应的参数，否则可能会有统计错误。
            if (entry != null) {
                entry.exit(1, ip);
            }
        }
        return false;
    }
}
