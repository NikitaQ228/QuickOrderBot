package ru.nikita.QuickOrderBot.repository.criteria;

import ru.nikita.QuickOrderBot.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
}
