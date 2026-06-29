package dev.sh1on.amlethmp.song.model;

import dev.myrlennia237.template.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("songs")
public class Song extends Entity {
    private String name;
    private String description;
    private String category;
    private String albumId;
    private String artistId;
}
