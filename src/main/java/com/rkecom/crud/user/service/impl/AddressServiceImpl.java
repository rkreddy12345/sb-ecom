package com.rkecom.crud.user.service.impl;

import com.rkecom.core.exception.ApiException;
import com.rkecom.core.exception.ResourceNotFoundException;
import com.rkecom.core.util.ResourceConstants;
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

    @Override
    public AddressModel getAddressById ( Long addressId ) {
        Address address=addressRepository.getAddressesByAddressId(addressId).orElseThrow (
                ()->new ResourceNotFoundException ( ResourceConstants.ADDRESS, "addressId", addressId )
        );
        return addressMapper.toModel().apply(address);
    }

    @Override
    @Transactional
    public AddressModel updateAddressById ( Long addressId, AddressModel addressModel ) {
        Address address=addressRepository.findById ( addressId ).orElseThrow (
                ()-> new ResourceNotFoundException ( ResourceConstants.ADDRESS, "addressId", addressId)
        );
        Address updateEntity=addressMapper.toUpdatedEntity ().apply ( address, addressModel );
        Address updatedAddress=addressRepository.save ( updateEntity );
        return addressMapper.toModel().apply(updatedAddress);
    }

    @Override
    @Transactional
    public AddressModel deleteAddressById ( Long addressId ) {
        Address address=addressRepository.findById ( addressId ).orElseThrow (
                ()-> new ResourceNotFoundException ( ResourceConstants.ADDRESS, "addressId", addressId)
        );
        addressRepository.deleteById ( addressId );
        return addressMapper.toModel().apply(address);
    }

}
