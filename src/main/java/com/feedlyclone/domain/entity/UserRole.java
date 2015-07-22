package com.feedlyclone.domain.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole extends AbstractModel {

    private User user;

    private Integer roleId;

    @ManyToOne
    @JoinColumn(name = "userRole", nullable = true)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
