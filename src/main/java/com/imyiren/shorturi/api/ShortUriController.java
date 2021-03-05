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
        if (!original.startsWith(ORIGINAL_PREFIX)) {
            return ResponseResult.error("原始地址格式不正确");
        }
        if (original.length() > MAX_ORIGINAL_URI_LENGTH) {
            return ResponseResult.error("原始地址字符长度不能大于768");
        }
        String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + (80 == request.getServerPort() ? "" : request.getServerPort()) + request.getContextPath();
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
