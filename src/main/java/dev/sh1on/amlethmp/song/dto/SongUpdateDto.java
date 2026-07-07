package dev.sh1on.amlethmp.song.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongUpdateDto {
    private String title;
    private String coverUrl;
    private Integer trackNumber;
    private Boolean explicit;
}
