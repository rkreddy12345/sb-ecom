package com.rkecom.crud.user.service.impl;

import com.rkecom.core.exception.ApiException;
import com.rkecom.crud.user.service.AddressService;
import com.rkecom.data.user.repository.AddressRepository;
import com.rkecom.data.user.repository.UserRepository;
import com.rkecom.db.entity.user.Address;
import com.rkecom.db.entity.user.User;
import com.rkecom.objects.user.mapper.AddressMapper;
import com.rkecom.web.user.model.AddressModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

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
                .orElseThrow(() -> new ApiException ("Address was not persisted"));

        return addressMapper.toModel().apply(savedAddress);
    }

    @Override
    public List < AddressModel > getUserAddresses ( Long userId ) {
        List<Address> userAddresses = addressRepository.findAddressByUserId ( userId );
        Function<Address, AddressModel> modelMapper=addressMapper.toModel ();
        return userAddresses.stream ().map ( modelMapper ).toList ();
    }

}
