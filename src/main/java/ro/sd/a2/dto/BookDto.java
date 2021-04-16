package ro.sd.a2.dto;

import lombok.*;
import ro.sd.a2.entity.Genre;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookDto {

    private String id;

    private String title;

    private String author;

    private String description;

    private float price;

    private String image;

    private Genre genre;

}
