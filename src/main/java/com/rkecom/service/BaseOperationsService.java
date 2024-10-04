package com.rkecom.service;

import jakarta.validation.Valid;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface BaseOperationsService<M, ID> {
    M findById(@NonNull ID id);
    List <M> findAll();
    M save(@Valid M m);
    M updateById(@NonNull ID id, @Valid M m);
    M deleteById(@NonNull ID id);
}
