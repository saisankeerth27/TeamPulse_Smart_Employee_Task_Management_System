package com.teampulse.backend.repository;

import com.teampulse.backend.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository
        extends JpaRepository<ChatMessage, Long> {

    @Query("""

            SELECT c
            FROM ChatMessage c
            WHERE
            (c.sender.id = :sender
            AND c.receiver.id = :receiver)
            OR
            (c.sender.id = :receiver
            AND c.receiver.id = :sender)
            ORDER BY c.sentAt

            """)
    List<ChatMessage> getConversation(
            Long sender,
            Long receiver
    );

}
