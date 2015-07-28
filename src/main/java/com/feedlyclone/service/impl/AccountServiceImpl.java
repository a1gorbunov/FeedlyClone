package com.feedlyclone.service.impl;

import com.feedlyclone.domain.entity.Account;
import com.feedlyclone.domain.entity.RssCategory;
import com.feedlyclone.domain.repository.AccountRepository;
import com.feedlyclone.domain.repository.RssCategoryRepository;
import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.service.AccountService;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = Logger.getLogger(AccountService.class.getSimpleName());

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RssCategoryRepository categoryRepository;

    private BoundMapperFacade<Account, AccountDTO> accountMapper;

    @Autowired
    public AccountServiceImpl(MapperFactory mapperFactory) {
        accountMapper = mapperFactory.getMapperFacade(Account.class, AccountDTO.class, false);
    }

    @Override
    public AccountDTO save(@NotNull AccountDTO accountDTO){
        Account account = accountMapper.mapReverse(accountDTO);
        account = accountRepository.save(account);
        accountDTO.setId(account.getId());
        return accountDTO;
    }

    @Override
    public AccountDTO getAccountById(Long id){
        AccountDTO accountDTO = null;
        Account foundedAccount = accountRepository.findOne(id);
        if (foundedAccount != null) {
            accountDTO = accountMapper.map(foundedAccount);
        }
        return accountDTO;
    }

    @Override
    public void addFeedToAccount(long accountId, String feedTitle, String newFeedUrl){
        LOGGER.debug("trying to add feed={"+newFeedUrl+"} to account with id="+accountId);
        Account accountStored = accountRepository.findOne(accountId);
        List<RssCategory> categories = accountStored.getRssCategories();
        RssCategory categoryToStore = new RssCategory();
        if (CollectionUtils.isEmpty(categories)) {
            categoryToStore.setTitle(feedTitle);
            categoryToStore.setFeedUrls(Arrays.asList(newFeedUrl));
            categoryToStore.setAccounts(Arrays.asList(accountStored));
            categoryToStore = categoryRepository.save(categoryToStore);
            accountStored.setRssCategories(Arrays.asList(categoryToStore));
            accountRepository.save(accountStored);
        } else {
            categoryToStore = categoryRepository.getCategoryForAccountByTitle(feedTitle, accountStored.getId());
            if (categoryToStore == null){
                categoryToStore = new RssCategory();
                categoryToStore.setTitle(feedTitle);
                categoryToStore.setAccounts(Arrays.asList(accountStored));
                categoryToStore.setFeedUrls(Arrays.asList(newFeedUrl));
                categoryToStore = categoryRepository.save(categoryToStore);
                categories.add(categoryToStore);
                accountRepository.save(accountStored);
            } else {
                categoryToStore = categoryRepository.getCategoryWithUrls(categoryToStore.getId());
                categoryToStore.getFeedUrls().add(newFeedUrl);
                categoryRepository.save(categoryToStore);
            }
        }
        LOGGER.debug("feed={" + newFeedUrl + "} to account with id=" + accountId + " added succesfully");
    }
}
