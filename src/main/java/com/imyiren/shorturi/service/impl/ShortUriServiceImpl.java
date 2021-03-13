package com.imyiren.shorturi.service.impl;

import com.imyiren.shorturi.common.RadixUtil;
import com.imyiren.shorturi.dao.ShortUriDAO;
import com.imyiren.shorturi.entity.ShortUriDO;
import com.imyiren.shorturi.enums.PersistenceTypeEnum;
import com.imyiren.shorturi.service.ShortUriService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author yiren
 */
@Slf4j
@Service
public class ShortUriServiceImpl implements ShortUriService {

    private static final String PREFIX = "";
    private static final Pattern URI_PATTERN_ONE = Pattern.compile("^[A-Za-z]+://.+$");
    private static final Pattern URI_PATTERN_TWO = Pattern.compile("^[A-Za-z]+:.+$");


    @Resource
    private ShortUriDAO shortUriDAO;

    @Override
    public String shortUri(String originalUri, PersistenceTypeEnum type) {
        if (!URI_PATTERN_ONE.matcher(originalUri).matches() && !URI_PATTERN_TWO.matcher(originalUri).matches()) {
            originalUri = "http://" + originalUri;
        }
        if (originalUri.endsWith("/")) {
            originalUri = originalUri.substring(0, originalUri.length() - 1);
        }
        final String uri = originalUri;
        String to62radix = Optional.ofNullable(shortUriDAO.selectByOriginalUri(originalUri))
                .map(ShortUriDO::getId)
                .map(RadixUtil::to62RadixString)
                .orElseGet(() -> {
                    ShortUriDO newShortUriDO = new ShortUriDO();
                    newShortUriDO.setOriginalUri(uri);
                    newShortUriDO.setPersistence(type.toString());
                    if (PersistenceTypeEnum.TEMP.equals(type)) {
                        newShortUriDO.setExpireTime(LocalDateTime.now().plusDays(30));
                    }
                    shortUriDAO.insertSelective(newShortUriDO);
                    return RadixUtil.to62RadixString(newShortUriDO.getId());
                });
        log.info("short uri, type: {}, id: {}, result radix: {}, original: {}", type, RadixUtil.reverse62Radix(to62radix), to62radix, originalUri);
        return PREFIX + to62radix;
    }

    @Override
    public String originalUri(String key) {
        long id = RadixUtil.reverse62Radix(key);
        ShortUriDO shortUriDO = Optional.ofNullable(shortUriDAO.selectByPrimaryKey(id))
                .orElseThrow(
                        () -> new RuntimeException("没有匹配的原始地址")
                );
        log.info("key: {}, id: {}, originalUri: {}", key, shortUriDO.getId(), shortUriDO.getOriginalUri());
        return shortUriDO.getOriginalUri();
    }

}
