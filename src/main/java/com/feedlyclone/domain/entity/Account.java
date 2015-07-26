package com.feedlyclone.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * represent account with preferences
 */
@Entity
@Table(name = "account")
public class Account extends AbstractModel {

    @OneToOne(mappedBy = "account")
    private User user;

    @ManyToMany
    @JoinTable(joinColumns = {
            @JoinColumn(name = "rss_category_id") },
               inverseJoinColumns = { @JoinColumn(name = "account_id")})
    private List<RssCategory> rssCategory;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<RssCategory> getRssCategory() {
        return rssCategory;
    }

    public void setRssCategory(List<RssCategory> rssCategory) {
        this.rssCategory = rssCategory;
    }
}
