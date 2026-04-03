package com.lyx.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyx.portal.model.CmsArticle;
import com.lyx.portal.service.CmsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 文章控制器
 */
@RestController
@RequestMapping("/api/v1/articles")
public class CmsArticleController {

    @Autowired
    private CmsArticleService cmsArticleService;

    /**
     * 获取文章列表
     */
    @GetMapping
    public IPage<CmsArticle> getArticles(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(required = false) Long categoryId,
                                       @RequestParam(defaultValue = "1") Integer status) {
        // TODO: 添加条件查询逻辑
        return cmsArticleService.page(new Page<>(page, size));
    }

    /**
     * 获取文章详情
     */
    @GetMapping("/{id}")
    public CmsArticle getArticle(@PathVariable Long id) {
        return cmsArticleService.getById(id);
    }

    /**
     * 创建文章
     */
    @PostMapping
    public CmsArticle createArticle(@RequestBody CmsArticle article) {
        cmsArticleService.save(article);
        return article;
    }

    /**
     * 更新文章
     */
    @PutMapping("/{id}")
    public CmsArticle updateArticle(@PathVariable Long id, @RequestBody CmsArticle article) {
        article.setId(id);
        cmsArticleService.updateById(article);
        return article;
    }

    /**
     * 删除文章
     */
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id) {
        cmsArticleService.removeById(id);
    }

    /**
     * 发布文章
     */
    @PostMapping("/{id}/publish")
    public CmsArticle publishArticle(@PathVariable Long id) {
        CmsArticle article = cmsArticleService.getById(id);
        article.setStatus(1); // 1 = 已发布
        cmsArticleService.updateById(article);
        return article;
    }

    /**
     * 草稿文章
     */
    @PostMapping("/{id}/draft")
    public CmsArticle draftArticle(@PathVariable Long id) {
        CmsArticle article = cmsArticleService.getById(id);
        article.setStatus(0); // 0 = 草稿
        cmsArticleService.updateById(article);
        return article;
    }
}