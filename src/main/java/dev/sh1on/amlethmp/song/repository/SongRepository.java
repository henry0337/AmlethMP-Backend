package dev.sh1on.amlethmp.song.repository;

import org.springframework.stereotype.Repository;

import dev.myrlennia237.template.repository.ExtendedR2dbcRepository;
import dev.sh1on.amlethmp.song.model.Song;

/**
 * <b>[Domain Repository]</b> <br>
 * Repository cho module {@code Song}.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Repository
public interface SongRepository extends ExtendedR2dbcRepository<Song> { }
