package ro.sd.a2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Book implements Comparable<Book>{

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

    @Override
    public int compareTo(Book book) {

        return (int) (this.price - book.price);
    }
    public static List<Book> lowToHigh(List<Book> list) {
        Collections.sort(list);
        return list;
    }

    public static List<Book> highToLow(List<Book> list) {
        List<Book> list2 = lowToHigh(list);
        Collections.reverse(list2);

        return list2;
    }

}

