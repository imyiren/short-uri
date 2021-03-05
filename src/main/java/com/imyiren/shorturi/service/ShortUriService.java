package com.imyiren.shorturi.service;

import com.imyiren.shorturi.enums.PersistenceTypeEnum;

public interface ShortUriService {

    /**
     * 获取短链接
     *
     * @param originalUri 原始Uri
     * @return key
     */
    String shortUri(String originalUri, PersistenceTypeEnum type);


    /**
     * 获取原始地址
     * @param key 62 radix key
     * @return original uri
     */
    String originalUri(String key);
}
