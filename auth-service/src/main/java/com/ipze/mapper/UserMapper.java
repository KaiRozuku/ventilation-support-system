package com.ipze.mapper;

import com.ipze.dto.request.UserDto;
import com.ipze.model.postgres.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDto toDto(User user);
}