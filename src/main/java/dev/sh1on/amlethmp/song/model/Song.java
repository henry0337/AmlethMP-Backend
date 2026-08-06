package dev.sh1on.amlethmp.song.model;

import java.util.UUID;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import dev.myrlennia237.template.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <b>[API Entity]</b> <br>
 * Entity chứa thông tin liên quan tới các <b>bài hát</b>.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Table("songs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@SuppressWarnings("java:S2057")
public class Song extends Entity {
    /**
     * Tên bài hát.
     */
    private String title;

    /**
     * Thời lượng bài hát, tính theo mili giây.
     */
    @Column("duration_ms")
    private int durationMs;

    /**
     * URL tham chiếu tới file âm thanh của bài hát này.
     */
    @Column("audio_url")
    private String audioUrl;

    /**
     * URL tham chiếu tới ảnh cover cho bài hát này.
     */
    @Column("cover_url")
    private String coverUrl;

    /**
     * ID tham chiếu tới album, nếu có.
     */
    @Column("album_id")
    private UUID albumId;

    /**
     * Số thứ tự của bài hát này trong album.
     */
    @Column("track_number")
    private Integer trackNumber;

    /**
     * Đánh dấu bài hát này không phù hợp cho mọi đối tượng nghe.
     */
    private boolean explicit;

    /**
     * Số lượt nghe bài hát này (Tổng).
     */
    @Column("play_count")
    private long playCount;
}
