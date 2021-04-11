package ro.sd.a2.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "orderItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderItem {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "book_id")
    private Book item;


    @Column
    private String deleted;
}
