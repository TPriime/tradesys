package com.agbafune.tradesys.inmem;

import com.agbafune.tradesys.domain.model.Asset;
import com.agbafune.tradesys.domain.repository.AssetRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AssetRepositoryImpl implements AssetRepository {

    private final Map<Long, Asset> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public Optional<Asset> findById(Long id) {
        if (store.containsKey(id)) {
            return Optional.of(store.get(id));
        }
        return Optional.empty();
    }

    @Override
    public Asset save(Asset asset) {
        Asset a = asset;

        if (a.id() == null) {
            a = new Asset.Builder(asset)
                    .id(idGen.getAndIncrement())
                    .build();
        }

        store.put(a.id(), a);
        return a;
    }

    @Override
    public List<Asset> findAll() {
        return store.values().stream().toList();
    }
}
