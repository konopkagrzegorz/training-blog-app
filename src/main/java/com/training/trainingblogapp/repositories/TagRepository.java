package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {

    List<Tag> findAll();

}
