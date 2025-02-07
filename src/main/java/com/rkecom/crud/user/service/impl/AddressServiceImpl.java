package com.rkecom.crud.user.service.impl;

import com.rkecom.crud.user.service.AddressService;
import com.rkecom.data.user.repository.UserRepository;
import com.rkecom.db.entity.user.Address;
import com.rkecom.db.entity.user.User;
import com.rkecom.objects.user.mapper.AddressMapper;
import com.rkecom.web.user.model.AddressModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AddressModel createAddress(AddressModel addressModel, User user) {
        Address address = addressMapper.toEntity().apply(addressModel);
        user.addAddress(address);
        userRepository.save(user);
        Address savedAddress = user.getAddresses().stream()
                .filter(a -> a.getStreet().equals(address.getStreet())
                        && a.getCity().equals(address.getCity())
                        && a.getPincode().equals(address.getPincode()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Address was not persisted"));

        return addressMapper.toModel().apply(savedAddress);
    }

}
