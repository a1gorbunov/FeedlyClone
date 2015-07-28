package com.feedlyclone.mapper;

import com.feedlyclone.domain.entity.Account;
import com.feedlyclone.domain.entity.RssCategory;
import com.feedlyclone.dto.AccountDTO;
import com.feedlyclone.dto.RssCategoryDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class RssCategoryMapper extends CustomMapper<RssCategory, RssCategoryDTO> {

    @Override
    public void mapAtoB(RssCategory category, RssCategoryDTO categoryDTO, MappingContext context) {
        if (category != null && categoryDTO != null){
            categoryDTO.setId(category.getId());
            categoryDTO.setTitle(category.getTitle());
            if (category.getAccounts() != null) {
                ArrayList<AccountDTO> accounts = new ArrayList<>();
                category.getAccounts().forEach(account -> {
                    AccountDTO accountDTO = new AccountDTO();
                    accountDTO.setId(account.getId());
                    accounts.add(accountDTO);
                });
                categoryDTO.setAccounts(accounts);
            }
            if (category.getFeedUrls() != null){
                categoryDTO.setFeedUrls(category.getFeedUrls().stream().filter(s -> true).collect(Collectors.toList()));
            }
        }
    }

    @Override
    public void mapBtoA(RssCategoryDTO categoryDTO, RssCategory category, MappingContext context) {
        if (category != null && categoryDTO != null){
            category.setId(categoryDTO.getId());
            category.setTitle(categoryDTO.getTitle());
            if (categoryDTO.getAccounts() != null) {
                ArrayList<Account> accounts = new ArrayList<>();
                categoryDTO.getAccounts().forEach(accountDTO -> {
                    Account account = new Account();
                    category.setId(account.getId());
                    accounts.add(account);
                });
                category.setAccounts(accounts);
            }
            if (categoryDTO.getFeedUrls() != null){
                category.setFeedUrls(categoryDTO.getFeedUrls().stream().filter(s -> true).collect(Collectors.toList()));
            }
        }
    }
}
