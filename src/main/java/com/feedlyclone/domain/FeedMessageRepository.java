package com.feedlyclone.domain;

import com.feedlyclone.domain.entity.FeedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedMessageRepository extends JpaRepository<FeedMessage, Long> {

}
