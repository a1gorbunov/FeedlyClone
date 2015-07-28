package com.feedlyclone.service.impl;

import com.feedlyclone.domain.entity.RssCategory;
import com.feedlyclone.domain.repository.RssCategoryRepository;
import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.dto.RssCategoryDTO;
import com.feedlyclone.service.RssCategoryService;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RssCategoryServiceImpl implements RssCategoryService {

    @Autowired
    private RssCategoryRepository categoryRepository;

    private BoundMapperFacade<RssCategory, RssCategoryDTO> categoryMapper;

    @Autowired
    public RssCategoryServiceImpl(MapperFactory mapperFactory) {
        categoryMapper = mapperFactory.getMapperFacade(RssCategory.class, RssCategoryDTO.class, false);
    }

    @Override
    public List<RssCategoryDTO> getAll(){
        List<RssCategoryDTO> toReturn = new ArrayList<>();
        categoryRepository.findAll().forEach(category ->toReturn.add(categoryMapper.map(category)));
        return toReturn;
    }

    @Override
    @Transactional
    public RssCategoryDTO getByTitle(String titlte){
        RssCategory storedCategory = categoryRepository.findByTitle(titlte);
        if(storedCategory != null){
            return categoryMapper.map(storedCategory);
        }
        return null;
    }

    @Override
    public RssCategoryDTO save(RssCategoryDTO categoryDTO){
        RssCategory category = categoryMapper.mapReverse(categoryDTO);
        category = categoryRepository.save(category);
        categoryDTO.setId(category.getId());
        return categoryDTO;
    }

    @Override
    @Transactional
    public RssCategoryDTO getCategoryForAccountByTitle(String title, long id){
        RssCategory storedCategory = categoryRepository.getCategoryForAccountByTitle(title, id);
        RssCategoryDTO categoryDTO = null;
        if (storedCategory != null) {
            categoryDTO = categoryMapper.map(storedCategory);
        }
        return categoryDTO;
    }

    @Override
    @Transactional
    public RssCategoryDTO getCategoryWithUrls(long id){
        RssCategory category = categoryRepository.findOne(id);
        RssCategoryDTO categoryDTO = null;
        if (category != null) {
            categoryDTO = categoryMapper.map(category);
        }
        return categoryDTO;
    }
}
