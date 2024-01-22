package com.jobdelas.jobdelas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobdelas.jobdelas.model.Postagens;

@Repository
public interface PostagensRepository extends JpaRepository<Postagens, Long> {

}
