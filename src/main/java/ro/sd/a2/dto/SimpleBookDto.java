package ro.sd.a2.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SimpleBookDto {
    private String title;

    private String author;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SimpleBookDto)) {
            return false;
        }
        SimpleBookDto other = (SimpleBookDto) o;
        return title.equals(other.title) && author.equals(other.author);
    }

}
