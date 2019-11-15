package com.lambdaschool.zoos;

import com.lambdaschool.zoos.models.User;
import com.lambdaschool.zoos.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class SeedData implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {

        List<User> userList = new ArrayList<>();
        userService.findAll().iterator().forEachRemaining(userList::add);

        for (User u : userList) {
            u.setUserroles(new ArrayList<>());
            userService.update(u, u.getUserid());
        }
    }
}
