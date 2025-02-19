package com.rkecom.web.user.controller;

import com.rkecom.core.controller.BaseController;
import com.rkecom.core.exception.ApiException;
import com.rkecom.core.response.ApiResponse;
import com.rkecom.core.util.PaginationUtil;
import com.rkecom.crud.user.service.UserService;
import com.rkecom.security.auth.util.UserDetailsUtil;
import com.rkecom.web.user.model.UserModel;
import com.rkecom.web.user.constants.UserConstants;
import com.rkecom.web.user.model.UserModelWithoutRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Validated
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UserService userService;
    private final UserDetailsUtil userDetailsUtil;

    @GetMapping ("/admin/users/sorted")
    public ResponseEntity < ApiResponse < UserModel > > getAllNonAdminUsers(
            @RequestParam (defaultValue = PaginationUtil.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE_SIZE) Integer size,
            @RequestParam(defaultValue = UserConstants.SORT_BY_ID ) String sortBy,
            @RequestParam(defaultValue = PaginationUtil.SORT_IN_ASC) String sortOrder) {
            return ResponseEntity.ok (userService.getAllNonAdminUsers (page, size, sortBy, sortOrder));
    }

    @GetMapping("/user/addresses")
    public ResponseEntity< UserModelWithoutRoles > getCurrentUserAddresses(){
        UserModel user = userDetailsUtil.getCurrentUser ().orElseThrow (
                ()->new ApiException ( "user not authenticated" )
        );
        return ResponseEntity.ok (userService.getCurrentUserAddresses( user.getUserName ()));
    }

}
