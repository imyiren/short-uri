package com.imyiren.shorturi.web;

import com.imyiren.shorturi.common.RadixUtil;
import com.imyiren.shorturi.service.ShortUriService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 主页
 *
 * @author yiren
 */
@Slf4j
@Controller
public class IndexController {

    @Resource
    private ShortUriService shortUriService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/s/{key}")
    public void redirect(@PathVariable String key, HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.sendRedirect(shortUriService.originalUri(key));
        } catch (IOException e) {
            log.warn("重定向失败", e);
        }
    }

}
