package com.mahammad.chatapp.service;

import com.mahammad.chatapp.dto.request.UpdateUserRequestDTO;
import com.mahammad.chatapp.exception.UserException;
import com.mahammad.chatapp.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User findUserById(UUID id) throws UserException;

    User findUserByProfile(String jwt) throws UserException;

    User updateUser(UUID id, UpdateUserRequestDTO request) throws UserException;

    List<User> searchUser(String query);

    List<User> searchUserByName(String name);

}
