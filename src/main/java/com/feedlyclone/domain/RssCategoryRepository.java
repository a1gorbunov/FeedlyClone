package com.feedlyclone.domain;

import com.feedlyclone.domain.entity.RssCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RssCategoryRepository extends JpaRepository<RssCategory, Long> {

}
