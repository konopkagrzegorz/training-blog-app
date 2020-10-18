package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.model.Role;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.repositories.RoleRepository;
import com.training.trainingblogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public Role save(User user) {
        if (user.getRole() == null) {
            throw new RuntimeException("User does not have any role!");
        }
        Role role = roleRepository.findByName(user.getRole().toString());
        role.getUsers().add(user);
        return role;
    }
}
