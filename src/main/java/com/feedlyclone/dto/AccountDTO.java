package com.feedlyclone.dto;

import java.util.ArrayList;
import java.util.List;

public class AccountDTO {

    private Long id;

    private List<RssCategoryDTO> rssCategories;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RssCategoryDTO> getRssCategories() {
        if (rssCategories == null){
            rssCategories = new ArrayList<>();
        }
        return rssCategories;
    }

    public void setRssCategories(List<RssCategoryDTO> rssCategories) {
        this.rssCategories = rssCategories;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
