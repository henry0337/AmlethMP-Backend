package dev.sh1on.amlethmp.user.mapper;

import dev.sh1on.amlethmp.user.dto.UserCreateDto;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.dto.UserUpdateDto;
import dev.sh1on.amlethmp.user.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    @Mapping(target = "email", ignore = true)
    UserDto toUserDto(User user);

    UserDto toUserDto(UserCreateDto dto);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserDto toUserDto(UserUpdateDto userDto);

    @Mapping(target = "lastUpdatedBy", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "lastDisabledBy", ignore = true)
    @Mapping(target = "lastDisabledAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "credentialsExpired", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "accountPassword", ignore = true)
    @Mapping(target = "accountName", ignore = true)
    @Mapping(target = "accountLocked", ignore = true)
    @Mapping(target = "accountExpired", ignore = true)
    User toUser(UserDto userDto);
}
