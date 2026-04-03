package com.lyx.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyx.portal.model.CmsArticle;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章Mapper
 */
@Mapper
public interface CmsArticleMapper extends BaseMapper<CmsArticle> {
}