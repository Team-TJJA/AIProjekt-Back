package com.example.aiproject.repository;

import com.example.aiproject.entity.ChatApplicationResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatApplicationResponse,Integer> {
}
