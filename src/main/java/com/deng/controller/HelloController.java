package com.deng.controller;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.deng.core.constant.DateBaseConstants;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
@RestController
@RequestMapping("/api")
public class HelloController {

    @RequestMapping("hello")
    public String hello(HttpServletRequest request){
        //CsrfToken token = (CsrfToken)request.getAttribute("_csrf");
        return "hello";
    }

    @RequestMapping("error")
    public String error(HttpServletRequest request){
        return "hello";
    }
}
