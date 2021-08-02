package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public int createUser(User user) {
        if (isUsernameAvailable(user.getUsername())) {
            String encodedSalt = hashService.generateSalt();
            String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
            return userMapper.createUser(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
        }
        return -1;
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    public boolean updateUser(User user) {
        User optionalUser = userMapper.getUser(user.getUsername());

        if (optionalUser != null) {

            if (!optionalUser.getPassword().equals(user.getPassword())) {
                String encodedSalt = hashService.generateSalt();
                String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
                user.setSalt(encodedSalt);
                user.setPassword(hashedPassword);
            }
            int update = userMapper.updateUser(user);
            return update > 0;
        }
        return false;
    }

    public boolean deleteUser(int id) {
        User optionalUser = userMapper.getUserById(id);
        if(optionalUser != null) {
            userMapper.deleteUser(id);
            return true;
        }
        return false;
    }
}
