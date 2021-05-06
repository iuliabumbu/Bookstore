package ro.sd.a2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;
import ro.sd.a2.dto.AddressDto;
import ro.sd.a2.dto.BookDto;
import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.Address;
import ro.sd.a2.entity.Book;
import ro.sd.a2.entity.Residence;
import ro.sd.a2.entity.User;
import ro.sd.a2.mappers.Mapper;
import ro.sd.a2.service.repository.AddressRepository;
import ro.sd.a2.service.repository.BookRepository;
import ro.sd.a2.service.repository.ResidenceRepository;
import ro.sd.a2.validators.BookValidators;
import ro.sd.a2.validators.ResidenceValidators;
import ro.sd.a2.validators.UserValidators;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResidenceService {
    private static final Logger log = LoggerFactory.getLogger(ResidenceService.class);

    @Autowired
    private ResidenceRepository residenceRepository;

    @Autowired
    private AddressRepository addressRepository;

    public void saveResidence(AddressDto addressDto, User user){
        log.info("Insert address attempt");

        Address address;

        Address addr = addressRepository.findByApartmentAndStreetAndNumberAndCity(addressDto.getApartment(),
                addressDto.getStreet(),addressDto.getNumber(), addressDto.getCity());

        if(addr == null){
             address = Mapper.AddressDtoMapping(addressDto);
             addressRepository.save(address);
        }
        else{
            address = addr;
        }
        Residence residence;
        Residence res = residenceRepository.findByOwner(user);
        List<Address> addressList;
        if(res == null){
            addressList = Arrays.asList(address);

            System.out.println("Aici");
            System.out.println(addressList);

            residence = Residence.builder()
                    .id(UUID.randomUUID().toString())
                    .owner(user)
                    .deleted("no")
                    .addresses(addressList)
                    .build();
            ResidenceValidators.validateResidence(residence);
            residenceRepository.save(residence);

            log.info("Successfully added address " + residence.toString());
        }
        else{
            addressList = res.getAddresses();
            addressList.add(address);

            System.out.println("Aici2");
            System.out.println(addressList);

            res.setAddresses(addressList);
            ResidenceValidators.validateResidence(res);
            residenceRepository.save(res);

            log.info("Successfully added address " + res.toString());

        }

    }

    public Residence findAllByOwner(User user){
        UserValidators.validateInsertUser(user);
        return residenceRepository.findByOwner(user);

    }

    public void deleteAddress(AddressDto addressDto, User user) {
        Residence residence = residenceRepository.findByOwner(user);

        Optional<Address> address = addressRepository.findById(addressDto.getId());

        if(address.isPresent() && residence != null){
            List<Address> addressList = residence.getAddresses();
            addressList.remove(address.get());
            residence.setAddresses(addressList);

            ResidenceValidators.validateResidence(residence);

            residenceRepository.save(residence);

            log.info("Successfully deleted address " + address.toString());
        }

    }
}
