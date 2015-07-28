package com.feedlyclone.service;

import com.feedlyclone.dto.AccountDTO;

public interface AccountService {

    AccountDTO save(AccountDTO entity);

    AccountDTO getAccountById(Long id);

    void addFeedToAccount(long accountId, String feedTitle, String newFeedUrl);
}
