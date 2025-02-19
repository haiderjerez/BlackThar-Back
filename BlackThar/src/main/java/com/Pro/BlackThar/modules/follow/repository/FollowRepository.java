package com.Pro.BlackThar.modules.follow.repository;

import com.Pro.BlackThar.modules.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowerId(Long followerId); // Obtener usuarios seguidos por un usuario
    List<Follow> findByFollowingId(Long followingId); // Obtener seguidores de un usuario
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId); // Verificar si un usuario sigue a otro
}