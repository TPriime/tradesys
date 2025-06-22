package com.agbafune.tradesys.repository;

import com.agbafune.tradesys.exception.ModelNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

abstract class BaseInmemRepository<T> implements BaseRepository<T> {

    private final Map<Long, T> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public abstract Long getId(T entity);
    public abstract T setId(T entity, Long id);
    public abstract String getName();

    @Override
    public T save(T entity) {
        Long id = getId(entity);
        if (id == null) {
            id = idGen.getAndIncrement();
            entity = setId(entity, id);
        }
        store.put(id, entity);
        return entity;
    }

    @Override
    public Optional<T> findById(Long id) {
        if (id != null && store.containsKey(id)) {
            return Optional.of(store.get(id));
        }
        return Optional.empty();
    }

    @Override
    public T getById(Long id) {
        return findById(id).orElseThrow(()-> new ModelNotFoundException(getName(), id));
    }

    @Override
    public List<T> findAll() {
        return store.values().stream().toList();
    }

    public Stream<T> getValuesStream() { return store.values().stream(); }
}
