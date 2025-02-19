package com.Pro.BlackThar.modules.user.service;

import com.Pro.BlackThar.modules.user.dto.UserDTO;
import com.Pro.BlackThar.modules.user.entity.User;
import com.Pro.BlackThar.exception.ResourceNotFoundException;
import com.Pro.BlackThar.exception.ValidationException;
import com.Pro.BlackThar.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registrar un nuevo usuario
    public UserDTO registerUser(UserDTO userDTO) {
        // Validar que el usuario tenga al menos 14 años
        if (Period.between(userDTO.getBirthDate(), LocalDate.now()).getYears() < 14) {
            throw new ValidationException("El usuario debe tener al menos 14 años.");
        }

        // Validar que el nombre de usuario y el correo electrónico sean únicos
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new ValidationException("El nombre de usuario ya está en uso.");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ValidationException("El correo electrónico ya está en uso.");
        }

        // Crear una nueva entidad User
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setBirthDate(userDTO.getBirthDate());
        user.setBio(userDTO.getBio());
        user.setProfilePicture(userDTO.getProfilePicture());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Cifrar la contraseña

        // Guardar el usuario en la base de datos
        User savedUser = userRepository.save(user);

        // Convertir la entidad guardada a DTO
        return convertToDTO(savedUser);
    }

    // Iniciar sesión (devuelve el token JWT)
    public String loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("Usuario no encontrado.");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ValidationException("Contraseña incorrecta.");
        }

        // Aquí deberías generar y devolver un token JWT
        return "token_jwt_generado"; // Reemplaza con la lógica de generación de JWT
    }

    // Obtener perfil de usuario por ID
    public UserDTO getUserProfile(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("Usuario no encontrado.");
        }

        User user = userOptional.get();
        return convertToDTO(user);
    }

    // Actualizar perfil de usuario
    public UserDTO updateUserProfile(Long id, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("Usuario no encontrado.");
        }

        User user = userOptional.get();
        user.setFullName(userDTO.getFullName());
        user.setBio(userDTO.getBio());
        user.setProfilePicture(userDTO.getProfilePicture());
        user.setPhone(userDTO.getPhone());

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    // Eliminar usuario
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado.");
        }
        userRepository.deleteById(id);
    }

    // Convertir entidad User a UserDTO
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setBio(user.getBio());
        userDTO.setProfilePicture(user.getProfilePicture());
        return userDTO;
    }
}
