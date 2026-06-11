package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dtos.request.UserRequestDto;
import com.nnk.springboot.dtos.view.UserViewDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps User entities to DTOs and DTOs to entities.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * Converts submitted form data to a User entity.
     *
     * @param userRequestDto the form data to convert
     * @return the corresponding User entity
     */
    User toEntity (UserRequestDto userRequestDto);

    /**
     * Converts a User entity to display data.
     *
     * @param user the entity to convert
     * @return the corresponding display DTO
     */
    UserViewDto toDto (User user);
}
