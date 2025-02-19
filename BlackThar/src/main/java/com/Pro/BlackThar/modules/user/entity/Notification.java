package com.Pro.BlackThar.modules.user.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.Pro.BlackThar.modules.post.entity.Post;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private Boolean isRead = false;
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getSender() {
        return sender;
    }
    public void setSender(User sender) {
        this.sender = sender;
    }
    public NotificationType getType() {
        return type;
    }
    public void setType(NotificationType type) {
        this.type = type;
    }
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    public Boolean getIsRead() {
        return isRead;
    }
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Notification() {
    }
    public Notification(Long id, User user, User sender, NotificationType type, Post post, Boolean isRead,
            LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.sender = sender;
        this.type = type;
        this.post = post;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }
    public enum NotificationType {
        FOLLOW, LIKE, COMMENT, MENTION
    }
}


