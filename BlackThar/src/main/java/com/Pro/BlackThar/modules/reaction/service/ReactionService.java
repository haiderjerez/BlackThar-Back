package com.Pro.BlackThar.modules.reaction.service;

import com.Pro.BlackThar.modules.reaction.dto.ReactionDTO;
import com.Pro.BlackThar.modules.reaction.entity.Reaction;
import com.Pro.BlackThar.modules.reaction.exception.ReactionNotFoundException;
import com.Pro.BlackThar.modules.reaction.repository.ReactionRepository;
import com.Pro.BlackThar.modules.post.entity.Post;
import com.Pro.BlackThar.modules.post.repository.PostRepository;
import com.Pro.BlackThar.modules.user.entity.User;
import com.Pro.BlackThar.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public ReactionService(ReactionRepository reactionRepository, UserRepository userRepository, PostRepository postRepository) {
        this.reactionRepository = reactionRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public ReactionDTO createReaction(ReactionDTO reactionDTO) {
        User user = userRepository.findById(reactionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Post post = postRepository.findById(reactionDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        // Verificar si el usuario ya reaccionó a la publicación
        if (reactionRepository.existsByUserIdAndPostId(user.getId(), post.getId())) {
            throw new RuntimeException("El usuario ya reaccionó a esta publicación");
        }

        Reaction reaction = new Reaction();
        reaction.setType(reactionDTO.getType());
        reaction.setUser(user);
        reaction.setPost(post);

        Reaction savedReaction = reactionRepository.save(reaction);
        return convertToDTO(savedReaction);
    }

    public void deleteReaction(Long id) {
        if (!reactionRepository.existsById(id)) {
            throw new ReactionNotFoundException("Reacción no encontrada");
        }
        reactionRepository.deleteById(id);
    }

    public List<ReactionDTO> getReactionsByPostId(Long postId) {
        List<Reaction> reactions = reactionRepository.findByPostId(postId);
        return reactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ReactionDTO convertToDTO(Reaction reaction) {
        ReactionDTO reactionDTO = new ReactionDTO();
        reactionDTO.setId(reaction.getId());
        reactionDTO.setType(reaction.getType());
        reactionDTO.setUserId(reaction.getUser().getId());
        reactionDTO.setPostId(reaction.getPost().getId());
        return reactionDTO;
    }
}