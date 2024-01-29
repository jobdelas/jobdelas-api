package com.jobdelas.jobdelas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobdelas.jobdelas.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

}
