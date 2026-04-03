package com.lyx.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyx.portal.model.CmsArticleTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签关联Mapper
 */
@Mapper
public interface CmsArticleTagMapper extends BaseMapper<CmsArticleTag> {
}