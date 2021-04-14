package ro.sd.a2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.Genre;
import ro.sd.a2.entity.Shipper;
import ro.sd.a2.service.repository.GenreRepository;
import ro.sd.a2.service.repository.ShipperRepository;
import ro.sd.a2.validators.GenreValidators;
import ro.sd.a2.validators.ShipperValidators;

import java.util.List;

@Service
public class ShipperService {

    private static final Logger log = LoggerFactory.getLogger(ShipperService.class);

    @Autowired
    private final ShipperRepository shipperRepository;

    public ShipperService(ShipperRepository shipperRepository){

        this.shipperRepository = shipperRepository;
    }

    public void saveShipper(Shipper shipper){
        ShipperValidators.validateShipper(shipper.getName(), shipper.getCost());
        shipperRepository.save(shipper);

        log.info("Successfully created genre " + shipper.toString());
    }

    public List<Shipper> findAllShippers(){
        List<Shipper> shippers = shipperRepository.findAll();

        return shippers;
    }

}
