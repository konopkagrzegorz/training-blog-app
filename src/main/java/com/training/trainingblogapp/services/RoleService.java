package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.model.Role;
import com.training.trainingblogapp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    private UserService userService;

    @Autowired
    public RoleService(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
