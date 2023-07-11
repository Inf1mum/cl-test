package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Human;
import org.example.repository.HumanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HumanService {
    private final HumanRepository humanRepository;

    public List<Human> findAll() {
        return humanRepository.findAll();
    }

    public Optional<Human> findById(Long id) {
        return humanRepository.findById(id);
    }

    public Human save(Human human) {
        return humanRepository.save(human);
    }

    public void delete(Human human) {
        humanRepository.delete(human);
    }
}
