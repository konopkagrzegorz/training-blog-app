package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User save(User user);
    List<User> findAll();

}
