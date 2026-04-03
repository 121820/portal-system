package com.lyx.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.portal.mapper.CmsArticleMapper;
import com.lyx.portal.model.CmsArticle;
import com.lyx.portal.service.CmsArticleService;
import org.springframework.stereotype.Service;

/**
 * 文章服务实现类
 */
@Service
public class CmsArticleServiceImpl extends ServiceImpl<CmsArticleMapper, CmsArticle> implements CmsArticleService {
}