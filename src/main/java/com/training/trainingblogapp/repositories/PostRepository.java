package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.domain.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post,Long> {

    List<Post> findAll();
    @Query(value = "SELECT DISTINCT * FROM posts p WHERE p.heading LIKE %:keyword% OR p.text LIKE %:keyword%",
            nativeQuery = true)
    List<Post> findAllPostContainsPhase(@Param("keyword") String keyword);
    List<Post> findByTags_Id(Long id);
    void deleteByUser(User user);
}
