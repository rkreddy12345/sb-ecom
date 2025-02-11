package com.rkecom.data.user.repository;

import com.rkecom.core.data.repository.BaseRepository;
import com.rkecom.db.entity.user.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends BaseRepository< Address, Long > {
    @Query("select a from Address a where a.user.userId=:userId")
    List <Address> findAddressByUserId( @Param ( "userId" ) Long userId);

    Optional<Address> getAddressesByAddressId ( Long addressId );
}
