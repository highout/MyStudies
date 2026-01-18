package com.example.demo.service;

import com.example.demo.repository.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    public List<User> helloW(){
        return List.of(
                new User(1L, "Sergey", "blabla@gmail.com", LocalDate.of(1990, 1, 1), 25),
                new User(2L, "Marry", "marrya@gmail.com", LocalDate.of(1993, 2, 2), 34),
                new User(3L, "Ivan", "Ivan@gmail.com", LocalDate.of(1970, 3, 3), 33)
        );
    }
}
