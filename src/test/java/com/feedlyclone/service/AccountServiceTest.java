package com.feedlyclone.service;

import com.feedlyclone.BaseSpringTest;
import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.dto.RssCategoryDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AccountServiceTest extends BaseSpringTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RssCategoryService rssCategoryService;

    @Test
    public void addFeedToAccount(){
        String testUrl = "test url";
        String testTitle = "test title";
        String testUrl2 = "test url 2";
        String testTitle2 = "test title 2";
        String testUrl3 = "test url 3";

        // test new account and new category
        AccountDTO account = accountService.save(new AccountDTO());
        accountService.addFeedToAccount(account.getId(), testTitle, testUrl);
        account = accountService.getAccountById(account.getId());

        assertNotNull(account.getRssCategories());
        assertEquals(1, account.getRssCategories().size());
        assertNotNull(account.getRssCategories().get(0));

        RssCategoryDTO category = rssCategoryService.getCategoryWithUrls(account.getRssCategories().get(0).getId());
        assertNotNull(category.getFeedUrls());
        assertEquals(1, category.getFeedUrls().size());
        assertEquals(testUrl, category.getFeedUrls().get(0));
        assertEquals(testTitle, category.getTitle());

        // test filled account and new category
        accountService.addFeedToAccount(account.getId(), testTitle2, testUrl2);
        account = accountService.getAccountById(account.getId());

        assertNotNull(account.getRssCategories());
        assertEquals(2, account.getRssCategories().size());

        category = rssCategoryService.getCategoryForAccountByTitle(testTitle2, account.getId());
        assertNotNull(category);
        category = rssCategoryService.getCategoryWithUrls(category.getId());

        assertNotNull(category.getFeedUrls());
        assertEquals(1, category.getFeedUrls().size());
        assertEquals(testUrl2, category.getFeedUrls().get(0));
        assertEquals(testTitle2, category.getTitle());

        // test filled account and filled categoy
        accountService.addFeedToAccount(account.getId(), testTitle2, testUrl3);
        account = accountService.getAccountById(account.getId());

        assertNotNull(account.getRssCategories());
        assertEquals(2, account.getRssCategories().size());

        category = rssCategoryService.getCategoryForAccountByTitle(testTitle2, account.getId());
        assertNotNull(category);
        category = rssCategoryService.getCategoryWithUrls(category.getId());

        assertNotNull(category.getFeedUrls());
        assertEquals(2, category.getFeedUrls().size());
        assertTrue(category.getFeedUrls().contains(testUrl3));
        assertEquals(testTitle2, category.getTitle());
    }
}
