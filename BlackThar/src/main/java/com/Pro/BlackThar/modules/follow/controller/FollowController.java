package com.Pro.BlackThar.modules.follow.controller;

import com.Pro.BlackThar.modules.follow.dto.FollowDTO;
import com.Pro.BlackThar.modules.follow.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping
    public ResponseEntity<FollowDTO> followUser(@RequestBody FollowDTO followDTO) {
        FollowDTO createdFollow = followService.followUser(followDTO);
        return ResponseEntity.ok(createdFollow);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long id) {
        followService.unfollowUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<FollowDTO>> getFollowersByUserId(@PathVariable Long userId) {
        List<FollowDTO> followers = followService.getFollowersByUserId(userId);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<FollowDTO>> getFollowingByUserId(@PathVariable Long userId) {
        List<FollowDTO> following = followService.getFollowingByUserId(userId);
        return ResponseEntity.ok(following);
    }
}