package dev.sh1on.amlethmp.song.repository;

import dev.myrlennia237.template.repository.ModifiedR2dbcRepository;
import dev.sh1on.amlethmp.song.model.Song;
import org.springframework.stereotype.Repository;

/**
 * <b>[Domain Repository]</b> <br>
 * Repository cho mô-đun {@code Song}.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@Repository
public interface SongRepository extends ModifiedR2dbcRepository<Song> { }
