package com.mahammad.chatapp.service;

import com.mahammad.chatapp.dto.request.SendMessageRequestDTO;
import com.mahammad.chatapp.exception.ChatException;
import com.mahammad.chatapp.exception.MessageException;
import com.mahammad.chatapp.exception.UserException;
import com.mahammad.chatapp.model.Message;
import com.mahammad.chatapp.model.User;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    Message sendMessage(SendMessageRequestDTO req, UUID userId) throws UserException, ChatException;

    List<Message> getChatMessages(UUID chatId, User reqUser) throws UserException, ChatException;

    Message findMessageById(UUID messageId) throws MessageException;

    void deleteMessageById(UUID messageId, User reqUser) throws UserException, MessageException;

}
