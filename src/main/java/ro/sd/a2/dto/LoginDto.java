package ro.sd.a2.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginDto {
    private String id;

    private String email;

    private String password;

}
