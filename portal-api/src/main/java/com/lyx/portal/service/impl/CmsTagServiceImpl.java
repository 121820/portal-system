package com.lyx.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.portal.mapper.CmsTagMapper;
import com.lyx.portal.model.CmsTag;
import com.lyx.portal.service.CmsTagService;
import org.springframework.stereotype.Service;

/**
 * 标签服务实现类
 */
@Service
public class CmsTagServiceImpl extends ServiceImpl<CmsTagMapper, CmsTag> implements CmsTagService {
}