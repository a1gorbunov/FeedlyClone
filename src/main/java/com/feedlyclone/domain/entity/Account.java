package com.feedlyclone.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * represent account with preferences
 */
@Entity
@Table(name = "account")
public class Account extends AbstractModel {

    @OneToOne(mappedBy = "account")
    private User user;

    private RssCategory rssCategory;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(cascade = CascadeType.REMOVE)
    public RssCategory getRssCategory() {
        return rssCategory;
    }

    public void setRssCategory(RssCategory rssCategory) {
        this.rssCategory = rssCategory;
    }
}
