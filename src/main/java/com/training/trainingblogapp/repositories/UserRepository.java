package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.dtos.UserRegistrationDTO;
import com.training.trainingblogapp.domain.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    void deleteByUsername(String username);
    User findByEmail(String email);
    User save(UserRegistrationDTO user);
    User save(User user);
    List<User> findAll();

}
