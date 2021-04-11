package ro.sd.a2.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Address {

    @Id
    private String id;

    @Column
    private String street;

    @Column
    private String apartment;

    @Column
    private String number;

    @Column
    private String city;

    @Column
    private String deleted;

}
