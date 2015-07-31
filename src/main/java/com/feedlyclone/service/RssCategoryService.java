package com.feedlyclone.service;

import com.feedlyclone.domain.entity.RssCategory;
import com.feedlyclone.domain.repository.AccountRepository;
import com.feedlyclone.domain.repository.RssCategoryRepository;
import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.dto.RssCategoryDTO;
import com.feedlyclone.mapper.RssCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  implement methods for work with user's feed categories
 */

@Service
@Transactional
public class RssCategoryService {

    @Autowired
    private RssCategoryRepository categoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RssCategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<RssCategoryDTO> getAll(){
        List<RssCategoryDTO> toReturn = new ArrayList<>();
        categoryRepository.findAll().forEach(category ->toReturn.add(categoryMapper.map(category)));
        return toReturn;
    }

    @Transactional(readOnly = true)
    public RssCategoryDTO getByTitle(String titlte){
        RssCategory storedCategory = categoryRepository.findByTitle(titlte);
        if(storedCategory != null){
            return categoryMapper.map(storedCategory);
        }
        return null;
    }

    public RssCategoryDTO save(RssCategoryDTO categoryDTO){
        RssCategory category = categoryMapper.mapReverse(categoryDTO);
        category = categoryRepository.save(category);
        categoryDTO.setId(category.getId());
        return categoryDTO;
    }

    @Transactional(readOnly = true)
    public RssCategoryDTO getCategoryForAccountByTitle(String title, long id){
        RssCategory storedCategory = categoryRepository.getCategoryForAccountByTitle(title, id);
        RssCategoryDTO categoryDTO = null;
        if (storedCategory != null) {
            categoryDTO = categoryMapper.map(storedCategory);
        }
        return categoryDTO;
    }

    @Transactional(readOnly = true)
    public RssCategoryDTO getCategoryWithUrls(long id){
        RssCategory category = categoryRepository.findOne(id);
        RssCategoryDTO categoryDTO = null;
        if (category != null) {
            categoryDTO = categoryMapper.map(category);
        }
        return categoryDTO;
    }

    @Transactional(readOnly = true)
    public List<RssCategoryDTO> getCategoriesForAccount(AccountDTO accountDTO) {
        List<RssCategoryDTO> result = new ArrayList<>();
        if (accountDTO != null && accountDTO.getId() != null) {
            List<RssCategory> categories = categoryRepository.getByAccountId(accountDTO.getId());
            if (!CollectionUtils.isEmpty(categories)) {
                categories.forEach(category -> result.add(categoryMapper.map(category)));
            }
        }
        return result;
    }
}
