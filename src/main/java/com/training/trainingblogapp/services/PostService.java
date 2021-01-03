package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.PostDTO;
import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.exceptions.UserNotAuthorizedException;
import com.training.trainingblogapp.repositories.PostRepository;
import com.training.trainingblogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private MappingService mappingService;
    private UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, MappingService mappingService, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.mappingService = mappingService;
        this.userRepository = userRepository;
    }

    public List<PostDTO> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> {
            PostDTO postDTO = mappingService.postToPostDto(post);
            return postDTO;
        }).collect(Collectors.toList());
    }

    public Page<PostDTO> listAllByPage(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo - 1, pageSize, Sort.by("date").descending());
        Page<Post> entities = postRepository.findAll(pageable);
        Page<PostDTO> dtoPage = entities.map(entity -> mappingService.postToPostDto(entity));
        return dtoPage;
    }

    public List<PostDTO> findAllPostContainsPhase(String phase) {
        List<Post> posts = postRepository.findAllPostContainsPhase(phase);
        return posts.stream().map(post -> {
            PostDTO postDTO = mappingService.postToPostDto(post);
            return postDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(long id, Principal principal) {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        if (user.isPresent() && user.get().getRole().getName().equals("ROLE_ADMIN")) {
            postRepository.deleteById(id);
        } else {
            throw new UserNotAuthorizedException("You are not logged in or You are not an admin!");
        }
    }

    public Optional<PostDTO> findById(Long id) {
        Optional<Post> post = Optional.ofNullable(postRepository.findById(id)).get();
        Optional<PostDTO> postDTO = Optional.empty();
        if (post.isPresent()) {
            postDTO = Optional.ofNullable(mappingService.postToPostDto(post.get()));
        }
        return postDTO;
    }

    @Transactional
    public void deleteByUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        postRepository.deleteByUser(user.get());
    }

    public PostDTO findPostById(long id) {
        PostDTO postDTO = mappingService.postToPostDto(postRepository.findById(id).get());
        return postDTO;
    }

    public void update(PostDTO postDTO, Principal principal) {
        Post temp = postRepository.findById(postDTO.getId()).get();
        User user = userRepository.findByUsername(temp.getUser().getUsername()).get();
        Post post = null;
        try {
            post = mappingService.updatePostDtoToPost(postDTO);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        post.setDate(temp.getDate());
        post.setUser(user);
        postRepository.save(post);
    }

    public void addPost(PostDTO postDTO, Principal principal) {

        User user = userRepository.findByUsername(principal.getName()).get();
        postDTO.setDate(LocalDateTime.now());
        Post post = null;
        try {
            post = mappingService.newPostDtoToPost(postDTO);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        post.setUser(user);
        postRepository.save(post);
    }
}
