package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.UserDTO;
import com.training.trainingblogapp.domain.dtos.UserPasswordDTO;
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

import java.security.Principal;
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

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> {UserDTO userDTO = mappingService.userToUserDTO(user);
        return userDTO;
        }).collect(Collectors.toList());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserPasswordDTO findUserPasswordDTOByUsername(String username) {
        UserPasswordDTO userPasswordDTO = mappingService.userToUserPasswordDto(userRepository.findByUsername(username));
        return userPasswordDTO;
    }

    public User save(UserRegistrationDTO userRegistrationDTO) {
        User temp = mappingService.userRegistrationDtoToUser(userRegistrationDTO);
        temp.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        temp.setRole(roleRepository.findByName("ROLE_USER"));
        return userRepository.save(temp);
    }

    public void resetPassword(UserDTO userDTO,String password) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void update(UserDTO userDTO, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
    }

    public void changePassword(UserPasswordDTO userPasswordDTO, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        user.setPassword(passwordEncoder.encode(userPasswordDTO.getPassword()));
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
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
