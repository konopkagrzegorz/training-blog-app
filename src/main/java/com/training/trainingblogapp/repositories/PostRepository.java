package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post,Long> {

    List<Post> findAll();

}
