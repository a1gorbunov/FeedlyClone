package com.feedlyclone.mapper;

import com.feedlyclone.domain.entity.Account;
import com.feedlyclone.domain.entity.RssCategory;
import com.feedlyclone.domain.entity.User;
import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.dto.RssCategoryDTO;
import com.feedlyclone.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapper implements FeedCustomMapper<Account, AccountDTO> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RssCategoryMapper categoryMapper;

    @Override
    public void mapAtoB(Account account, AccountDTO accountDTO) {
        if (account != null && accountDTO != null){
            accountDTO.setId(account.getId());
            if (account.getUser() != null) {
                User user = account.getUser();
                UserDTO userDto = new UserDTO();
                userDto.setId(user.getId());
                userDto.setAccount(accountDTO);
                userDto.setName(user.getName());
                userDto.setPassword(user.getPassword());
                accountDTO.setUser(userDto);
            }
            if (account.getRssCategories() != null){
                List<RssCategoryDTO> categories = new ArrayList<>();
                account.getRssCategories().forEach(rssCategory -> {
                    RssCategoryDTO category = new RssCategoryDTO();
                    category.setId(rssCategory.getId());
                    category.setTitle(rssCategory.getTitle());
                    categories.add(category);
                });
                accountDTO.setRssCategories(categories);
            }
        }
    }

    @Override
    public void mapBtoA(AccountDTO accountDTO, Account account) {
        if (account != null && accountDTO != null){
            account.setId(accountDTO.getId());
            if (accountDTO.getUser() != null) {
                UserDTO userDto = accountDTO.getUser();
                User user = new User();
                user.setId(accountDTO.getId());
                user.setAccount(account);
                user.setName(userDto.getName());
                user.setPassword(userDto.getPassword());
                account.setUser(user);
            }
            if (accountDTO.getRssCategories() != null){
                List<RssCategory> categories = new ArrayList<>();
                accountDTO.getRssCategories().forEach(rssCategoryDTO -> {
                    RssCategory category = new RssCategory();
                    category.setTitle(rssCategoryDTO.getTitle());
                    category.setId(rssCategoryDTO.getId());
                    categories.add(category);
                });
                account.setRssCategories(categories);
            }
        }
    }

    @Override
    public AccountDTO map(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        mapAtoB(account, accountDTO);
        return accountDTO;
    }

    @Override
    public Account mapReverse(AccountDTO accountDTO) {
        Account account = new Account();
        mapBtoA(accountDTO, account);
        return account;
    }
}
