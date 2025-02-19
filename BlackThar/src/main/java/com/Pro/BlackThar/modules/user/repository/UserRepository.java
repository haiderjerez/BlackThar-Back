package com.Pro.BlackThar.modules.user.repository;

import com.Pro.BlackThar.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Buscar usuario por nombre de usuario
    Optional<User> findByUsername(String username);

    // Buscar usuario por correo electrónico
    Optional<User> findByEmail(String email);

    // Verificar si un nombre de usuario ya existe
    boolean existsByUsername(String username);

    // Verificar si un correo electrónico ya existe
    boolean existsByEmail(String email);

    @Repository
    public interface UserRepositoryTwo extends JpaRepository<User, Long> {
        List<User> findByUsernameContainingOrFullNameContaining(String username, String fullName);
    
    }   
}
