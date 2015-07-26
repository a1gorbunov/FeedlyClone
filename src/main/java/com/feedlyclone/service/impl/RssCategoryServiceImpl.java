package com.feedlyclone.service.impl;

import com.feedlyclone.domain.RssCategoryRepository;
import com.feedlyclone.domain.entity.RssCategory;
import com.feedlyclone.service.RssCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RssCategoryServiceImpl implements RssCategoryService {

    @Autowired
    private RssCategoryRepository categoryRepository;

    @Override
    public List<RssCategory> getAll(){
        return categoryRepository.findAll();
    }
}
