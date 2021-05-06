package ro.sd.a2.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

    Address findByApartmentAndStreetAndNumberAndCity(String apartment, String street,String number, String city);
}
