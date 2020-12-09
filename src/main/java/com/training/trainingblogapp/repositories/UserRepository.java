package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.dtos.UserRegistrationDTO;
import com.training.trainingblogapp.domain.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);
    Optional<User> findByEmail(String email);
    User save(UserRegistrationDTO user);
    User save(User user);
    List<User> findAll();

}
