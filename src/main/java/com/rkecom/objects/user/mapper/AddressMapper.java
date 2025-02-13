package com.rkecom.objects.user.mapper;

import com.rkecom.core.util.MapperUtil;
import com.rkecom.db.entity.user.Address;
import com.rkecom.web.user.model.AddressModel;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;
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

    public BiFunction<Address, AddressModel, Address> toUpdatedEntity(){
        return (address, addressModel)->{
            MapperUtil.updateField ( addressModel.getStreet (), address::setStreet );
            MapperUtil.updateField ( addressModel.getBuilding (), address::setBuilding );
            MapperUtil.updateField ( addressModel.getCity (), address::setCity );
            MapperUtil.updateField ( addressModel.getState (), address::setState );
            MapperUtil.updateField ( addressModel.getPincode (), address::setPincode);
            MapperUtil.updateField ( addressModel.getCountry (), address::setCountry );
            return address;
        };
    }
}
