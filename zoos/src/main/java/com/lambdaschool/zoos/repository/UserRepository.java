package com.lambdaschool.zoos.repository;

import com.lambdaschool.zoos.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByUsernameContainingIgnoreCase(String name);

    User findByUsername(String username);
}
