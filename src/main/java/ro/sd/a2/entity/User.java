package ro.sd.a2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {

    @Id
    private String id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String phoneNumber;

    @OneToMany
    private List<Order> orders = new ArrayList<Order>();

    @OneToMany
    private List<Residence> residences = new ArrayList<Residence>();

    @Column
    private String deleted;


}
