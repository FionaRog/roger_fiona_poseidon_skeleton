package com.nnk.springboot.services;

import com.nnk.springboot.dtos.request.UserRequestDto;
import com.nnk.springboot.dtos.view.UserViewDto;

import java.util.List;

/**
 * Defines business operations for managing User records.
 */
public interface IUserService {

    /**
     * Retrieves all User records.
     *
     * @return User records formatted for display
     */
    List<UserViewDto> getAllUsers();

    /**
     * Retrieves a User by its technical identifier.
     *
     * @param id the technical identifier of the User
     * @return the matching User formatted for display
     */
    UserViewDto getUserById(Integer id);

    /**
     * Creates a User from submitted form data.
     *
     * @param dto the form data used to create the User
     * @return the created User formatted for display
     */
    UserViewDto addUser(UserRequestDto dto);

    /**
     * Updates an existing User.
     *
     * @param id  the technical identifier of the User to update
     * @param dto the form data containing updated values
     * @return the updated User formatted for display
     */
    UserViewDto updateUser(Integer id, UserRequestDto dto);

    /**
     * Deletes an existing User.
     *
     * @param id the technical identifier of the User to delete
     */
    void deleteUser(Integer id);
}
