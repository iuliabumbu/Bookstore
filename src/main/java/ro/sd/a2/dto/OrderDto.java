package ro.sd.a2.dto;

import lombok.*;
import ro.sd.a2.entity.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {

    private String id;

    private String status;

    private Shipper shipper;

    private Address address;

    private User owner;

    private float totalCost;

    private List<Book> items = new ArrayList<Book>();
}
