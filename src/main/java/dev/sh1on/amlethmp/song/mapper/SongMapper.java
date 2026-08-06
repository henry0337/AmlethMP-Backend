package dev.sh1on.amlethmp.song.mapper;

import dev.sh1on.amlethmp.song.dto.SongCreateDto;
import dev.sh1on.amlethmp.song.dto.SongDto;
import dev.sh1on.amlethmp.song.dto.SongUpdateDto;
import dev.sh1on.amlethmp.song.model.Song;
import org.mapstruct.BeanMapping;
import org.mapstruct.Javadoc;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Mô hình cung cấp các phương thức mapping cho mô-đun {@link Song}.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Mapper
@Javadoc(
        value = "Mô hình cung cấp phiên bản triển khai cho các phương thức mapping thuộc module {@link Song}.",
        authors = {
                "<a href=\"https://github.com/henry0337\">Myrlennia</a>",
                "<a href=\"https://github.com/AdorableDandelion25\">Himekawa</a>",
                "<a href=\"https://github.com/mapstruct\">MapStruct</a>"}
)
public interface SongMapper {
    SongDto toSongDto(Song song);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    Song toSong(SongCreateDto dto);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateSong(SongUpdateDto dto, @MappingTarget Song song);
}
