package com.grootan.kafka.repository;

import com.grootan.kafka.model.ConsumeMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * * @author <a href="sheikabdullah.m@grootan.com">sheik abdullah</a>
 */
@Repository
public interface ConsumerRepository extends JpaRepository<ConsumeMessage, String> {
    @Query(value = "select * from consume_message where sender_id=?1",nativeQuery = true)
    ConsumeMessage getByMessageId(String messageId);
}
