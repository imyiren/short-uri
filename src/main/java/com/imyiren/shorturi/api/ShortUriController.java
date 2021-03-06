package com.imyiren.shorturi.api;

import com.imyiren.shorturi.common.ResponseResult;
import com.imyiren.shorturi.enums.PersistenceTypeEnum;
import com.imyiren.shorturi.service.ShortUriService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author yiren
 */
@Slf4j
@RestController
public class ShortUriController {

    public static final String TEMP = "TEMP";
    public static final int MAX_ORIGINAL_URI_LENGTH = 768;
    public static final String ORIGINAL_PREFIX = "http";

    @Resource
    private ShortUriService shortUriService;

    @GetMapping("/api/generate")
    public ResponseResult<String> generate(
            @RequestParam String original,
            @RequestParam(defaultValue = TEMP) String type,
            HttpServletRequest request
    ) {
        try {
            new URI(original);
        } catch (URISyntaxException e) {
            return ResponseResult.error("URI语法错误，请改正后重试！");
        }
        if (original.length() > MAX_ORIGINAL_URI_LENGTH) {
            return ResponseResult.error("原始URI字符长度不能大于768");
        }
        String contextPath = request.getScheme() + "://" + request.getServerName() + (80 == request.getServerPort() ? "" : ":" + request.getServerPort()) + request.getContextPath();
        PersistenceTypeEnum persistenceTypeEnum;
        try {
            persistenceTypeEnum = PersistenceTypeEnum.valueOf(type);
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return ResponseResult.error("非法操作");
        }
        return ResponseResult.success(contextPath + shortUriService.shortUri(original.trim(), persistenceTypeEnum));
    }
}
