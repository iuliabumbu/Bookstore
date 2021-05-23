package ro.sd.a2.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User implements UserDetails {

    @Id
    private String id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String username;

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

    /**
     * Return the roles here.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        /**
         * Add each role as a SimpleGrantedAuthority.
         * If you got only OneToOne relation, then return the list with only one element.
         */
        GrantedAuthority auth_example = new SimpleGrantedAuthority("user");
        authorities.add(auth_example);
        return authorities;
    }

    /**
     * All this fields are from UserDetails.
     */


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * This is used to retrieve the session user username.
     * Please do not remove or change it.
     * @return - the authenticated user username.
     */

    @Override
    public String toString() {
        return username;
    }


}
