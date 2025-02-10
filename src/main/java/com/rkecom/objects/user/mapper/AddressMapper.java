package com.rkecom.objects.user.mapper;

import com.rkecom.db.entity.user.Address;
import com.rkecom.web.user.model.AddressModel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddressMapper {
    public Function< AddressModel, Address > toEntity() {
        return addressModel -> Address.builder ()
                .street ( addressModel.getStreet () )
                .building ( addressModel.getBuilding () )
                .city ( addressModel.getCity () )
                .state ( addressModel.getState () )
                .country ( addressModel.getCountry () )
                .pincode ( addressModel.getPincode () )
                .build ();
    }

    public Function<Address, AddressModel> toModel() {
        return address -> AddressModel.builder ()
                .addressId ( address.getAddressId () )
                .street ( address.getStreet () )
                .building ( address.getBuilding () )
                .city ( address.getCity () )
                .state ( address.getState () )
                .country ( address.getCountry () )
                .pincode ( address.getPincode () )
                .build ();
    }
}
