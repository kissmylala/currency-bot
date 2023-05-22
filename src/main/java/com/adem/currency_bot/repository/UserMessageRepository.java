package com.adem.currency_bot.repository;

import com.adem.currency_bot.model.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMessageRepository extends JpaRepository<UserMessage,Long> {
    Optional<List<UserMessage>> findAllByChatId(Long chatId);
}
