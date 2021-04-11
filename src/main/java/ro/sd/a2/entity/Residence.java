package ro.sd.a2.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "residence")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Residence {

    @Id
    private String id;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column
    private String deleted;

}
