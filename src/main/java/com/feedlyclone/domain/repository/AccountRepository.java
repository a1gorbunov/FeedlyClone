package com.feedlyclone.domain.repository;

import com.feedlyclone.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
