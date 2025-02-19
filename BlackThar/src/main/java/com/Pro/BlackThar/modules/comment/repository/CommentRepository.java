package com.Pro.BlackThar.modules.comment.repository;

import com.Pro.BlackThar.modules.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId); // Obtener comentarios por ID de publicación
}