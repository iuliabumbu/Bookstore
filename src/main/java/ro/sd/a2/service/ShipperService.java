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

    /**
     * Metoda responsabila cu inserarea in baza de date a unui nou curier
     * @param shipperDto - curierul pe care dorim sa il introducem
     */
    public void saveShipper(ShipperDto shipperDto){
        log.info("Insert shipper attempt");

        Shipper shipper = Mapper.ShipperDtoMapping(shipperDto);
        ShipperValidators.validateShipperId(shipper.getId());
        ShipperValidators.validateShipper(shipper.getName(), shipper.getCost());
        shipperRepository.save(shipper);

        log.info("Successfully created shipper " + shipper.toString());
    }

    /**
     * Metoda responsabila cu actualizarea datelor unui curier
     * @param shipperDto - curierul pe care dorim sa il actualizam
     * @throws InvalidParameterException - daca curierul pe care vrem sa il actualizam nu exista in baza de date
     */
    public void updateShipper(ShipperDto shipperDto){
        log.info("Update shipper attempt");

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

    /**
     * Metoda responsabila cu stergerea unui curier (stergere marcata prin setarea "yes" a campului deleted, nu stergere fizica)
     * @param shipperDto - curierul pe care dorim sa il stergem
     * @throws InvalidParameterException - daca curierul pe care vrem sa il stergem nu exista in baza de date
     */
    public void deleteShipper(ShipperDto shipperDto){
        log.info("Delete shipper attempt");

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
    /**
     * Metoda responsabila cu gasirea unui curier din baza de date
     * @param id - id-ul curierului pe care dorim sa il gasim
     * @return - returneaza curierul gasit
     */
    public Optional<Shipper> findShipperById(String id){
        log.info("Find shipper by id attempt " + id);

        ShipperValidators.validateShipperId(id);
        Optional<Shipper> shipper = shipperRepository.findById(id);

        return shipper;
    }

    /**
     * Metoda responsabila cu gasirea tuturor curieriilor din baza de date
     * @return - returneaza o lista ce contine toti curierii gasiti
     */
    public List<Shipper> findAllShippers(){
        log.info("Find all shippers attempt");

        List<Shipper> shippers = shipperRepository.findAll();

        return shippers;
    }

}
