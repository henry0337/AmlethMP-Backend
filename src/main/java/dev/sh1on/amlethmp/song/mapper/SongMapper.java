package dev.sh1on.amlethmp.song.mapper;

import dev.sh1on.amlethmp.song.dto.SongCreateDto;
import dev.sh1on.amlethmp.song.dto.SongDto;
import dev.sh1on.amlethmp.song.model.Song;
import org.mapstruct.Javadoc;
import org.mapstruct.Mapper;

/**
 * Giao diện cung cấp các phương thức mapping cho mô-đun {@link Song}.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@Mapper
@Javadoc(
        value = "Giao diện cung cấp các phương thức mapping cho mô-đun {@link Song}.",
        authors = {"<a href=\"https://github.com/henry0337\">Muharux</a>", "<a href=\"https://github.com/mapstruct\">MapStruct</a>"})
public interface SongMapper {
    SongDto toSongDto(Song song);
    Song toSong(SongCreateDto dto);
}
