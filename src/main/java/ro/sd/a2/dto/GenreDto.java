package ro.sd.a2.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GenreDto {
    private String id;

    private String type;
}
