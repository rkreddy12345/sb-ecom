package com.rkecom.crud.user.service;

import com.rkecom.db.entity.user.User;
import com.rkecom.web.user.model.AddressModel;

public interface AddressService {
    AddressModel createAddress ( AddressModel address, User user );
}
