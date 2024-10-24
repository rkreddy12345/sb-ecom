package com.rkecom.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository< E, ID extends Serializable > extends JpaRepository<E, ID> {
    public Optional < E > findByName ( String name );
}
