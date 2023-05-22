package com.adem.currency_bot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


//Entity class for db
@Entity
@Table(name = "user_message")
@Data
@NoArgsConstructor
public class UserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String text;
    private Long chatId;
    private Integer messageId;

    public UserMessage(String username, String text,Long chatId,Integer messageId){
        this.username = username;
        this.text = text;
        this.chatId = chatId;
        this.messageId = messageId;
    }

}
