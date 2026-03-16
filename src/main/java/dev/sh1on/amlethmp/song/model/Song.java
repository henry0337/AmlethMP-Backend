package dev.sh1on.amlethmp.song.model;

import dev.sh1on.amlethmp.common.template.model.SoftDeletableEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Getter
@Setter
@Table("songs")
public class Song extends SoftDeletableEntity {
    private String name;
    private String description;
    private String category;
    private String albumId;
    private String artistId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Song song = (Song) o;
        return Objects.equals(name, song.name) && Objects.equals(description, song.description) && Objects.equals(category, song.category) && Objects.equals(albumId, song.albumId) && Objects.equals(artistId, song.artistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, category, albumId, artistId);
    }
}
