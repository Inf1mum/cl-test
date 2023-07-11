package org.example.repository;

import org.example.entity.Human;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HumanRepository extends CrudRepository<Human, Long> {
    List<Human> findAll();
}
