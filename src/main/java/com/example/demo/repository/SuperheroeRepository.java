package com.example.demo.repository;

import com.example.demo.model.Superheroe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SuperheroeRepository extends JpaRepository<Superheroe, Long>{
    List<Superheroe> findByNameContaining(String searchString);
}
