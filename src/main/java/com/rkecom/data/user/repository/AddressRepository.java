package com.rkecom.data.user.repository;

import com.rkecom.core.data.repository.BaseRepository;
import com.rkecom.db.entity.user.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends BaseRepository< Address, Long > {
}
