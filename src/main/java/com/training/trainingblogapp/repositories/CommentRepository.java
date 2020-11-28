package com.training.trainingblogapp.repositories;


import com.training.trainingblogapp.domain.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByOrderByDateDesc();
}
