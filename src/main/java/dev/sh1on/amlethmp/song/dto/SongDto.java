package dev.sh1on.amlethmp.song.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SongDto {
    private UUID id;
    private String title;
    private int durationMs;
    private String audioUrl;
    private String coverUrl;
    private UUID albumId;
    private Integer trackNumber;
    private boolean explicit;
    private long playCount;
}
