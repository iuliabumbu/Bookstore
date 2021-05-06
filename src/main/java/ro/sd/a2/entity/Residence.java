package ro.sd.a2.entity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER)
    private List<Address> addresses = new ArrayList<Address>();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column
    private String deleted;

}
