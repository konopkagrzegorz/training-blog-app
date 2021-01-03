package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Comment;
import com.training.trainingblogapp.domain.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByOrderByDateDesc();
    List<Comment> findCommentByPost_IdOrderByDateDesc(Long id);
    void deleteByUser(User user);
}
