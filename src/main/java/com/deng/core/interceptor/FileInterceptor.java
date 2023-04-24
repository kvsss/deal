package com.deng.core.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author :deng
 * @version :1.0
 * @description :文件拦截器
 * @since :1.8
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FileInterceptor implements HandlerInterceptor {
    @Value("${deal.file.upload.path}")
    private String fileUploadPath;

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        // 图片返回,并没有借助controller层去处理
        // 而是根据url 带image的(这其实是图片的名称)
        // 逻辑：  前端上传图片  后端成功后(保存图片)  返回文件名  前端再得到成功信息后,更新用户头像 ->后端
        // 这里是将图片(用户请求)返回给前端 /image/ 的请求
        // 所有图片都存储在这样的路径下

        // 获取请求的 URI
        String requestUri = request.getRequestURI();
        // 缓存10天
        response.setDateHeader("expires", System.currentTimeMillis() + 60 * 60 * 24 * 1 * 1000);
        try (OutputStream out = response.getOutputStream(); InputStream input = new FileInputStream(
                fileUploadPath + requestUri)) {

            byte[] b = new byte[4096];
            for (int n; (n = input.read(b)) != -1; ) {
                out.write(b, 0, n);
            }
        }
        return false;
    }
}
