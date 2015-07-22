package com.feedlyclone.domain.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * represent rss category of news
 */
@Entity
@Table(name = "rss_category")
public class RssCategory extends AbstractModel {

    private String title;

    private Account account;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
