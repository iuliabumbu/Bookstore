package ro.sd.a2.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddressDto {

    private String id;

    private String street;

    private String apartment;

    private String number;

    private String city;
}
