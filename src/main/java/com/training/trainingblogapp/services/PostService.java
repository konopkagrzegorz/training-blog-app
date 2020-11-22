package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.MessageDTO;
import com.training.trainingblogapp.domain.dtos.PostDTO;
import com.training.trainingblogapp.domain.model.Message;
import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private MappingService mappingService;

    @Autowired
    public PostService(PostRepository postRepository, MappingService mappingService) {
        this.postRepository = postRepository;
        this.mappingService = mappingService;
    }

    public List<PostDTO> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> {
            PostDTO postDTO = mappingService.postToPostDto(post);
            return postDTO;
        }).collect(Collectors.toList());
    }

    public Page<PostDTO> listAllByPage(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo - 1, pageSize);
        Page<Post> entities = postRepository.findAll(pageable);
        Page<PostDTO> dtoPage = entities.map(new Function<Post, PostDTO>() {
            @Override
            public PostDTO apply(Post entity) {
                return mappingService.postToPostDto(entity);
            }
        });
        return dtoPage;
    }
}
