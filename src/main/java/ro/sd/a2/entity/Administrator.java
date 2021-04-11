package ro.sd.a2.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Administrator {

    @Id
    private String id;

    @Column
    private String username;

    @Column
    private String password;

}
