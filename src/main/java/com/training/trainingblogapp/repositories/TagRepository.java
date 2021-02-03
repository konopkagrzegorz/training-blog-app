package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    List<Tag> findAll();
    Optional<Tag> findByNameIgnoreCase(String name);
    List<Tag> findByPosts_Id(Long id);

}
