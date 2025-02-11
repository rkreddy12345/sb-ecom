package com.rkecom.web.user.controller;

import com.rkecom.core.controller.BaseController;
import com.rkecom.crud.user.service.AddressService;
import com.rkecom.db.entity.user.User;
import com.rkecom.security.auth.util.UserDetailsUtil;
import com.rkecom.web.user.model.AddressModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/v1")
@RequiredArgsConstructor
@Validated
public class AddressController extends BaseController {
    private final AddressService addressService;
    private final UserDetailsUtil userDetailsUtil;

    @PostMapping("/address")
    public ResponseEntity< AddressModel > createAddress(@Valid @RequestBody AddressModel address) {
        User user = userDetailsUtil.getCurrentUserEntity ();
        AddressModel addressModel = addressService.createAddress(address, user);
        return new ResponseEntity <> ( addressModel, HttpStatus.CREATED );
    }

    @GetMapping("/address/user")
    public ResponseEntity< List <AddressModel> > getUserAddresses(){
        User user = userDetailsUtil.getCurrentUserEntity ();
        List<AddressModel> addressModels=addressService.getUserAddresses(user.getUserId ());
        return new ResponseEntity <> ( addressModels, HttpStatus.OK );
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<AddressModel> getAddressById(@PathVariable Long addressId){
        return ResponseEntity.ok (addressService.getAddressById ( addressId ));
    }

}
