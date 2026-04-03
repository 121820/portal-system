package com.lyx.portal.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 文章标签关联实体类
 */
@Data
@TableName("cms_article_tag")
public class CmsArticleTag {
    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 标签ID
     */
    private Long tagId;
}