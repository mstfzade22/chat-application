package com.mahammad.chatapp.service.implementation;

import com.mahammad.chatapp.dto.request.SendMessageRequestDTO;
import com.mahammad.chatapp.exception.ChatException;
import com.mahammad.chatapp.exception.MessageException;
import com.mahammad.chatapp.exception.UserException;
import com.mahammad.chatapp.model.Chat;
import com.mahammad.chatapp.model.Message;
import com.mahammad.chatapp.model.User;
import com.mahammad.chatapp.repository.MessageRepository;
import com.mahammad.chatapp.service.ChatService;
import com.mahammad.chatapp.service.MessageService;
import com.mahammad.chatapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserService userService;
    private final ChatService chatService;
    private final MessageRepository messageRepository;

    @Override
    public Message sendMessage(SendMessageRequestDTO req, UUID userId) throws UserException, ChatException {

        User user = userService.findUserById(userId);
        Chat chat = chatService.findChatById(req.chatId());

        Message message = Message.builder()
                .chat(chat)
                .user(user)
                .content(req.content())
                .timeStamp(LocalDateTime.now())
                .readBy(new HashSet<>(Set.of(user.getId())))
                .build();

        chat.getMessages().add(message);

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getChatMessages(UUID chatId, User reqUser) throws UserException, ChatException {

        Chat chat = chatService.findChatById(chatId);

        if (!chat.getUsers().contains(reqUser)) {
            throw new UserException("User isn't related to chat " + chatId);
        }

        return messageRepository.findByChat_Id(chat.getId());
    }

    @Override
    public Message findMessageById(UUID messageId) throws MessageException {

        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent()) {
            return message.get();
        }

        throw new MessageException("Message not found " + messageId);
    }

    @Override
    public void deleteMessageById(UUID messageId, User reqUser) throws UserException, MessageException {

        Message message = findMessageById(messageId);

        if (message.getUser().getId().equals(reqUser.getId())) {
            messageRepository.deleteById(messageId);
            return;
        }

        throw new UserException("User is not related to message " + message.getId());
    }

}
