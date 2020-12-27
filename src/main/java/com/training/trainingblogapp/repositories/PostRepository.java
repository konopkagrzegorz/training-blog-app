package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post,Long> {

    List<Post> findAll();

//    @Query("SELECT post FROM Post WHERE post.heading LIKE %:phase%")
//    Page<Post> findAllPostContainsPhase(@Param("phase") String phase, Pageable pageable);

//    @Query("SELECT p FROM Post WHERE p.heading LIKE %phase%")
//    List<Post> findAllPostContainsPhase(@Param("phase") String phase);

    void deleteByUser(User user);
    Page<Post> findDistinctByHeadingContaining(String phase,Pageable pageable);
}
