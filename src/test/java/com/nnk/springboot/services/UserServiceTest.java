package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dtos.request.UserRequestDto;
import com.nnk.springboot.dtos.view.UserViewDto;
import com.nnk.springboot.exceptions.UserNotFoundException;
import com.nnk.springboot.mappers.UserMapper;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private IUserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, userMapper, passwordEncoder);
    }

    @Test
    @DisplayName("Should return all Users")
    public void getAllUsersTest() {
        User user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setFullname("User Fullname");
        user.setRole("USER");

        UserViewDto viewDto = new UserViewDto();
        viewDto.setId(1);
        viewDto.setUsername("user");
        viewDto.setFullname("User Fullname");
        viewDto.setRole("USER");

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(viewDto);

        List<UserViewDto> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("user", result.get(0).getUsername());
        verify(userRepository).findAll();
        verify(userMapper).toDto(user);
    }

    @Test
    @DisplayName("Should return User by id")
    public void getUserByIdTest() {
        User user = new User();
        user.setId(1);
        user.setUsername("user");

        UserViewDto viewDto = new UserViewDto();
        viewDto.setId(1);
        viewDto.setUsername("user");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(viewDto);

        UserViewDto result = userService.getUserById(1);

        assertEquals(1, result.getId());
        assertEquals("user", result.getUsername());
        verify(userRepository).findById(1);
        verify(userMapper).toDto(user);
    }

    @Test
    @DisplayName("Should throw exception when User is not found")
    public void getUserByIdNotFoundTest() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1));

        verify(userRepository).findById(1);
        verifyNoInteractions(userMapper);
    }

    @Test
    @DisplayName("Should add User")
    public void addUserTest() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setUsername("user");
        requestDto.setPassword("123456");
        requestDto.setFullname("User Fullname");
        requestDto.setRole("USER");

        User user = new User();
        user.setUsername("user");
        user.setPassword("123456");
        user.setFullname("User Fullname");
        user.setRole("USER");

        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setUsername("user");
        savedUser.setPassword("encoded-password");
        savedUser.setFullname("User Fullname");
        savedUser.setRole("USER");

        UserViewDto viewDto = new UserViewDto();
        viewDto.setId(1);
        viewDto.setUsername("user");
        viewDto.setFullname("User Fullname");
        viewDto.setRole("USER");

        when(userMapper.toEntity(requestDto)).thenReturn(user);
        when(passwordEncoder.encode("123456")).thenReturn("encoded-password");
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(viewDto);

        UserViewDto result = userService.addUser(requestDto);

        assertEquals(1, result.getId());
        assertEquals("user", result.getUsername());
        assertEquals("encoded-password", user.getPassword());
        assertNotEquals("123456", user.getPassword());
        verify(userMapper).toEntity(requestDto);
        verify(passwordEncoder).encode("123456");
        verify(userRepository).save(user);
        verify(userMapper).toDto(savedUser);
    }

    @Test
    @DisplayName("Should update User")
    public void updateUserTest() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setUsername("new-user");
        requestDto.setPassword("new-password");
        requestDto.setFullname("New User Fullname");
        requestDto.setRole("ADMIN");

        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("old-user");
        existingUser.setPassword("old-password");
        existingUser.setFullname("Old User Fullname");
        existingUser.setRole("USER");

        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setUsername("new-user");
        savedUser.setPassword("encoded-new-password");
        savedUser.setFullname("New User Fullname");
        savedUser.setRole("ADMIN");

        UserViewDto viewDto = new UserViewDto();
        viewDto.setId(1);
        viewDto.setUsername("new-user");
        viewDto.setFullname("New User Fullname");
        viewDto.setRole("ADMIN");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("new-password")).thenReturn("encoded-new-password");
        when(userRepository.save(existingUser)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(viewDto);

        UserViewDto result = userService.updateUser(1, requestDto);

        assertEquals(1, result.getId());
        assertEquals("new-user", result.getUsername());
        assertEquals("new-user", existingUser.getUsername());
        assertEquals("New User Fullname", existingUser.getFullname());
        assertEquals("ADMIN", existingUser.getRole());
        assertEquals("encoded-new-password", existingUser.getPassword());
        verify(userRepository).findById(1);
        verify(passwordEncoder).encode("new-password");
        verify(userRepository).save(existingUser);
        verify(userMapper).toDto(savedUser);
    }

    @Test
    @DisplayName("Should throw exception when updating an unknown User")
    public void updateUserNotFoundTest() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1, new UserRequestDto()));

        verify(userRepository).findById(1);
        verify(userRepository, never()).save(any(User.class));
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @DisplayName("Should delete User")
    public void deleteUserTest() {
        User user = new User();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.deleteUser(1);

        verify(userRepository).findById(1);
        verify(userRepository).delete(user);
    }

    @Test
    @DisplayName("Should throw exception when deleting an unknown User")
    public void deleteUserNotFoundTest() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1));

        verify(userRepository).findById(1);
        verify(userRepository, never()).delete(any(User.class));
    }
}
