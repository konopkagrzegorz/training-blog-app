package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {

    Role findByName(String name);
    List<Role> findAll();

}
