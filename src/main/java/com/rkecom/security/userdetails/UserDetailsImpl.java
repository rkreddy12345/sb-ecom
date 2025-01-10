package com.rkecom.security.userdetails;

import com.rkecom.ui.model.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDetailsImpl implements UserDetails {

    @EqualsAndHashCode.Include
    private UserModel user;

    private Collection <? extends GrantedAuthority> authorities;

    @Override
    public String getUsername ( ) {
        return user.getUserName ();
    }

    @Override
    public String getPassword ( ) {
        return user.getPassword();
    }

    @Override
    public Collection < ? extends GrantedAuthority > getAuthorities ( ) {
        return authorities;
    }

    public Long getUserId(){
        return user.getUserId ();
    }

    public String getEmail ( ) {
        return user.getEmail ( );
    }

}
