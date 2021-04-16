package ro.sd.a2.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ShipperDto {

    private String id;

    private String name;

    private float cost;
}
