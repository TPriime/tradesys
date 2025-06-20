package com.agbafune.tradesys.domain.repository;

import com.agbafune.tradesys.domain.model.Asset;
import com.agbafune.tradesys.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface AssetRepository {
    Optional<Asset> findById(Long id);
    List<Asset> findAll();
    Asset save(Asset asset);
}
