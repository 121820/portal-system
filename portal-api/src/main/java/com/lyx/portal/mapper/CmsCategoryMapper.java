package com.lyx.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyx.portal.model.CmsCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 栏目Mapper
 */
@Mapper
public interface CmsCategoryMapper extends BaseMapper<CmsCategory> {
}