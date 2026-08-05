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
 * <b>[API Conversion Service]</b> <br>
 * Interface chứa các phương thức chuyển đổi dữ liệu cho module {@link User}.
 * 
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Mapper
@Javadoc(
        value = "Giao diện cung cấp các phương thức mapping cho mô-đun {@link User}.",
        authors = {"<a href=\"https://github.com/AdorableDandelion25\">Himekawa</a>", "<a href=\"https://github.com/mapstruct\">MapStruct</a>"})
public interface UserMapper {
    /**
     * {@link User} -> {@link UserDto}
     */
    @Mapping(source = "createdTimestamp", target = "createdAt")
    @Mapping(target = "createdBy", expression = "java(user.getCreatedAuditor() != null ? user.getCreatedAuditor().toString() : null)")
    UserDto toUserDto(User user);

    /**
     * {@link UserCreateDto} -> {@link User}
     */
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "accountPassword", ignore = true)
    User toUser(UserCreateDto dto);

    /**
     * {@link RegisterRequest} -> {@link User}
     */
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
