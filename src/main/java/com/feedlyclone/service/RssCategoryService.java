package com.feedlyclone.service;

import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.dto.RssCategoryDTO;

import java.util.List;

public interface RssCategoryService {

    List<RssCategoryDTO> getAll();

    RssCategoryDTO getByTitle(String titlte);

    RssCategoryDTO save(RssCategoryDTO category);

    RssCategoryDTO getCategoryForAccountByTitle(String title, long id);

    RssCategoryDTO getCategoryWithUrls(long id);
}
