package dev.sh1on.amlethmp.song.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongUpdateDto {
    private String title;
    private String coverUrl;
    private Integer trackNumber;
    private Boolean explicit;
}
