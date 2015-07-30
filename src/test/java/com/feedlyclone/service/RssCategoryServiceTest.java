package com.feedlyclone.service;

import com.feedlyclone.BaseSpringTest;
import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.dto.RssCategoryDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RssCategoryServiceTest extends BaseSpringTest {

    @Autowired
    private RssCategoryService categoryService;

    @Autowired
    private AccountService accountService;

    @Test
    public void getCategoryForAccountByTitle(){
        String title = "test title";
        RssCategoryDTO category = new RssCategoryDTO();
        category.setTitle(title);

        AccountDTO account = new AccountDTO();
        account.setRssCategories(Arrays.asList(category));
        account = accountService.save(account);

        RssCategoryDTO storedCategory = categoryService.getCategoryForAccountByTitle(title, account.getId());
        assertNotNull(storedCategory);
        assertEquals(title, storedCategory.getTitle());
    }
}
