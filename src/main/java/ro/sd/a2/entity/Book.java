package ro.sd.a2.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Book {

    @Id
    private String id;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String description;

    @Column
    private float price;

    @Column
    private float promotionPrice;

    @Column
    private String image;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column
    private String deleted;
}
