package ro.sd.a2.factory;

import ro.sd.a2.entity.Address;
import ro.sd.a2.entity.Genre;

import java.util.UUID;

public class AddressFactory {

    public static Address generateAddress(){
        Address address = new Address();
        address.setDeleted("no");
        address.setId(UUID.randomUUID().toString());
        return address;
    }
}
