package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.UserRegistrationDTO;
import com.training.trainingblogapp.domain.model.Role;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.repositories.RoleRepository;
import com.training.trainingblogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private MappingService mappingService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;


    @Autowired
    public UserService(MappingService mappingService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.mappingService = mappingService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(UserRegistrationDTO userRegistrationDTO) {
        User temp = mappingService.userRegistrationDtoToUser(userRegistrationDTO);
        temp.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        temp.setRole(roleRepository.findByName("ROLE_USER"));
        return userRepository.save(temp);
    }

    public void delete() {

    }

        @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User temp = findByUsername(username);

        if (temp == null) {
            throw new UsernameNotFoundException("Invalid username and/or password");
        }

        Collection collection = new HashSet();
        collection.add(temp.getRole());

        return new org.springframework.security.core.userdetails.User(temp.getUsername(),
                temp.getPassword(), mapRolesToAuthorities(collection));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

        return roles.stream()
                .map(roleTemp -> new SimpleGrantedAuthority(roleTemp.getName()))
                .collect(Collectors.toList());

    }
}
