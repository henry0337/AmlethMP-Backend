package dev.sh1on.amlethmp.user.mapper;

import dev.sh1on.amlethmp.auth.dto.RegisterRequest;
import dev.sh1on.amlethmp.user.dto.UserCreateDto;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.dto.UserUpdateDto;
import dev.sh1on.amlethmp.user.model.User;
import org.mapstruct.Javadoc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Mapper
@Javadoc(
        value = "Giao diện cung cấp các phương thức mapping cho mô-đun {@link User}.",
        authors = {"<a href=\"https://github.com/AdorableDandelion25\">Himekawa</a>", "<a href=\"https://github.com/mapstruct\">MapStruct</a>"})
public interface UserMapper {
    UserDto toUserDto(User user);

    @Mapping(target = "accountPassword", ignore = true)
    User toUser(UserCreateDto dto);

    UserDto toUserDto(UserCreateDto dto);

    UserDto toUserDto(RegisterRequest request);

    UserDto toUserDto(UserUpdateDto userDto);

    User toUser(UserDto userDto);
}
