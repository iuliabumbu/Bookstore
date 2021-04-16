package ro.sd.a2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @Column
    private Date data;

    @Column
    private String status;

    @Column
    private String deleted;

    @OneToMany
    private List<Book> items = new ArrayList<Book>();

    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private Shipper shipper;



}
