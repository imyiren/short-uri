package com.imyiren.shorturi.dao;

import com.imyiren.shorturi.entity.ShortUriDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ShortUriDAO {
    /**
     * 主键删除
     * @param id id
     * @return 生效数量
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 全字段插入
     * @param record shortUri
     * @return ShortUriDO
     */
    int insert(ShortUriDO record);

    /**
     * 部分插入
     * @param record shortUri
     * @return 生效熟料
     */
    int insertSelective(ShortUriDO record);

    /**
     * 主键查询
     * @param id id
     * @return ShortUriDO
     */
    ShortUriDO selectByPrimaryKey(Long id);

    /**
     * 主键查询
     * @param originalUri originalUri
     * @return ShortUriDO
     */
    ShortUriDO selectByOriginalUri(String originalUri);

    /**
     * id部分更新
     * @param record shortUri
     * @return 生效数量
     */
    int updateByPrimaryKeySelective(ShortUriDO record);

    /**
     * 全量更新
     * @param record shortUri
     * @return 生效数量
     */
    int updateByPrimaryKey(ShortUriDO record);
}