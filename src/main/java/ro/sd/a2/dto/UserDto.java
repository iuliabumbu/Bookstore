package ro.sd.a2.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

    private String id;

    private String email;

    private String password;

    private String username;

    private String name;

    private String surname;

    private String phoneNumber;

}
