package com.Pro.BlackThar.modules.reaction.repository;

import com.Pro.BlackThar.modules.reaction.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    List<Reaction> findByPostId(Long postId); 
    boolean existsByUserIdAndPostId(Long userId, Long postId); 
}