package com.mahammad.chatapp.repository;

import com.mahammad.chatapp.model.Chat;
import com.mahammad.chatapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    //Purpose: find every Chat that a given user participates in.
    @Query("select c from Chat c join c.users u where u.id = :userId")
    List<Chat> findChatByUserId(@Param("userId") UUID userId);



    //Purpose: to find a single chat where
    @Query("SELECT c FROM Chat c WHERE c.isGroup = false AND :user2 MEMBER OF c.users AND :reqUser MEMBER OF c.users")
    Optional<Chat> findSingleChatByUsers(@Param("user2") User user2, @Param("reqUser") User reqUser);

}


