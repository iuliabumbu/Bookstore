package ro.sd.a2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.ShipperDto;
import ro.sd.a2.entity.Genre;
import ro.sd.a2.entity.Shipper;
import ro.sd.a2.exceptions.InvalidParameterException;
import ro.sd.a2.mappers.Mapper;
import ro.sd.a2.messages.ErrorMessages;
import ro.sd.a2.service.repository.GenreRepository;
import ro.sd.a2.service.repository.ShipperRepository;
import ro.sd.a2.validators.GenreValidators;
import ro.sd.a2.validators.ShipperValidators;

import java.util.List;
import java.util.Optional;

@Service
public class ShipperService {

    private static final Logger log = LoggerFactory.getLogger(ShipperService.class);

    @Autowired
    private ShipperRepository shipperRepository;


    public void saveShipper(ShipperDto shipperDto){
        Shipper shipper = Mapper.ShipperDtoMapping(shipperDto);
        ShipperValidators.validateShipperId(shipper.getId());
        ShipperValidators.validateShipper(shipper.getName(), shipper.getCost());
        shipperRepository.save(shipper);

        log.info("Successfully created shipper " + shipper.toString());
    }

    public void updateShipper(ShipperDto shipperDto){
        Optional<Shipper> shipper = findShipperById(shipperDto.getId());

        if( shipper.isPresent()){

            shipper.get().setName(shipperDto.getName());
            shipper.get().setCost(shipperDto.getCost());
            ShipperValidators.validateShipper(shipper.get().getName(), shipper.get().getCost());
            shipperRepository.save(shipper.get());

            log.info("Successfully updated shipper " + shipper.toString());

        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }
    }

    public void deleteShipper(ShipperDto shipperDto){
        Optional<Shipper> shipper = findShipperById(shipperDto.getId());

        if( shipper.isPresent()){

            shipper.get().setDeleted("yes");

            shipperRepository.save(shipper.get());

            log.info("Successfully deleted shipper " + shipper.toString());

        }
        else{
            throw new InvalidParameterException(ErrorMessages.INVALID_FIND);
        }
    }

    public Optional<Shipper> findShipperById(String id){
        ShipperValidators.validateShipperId(id);
        Optional<Shipper> shipper = shipperRepository.findById(id);

        return shipper;
    }

    public List<Shipper> findAllShippers(){
        List<Shipper> shippers = shipperRepository.findAll();

        return shippers;
    }

}
