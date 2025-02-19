package com.Pro.BlackThar.modules.follow.service;

import com.Pro.BlackThar.modules.follow.dto.FollowDTO;
import com.Pro.BlackThar.modules.follow.entity.Follow;
import com.Pro.BlackThar.modules.follow.exception.FollowNotFoundException;
import com.Pro.BlackThar.modules.follow.repository.FollowRepository;
import com.Pro.BlackThar.modules.user.entity.User;
import com.Pro.BlackThar.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public FollowDTO followUser(FollowDTO followDTO) {
        User follower = userRepository.findById(followDTO.getFollowerId())
                .orElseThrow(() -> new RuntimeException("Usuario seguidor no encontrado"));
        User following = userRepository.findById(followDTO.getFollowingId())
                .orElseThrow(() -> new RuntimeException("Usuario seguido no encontrado"));

        // Verificar si el usuario ya sigue al otro usuario
        if (followRepository.existsByFollowerIdAndFollowingId(follower.getId(), following.getId())) {
            throw new RuntimeException("El usuario ya sigue a este usuario");
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        Follow savedFollow = followRepository.save(follow);
        return convertToDTO(savedFollow);
    }

    public void unfollowUser(Long id) {
        if (!followRepository.existsById(id)) {
            throw new FollowNotFoundException("Relación de seguimiento no encontrada");
        }
        followRepository.deleteById(id);
    }

    public List<FollowDTO> getFollowersByUserId(Long userId) {
        List<Follow> followers = followRepository.findByFollowingId(userId);
        return followers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<FollowDTO> getFollowingByUserId(Long userId) {
        List<Follow> following = followRepository.findByFollowerId(userId);
        return following.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FollowDTO convertToDTO(Follow follow) {
        FollowDTO followDTO = new FollowDTO();
        followDTO.setId(follow.getId());
        followDTO.setFollowerId(follow.getFollower().getId());
        followDTO.setFollowingId(follow.getFollowing().getId());
        return followDTO;
    }
}