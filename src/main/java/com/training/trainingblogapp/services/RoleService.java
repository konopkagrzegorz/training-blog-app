package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.model.Role;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.repositories.RoleRepository;
import com.training.trainingblogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void save(User user) {
        Optional<Role> role = roleRepository.findByName(user.getRole().getName());
        if (role.isEmpty()) {
            throw new RuntimeException("User does not have any role!");
        }
        role.get().getUsers().add(user);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
