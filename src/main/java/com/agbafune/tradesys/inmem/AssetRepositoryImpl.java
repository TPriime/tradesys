package com.agbafune.tradesys.inmem;

import com.agbafune.tradesys.domain.model.Asset;
import com.agbafune.tradesys.domain.repository.AssetRepository;
import org.springframework.stereotype.Service;

@Service
public class AssetRepositoryImpl extends BaseInmemRepository<Asset> implements AssetRepository {

    @Override
    public Long getId(Asset asset) {
        return asset.id();
    }

    @Override
    public Asset setId(Asset asset, Long id) {
        return new Asset.Builder(asset).build();
    }
}
