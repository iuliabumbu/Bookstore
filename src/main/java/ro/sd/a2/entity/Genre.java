package ro.sd.a2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genre")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Genre {

    @Id
    private String id;

    @Column
    private String type;

    @OneToMany
    private List<Book> books= new ArrayList<Book>();

    @Column
    private String deleted;
}
