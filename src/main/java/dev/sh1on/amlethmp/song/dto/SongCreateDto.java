package dev.sh1on.amlethmp.song.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongCreateDto {

    @NotBlank
    private String title;

    @NotNull
    @Positive
    private Integer durationMs;

    @NotBlank
    private String audioUrl;

    private String coverUrl;
    private UUID albumId;
    private Integer trackNumber;
    private boolean explicit;
}
