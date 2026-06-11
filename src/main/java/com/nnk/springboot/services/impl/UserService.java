package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dtos.request.UserRequestDto;
import com.nnk.springboot.dtos.view.UserViewDto;
import com.nnk.springboot.exceptions.UserNotFoundException;
import com.nnk.springboot.mappers.UserMapper;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for User business operations.
 */
@Slf4j
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieves all User records and maps them to display DTOs.
     *
     * @return all User records formatted for display
     */
    @Override
    public List<UserViewDto> getAllUsers() {
        log.info("getting all Users");

        List<User> results = userRepository.findAll();
        log.info("Found {} Users", results.size());

        return results.stream().
                map(userMapper::toDto).
                toList();
    }

    /**
     * Retrieves one User by id.
     *
     * @param id the technical identifier of the User
     * @return the matching User formatted for display
     * @throws UserNotFoundException when no User exists for the given id
     */
    @Override
    public UserViewDto getUserById(Integer id) {
        log.info("getting User with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with ID {} not found", id);
                    return new UserNotFoundException(id); });

        return userMapper.toDto(user);
    }

    /**
     * Creates and persists a new User after encoding its password.
     *
     * @param userRequestDto the submitted form data
     * @return the created User formatted for display
     */
    @Override
    @Transactional
    public UserViewDto addUser(UserRequestDto userRequestDto) {
        log.info("adding User");

        User user = userMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        User savedUser = userRepository.save(user);

        log.info("User with ID {} has been added", savedUser.getId());

        return userMapper.toDto(savedUser);
    }

    /**
     * Updates an existing User and encodes the submitted password.
     *
     * @param id the technical identifier of the User to update
     * @param dto the submitted form data containing updated values
     * @return the updated User formatted for display
     * @throws UserNotFoundException when no User exists for the given id
     */
    @Override
    @Transactional
    public UserViewDto updateUser(Integer id, UserRequestDto dto) {
        log.info("updating User");

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with ID {} not found", id);
                    return new UserNotFoundException(id);
                });

        user.setUsername(dto.getUsername());
        user.setFullname(dto.getFullname());
        user.setRole(dto.getRole());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(user);

        log.info("User with ID {} has been updated", savedUser.getId());

        return userMapper.toDto(savedUser);
    }

    /**
     * Deletes an existing User.
     *
     * @param id the technical identifier of the User to delete
     * @throws UserNotFoundException when no User exists for the given id
     */
    @Override
    @Transactional
    public void deleteUser(Integer id) {
        log.info("deleting User with ID {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with ID {} not found", id);
                    return new UserNotFoundException(id);
                });

        userRepository.delete(user);

        log.info("User with ID {} has been deleted", id);
    }
}
