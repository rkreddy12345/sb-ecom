package com.rkecom.crud.user.service;

import com.rkecom.db.entity.user.User;
import com.rkecom.web.user.model.AddressModel;

import java.util.List;

public interface AddressService {
    AddressModel createAddress ( AddressModel address, User user );

    List< AddressModel> getUserAddresses ( Long userId );
    AddressModel getAddressById ( Long addressId );
}
