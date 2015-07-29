package com.feedlyclone.service;

import com.feedlyclone.domain.entity.Account;
import com.feedlyclone.domain.entity.RssCategory;
import com.feedlyclone.domain.repository.AccountRepository;
import com.feedlyclone.domain.repository.RssCategoryRepository;
import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.exceptions.NotFoundException;
import com.feedlyclone.mapper.AccountMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class AccountService {
    private static final Logger LOGGER = Logger.getLogger(AccountService.class.getSimpleName());

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RssCategoryRepository categoryRepository;

    private AccountMapper accountMapper = new AccountMapper();

    public AccountDTO save(@NotNull AccountDTO accountDTO){
        Account account = accountMapper.mapReverse(accountDTO);

//        RssCategory category = categoryRepository.findOne(account.getRssCategories().iterator().next().getId());
//        account.getRssCategories().clear();
//        account.getRssCategories().add(category);

        account = accountRepository.save(account);
        accountDTO.setId(account.getId());
        return accountDTO;
    }

    @Transactional(readOnly = true)
    public AccountDTO getAccountById(Long id){
        AccountDTO accountDTO = null;
        Account foundedAccount = accountRepository.findOne(id);
        if (foundedAccount != null) {
            accountDTO = accountMapper.map(foundedAccount);
        }
        return accountDTO;
    }

    public void addFeedToAccount(long accountId, String feedTitle, String newFeedUrl) throws NotFoundException {
        LOGGER.debug("trying to add feed={"+newFeedUrl+"} to account with id="+accountId);
        Account accountStored = accountRepository.findOne(accountId);
        if (accountStored == null){
            throw new NotFoundException("account with id={"+accountId+"} not found");
        }
        RssCategory categoryToStore = categoryRepository.getCategoryForAccountByTitle(feedTitle, accountStored.getId());
        if (categoryToStore == null){
            createNewCategoryList(feedTitle, Arrays.asList(newFeedUrl), accountStored);
        } else {
            categoryToStore.getFeedUrls().add(newFeedUrl);
            categoryRepository.save(categoryToStore);
        }
        LOGGER.debug("feed={" + newFeedUrl + "} to account with id=" + accountId + " added succesfully");
    }

    /**
     *  create new category list for specified account
     * @param feedTitle  category title
     * @param newFeedUrl array of urls for feed
     * @param accountStored account for bind
     */
    public void createNewCategoryList(String feedTitle, List<String> newFeedUrl, Account accountStored){
        RssCategory categoryToStore = new RssCategory();
        categoryToStore.setTitle(feedTitle);
        categoryToStore.setFeedUrls(new ArrayList<>(newFeedUrl));
        categoryToStore.setAccounts(new ArrayList<>(Arrays.asList(accountStored)));
        categoryToStore = categoryRepository.save(categoryToStore);

        List<RssCategory> categories = accountStored.getRssCategories();
        if (CollectionUtils.isEmpty(categories)){
            accountStored.setRssCategories(new ArrayList<>(Arrays.asList(categoryToStore)));
        } else {
            categories.add(categoryToStore);
        }

        accountRepository.save(accountStored);
    }


}
