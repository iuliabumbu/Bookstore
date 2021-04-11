package ro.sd.a2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shipper")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipper {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private float cost;

    @OneToMany
    private List<Order> orders = new ArrayList<Order>();

    @Column
    private String deleted;

}
