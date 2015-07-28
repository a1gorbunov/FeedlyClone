package com.feedlyclone.dto;

import java.util.ArrayList;
import java.util.List;

public class RssCategoryDTO {

    private Long id;

    private String title;

    private List<AccountDTO> accounts;

    private List<String> feedUrls = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public List<String> getFeedUrls() {
        return feedUrls;
    }

    public void setFeedUrls(List<String> feedUrls) {
        this.feedUrls = feedUrls;
    }
}
