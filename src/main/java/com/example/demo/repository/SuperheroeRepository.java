package com.example.demo.repository;

import com.example.demo.model.Superheroe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuperheroeRepository extends JpaRepository<Superheroe, Long> {
    List<Superheroe> findByNameContaining(String searchString);
}
