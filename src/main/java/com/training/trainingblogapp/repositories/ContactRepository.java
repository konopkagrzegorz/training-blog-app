package com.training.trainingblogapp.repositories;

import com.training.trainingblogapp.domain.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Message, Long> {

        List<Message> findAll();

}
