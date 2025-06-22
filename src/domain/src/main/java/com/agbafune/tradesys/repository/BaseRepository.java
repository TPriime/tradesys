package com.agbafune.tradesys.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    Optional<T> findById(Long id);
    T getById(Long id);
    List<T> findAll();
    T save(T asset);
}
