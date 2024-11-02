package com.example.office.repository;

import com.example.office.entity.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long> {

    @Query("SELECT new Documents(d.id, d.name, d.size) FROM Documents d ORDER BY d.uploadTime DESC")
    List<Documents> findAll();
}
