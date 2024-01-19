package service;

import model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(long userId);

    boolean addGoldToClan(long userId, int contribution);

    boolean changeUserGold(long userId, int contribution);
}
