package com.lyx.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.portal.mapper.CmsCategoryMapper;
import com.lyx.portal.model.CmsCategory;
import com.lyx.portal.service.CmsCategoryService;
import org.springframework.stereotype.Service;

/**
 * 栏目服务实现类
 */
@Service
public class CmsCategoryServiceImpl extends ServiceImpl<CmsCategoryMapper, CmsCategory> implements CmsCategoryService {
}