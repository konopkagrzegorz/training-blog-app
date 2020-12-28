package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.domain.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post,Long> {

    List<Post> findAll();

//    @Query("SELECT post FROM Post WHERE post.heading LIKE %:phase%")
//    Page<Post> findAllPostContainsPhase(@Param("phase") String phase, Pageable pageable);
//    @Query("SELECT p FROM Post p WHERE p.heading LIKE '%phase%'")
//    Page<Post> findDistinctByHeadingContaining(String phase,Pageable pageable);


    @Query(value = "SELECT * FROM Post p WHERE p.heading LIKE %:keyword% OR p.text %:keyword%", nativeQuery = true)
    List<Post> findAllPostContainsPhase(@Param("keyword") String keyword);
    void deleteByUser(User user);
}
