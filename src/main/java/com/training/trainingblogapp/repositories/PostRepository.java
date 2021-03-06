package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Post;
import com.training.trainingblogapp.domain.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post,Long> {

    List<Post> findAll();
    @Query(value = "SELECT DISTINCT * FROM posts p WHERE LOWER(p.heading) LIKE LOWER(concat('%', :keyword,'%')) OR LOWER(p.text) LIKE LOWER(concat('%', :keyword,'%'))",
            nativeQuery = true)
    List<Post> findAllPostContainsPhase(@Param("keyword") String keyword);
    List<Post> findByTags_Id(Long id);
    void deleteByUser(User user);
}
