package com.example.user.service.services;

import java.util.List;

import com.example.user.service.exception.UserException;
import com.example.user.service.model.User;

public interface UserService {

	public User saveUser(User user) throws UserException;

	public User getUser(String userId) throws UserException;

	public List<User> getAllUser() throws UserException;

}
