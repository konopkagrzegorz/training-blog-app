package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.PostDTO;
import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.domain.model.User;
import com.training.trainingblogapp.repositories.PostRepository;
import com.training.trainingblogapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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
        Page<PostDTO> dtoPage = entities.map(new Function<Post, PostDTO>() {
            @Override
            public PostDTO apply(Post entity) {
                return mappingService.postToPostDto(entity);
            }
        });
        return dtoPage;
    }

    public PostDTO findPostById(long id) {
        PostDTO postDTO = mappingService.postToPostDto(postRepository.findById(id).get());
        return postDTO;
    }

    public void addPost(PostDTO postDTO, Principal principal) {

        User user = userRepository.findByUsername(principal.getName()).get();
        Post post = null;
        try {
            post = mappingService.postDtoToPost(postDTO);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        post.setDate(LocalDateTime.now());
        post.setUser(user);
        postRepository.save(post);
    }
}
