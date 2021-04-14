package ro.sd.a2.factory;

import ro.sd.a2.entity.Shipper;

import java.util.UUID;

public class ShipperFactory {

    public static Shipper generateShipper(){
        Shipper shipper = new Shipper();
        shipper.setDeleted("no");
        shipper.setId(UUID.randomUUID().toString());
        return shipper;
    }
}
