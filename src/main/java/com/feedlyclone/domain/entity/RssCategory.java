package com.feedlyclone.domain.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * represent rss category of news for share to other user
 */
@Entity
@Table(name = "rss_category")
public class RssCategory extends AbstractModel {

    private String title;

    @ManyToMany(mappedBy = "rssCategory")
    private List<Account> account;

    @ElementCollection
    private List<String> feedUrls = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }

    public List<String> getFeedUrls() {
        return feedUrls;
    }

    public void setFeedUrls(List<String> feedUrls) {
        this.feedUrls = feedUrls;
    }
}
