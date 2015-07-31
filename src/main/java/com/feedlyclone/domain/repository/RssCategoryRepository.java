package com.feedlyclone.domain.repository;

import com.feedlyclone.domain.entity.RssCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RssCategoryRepository extends JpaRepository<RssCategory, Long> {

    RssCategory findByTitle(String title);

    @Query("select cat from RssCategory cat join cat.accounts ac where cat.title = :title and ac.id = :accountId group by cat")
    RssCategory getCategoryForAccountByTitle(@Param("title") String title, @Param("accountId") Long accountId);

    @Query("select cat from RssCategory cat join fetch cat.feedUrls where cat.id= :categoryId")
    RssCategory getCategoryWithUrls(@Param("categoryId") long categoryId);

    @Query("select cat from RssCategory cat join cat.accounts ac where ac.id = :accountId")
    List<RssCategory> getByAccountId(@Param("accountId") long id);
}
