package com.Pro.BlackThar.modules.post.service;

import com.Pro.BlackThar.modules.post.dto.PostDTO;
import com.Pro.BlackThar.modules.post.entity.Post;
import com.Pro.BlackThar.modules.post.exception.PostNotFoundException;
import com.Pro.BlackThar.modules.post.repository.PostRepository;
import com.Pro.BlackThar.modules.user.entity.User;
import com.Pro.BlackThar.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostDTO createPost(PostDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setImageUrl(postDTO.getImageUrl());
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return convertToDTO(savedPost);
    }

    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Publicación no encontrada"));
        return convertToDTO(post);
    }

    public List<PostDTO> getPostsByUserId(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Publicación no encontrada"));

        post.setContent(postDTO.getContent());
        post.setImageUrl(postDTO.getImageUrl());

        Post updatedPost = postRepository.save(post);
        return convertToDTO(updatedPost);
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException("Publicación no encontrada");
        }
        postRepository.deleteById(id);
    }

    private PostDTO convertToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setContent(post.getContent());
        postDTO.setImageUrl(post.getImageUrl());
        postDTO.setUserId(post.getUser().getId());
        return postDTO;
    }
}