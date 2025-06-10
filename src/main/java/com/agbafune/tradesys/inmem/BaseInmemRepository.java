package com.agbafune.tradesys.inmem;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

abstract class BaseInmemRepository<T> {

    private final Map<Long, T> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public abstract Long getId(T entity);
    public abstract T setId(T entity, Long id);

    public T save(T entity) {
        Long id = getId(entity);
        if (id == null) {
            id = idGen.getAndIncrement();
            entity = setId(entity, id);
        }
        store.put(id, entity);
        return entity;
    }

    public Optional<T> findById(Long id) {
        if (id != null && store.containsKey(id)) {
            return Optional.of(store.get(id));
        }
        return Optional.empty();
    }

    public List<T> findAll() {
        return store.values().stream().toList();
    }

    public Stream<T> getValuesStream() { return store.values().stream(); }
}
