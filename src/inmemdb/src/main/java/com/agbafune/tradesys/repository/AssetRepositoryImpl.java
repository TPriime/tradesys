package com.agbafune.tradesys.repository;

import com.agbafune.tradesys.model.Asset;

public class AssetRepositoryImpl extends BaseInmemRepository<Asset> implements AssetRepository {

    @Override
    public Long getId(Asset asset) {
        return asset.id();
    }

    @Override
    public Asset setId(Asset asset, Long id) {
        return new Asset.Builder(asset).id(id).build();
    }

    @Override
    public String getName() {
        return "Asset";
    }
}
