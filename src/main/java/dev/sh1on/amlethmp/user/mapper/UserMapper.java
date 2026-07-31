package dev.sh1on.amlethmp.user.mapper;

import dev.sh1on.amlethmp.auth.dto.RegisterRequest;
import dev.sh1on.amlethmp.user.dto.UserCreateDto;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.dto.UserUpdateDto;
import dev.sh1on.amlethmp.user.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Javadoc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Mapper
@Javadoc(
        value = "Giao diện cung cấp các phương thức mapping cho mô-đun {@link User}.",
        authors = {"<a href=\"https://github.com/AdorableDandelion25\">Himekawa</a>", "<a href=\"https://github.com/mapstruct\">MapStruct</a>"})
public interface UserMapper {

    @Mapping(source = "createdDate", target = "createdAt")
    @Mapping(target = "createdBy", expression = "java(user.getCreatedBy() != null ? user.getCreatedBy().toString() : null)")
    UserDto toUserDto(User user);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "accountPassword", ignore = true)
    User toUser(UserCreateDto dto);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "accountPassword", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUser(RegisterRequest dto);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "accountPassword", ignore = true)
    void updateUser(UserUpdateDto dto, @MappingTarget User user);
}
