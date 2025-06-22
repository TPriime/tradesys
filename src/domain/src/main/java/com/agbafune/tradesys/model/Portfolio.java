package com.agbafune.tradesys.model;

import com.agbafune.tradesys.exception.InsufficientAssetsException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.Function;

public record Portfolio(
        Long id,
        Long userId,
        ArrayList<PrtfAsset> assets
) {

    public void loadAssets(Function<Long, Asset> assetGetter) {
        assets.forEach(asset -> asset.setAsset(assetGetter.apply(asset.assetId)));
    }

    public BigDecimal value() {
        return assets.stream()
                .map(asset -> asset.getAsset().price().multiply(asset.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addAsset(Asset asset, BigDecimal quantity) {
        assets().stream()
                .filter(prtfAsset -> prtfAsset.assetId.equals(asset.id()))
                .findFirst()
                .ifPresentOrElse(
                        prtfAsset -> prtfAsset.setQuantity(prtfAsset.getQuantity().add(quantity)),
                        () -> assets().add(new PrtfAsset(asset.id(), quantity)));
    }

    public void removeAsset(Asset asset, BigDecimal quantity) {
        assets().stream()
                .filter(prtfAsset -> prtfAsset.assetId.equals(asset.id()))
                .findFirst()
                .ifPresentOrElse(prtfAsset -> {
                    BigDecimal newQuantity = prtfAsset.getQuantity().subtract(quantity);
                    int comp = newQuantity.compareTo(BigDecimal.ZERO);
                    if (comp == 0) {
                        assets().remove(prtfAsset);
                    } else if (comp < 0) {
                        throw new InsufficientAssetsException(asset.id());
                    } else {
                        prtfAsset.setQuantity(newQuantity);
                    }
                }, () -> {
                    throw new InsufficientAssetsException(asset.id());
                });
    }

    public static class PrtfAsset {
        private final Long assetId;
        private Asset asset;
        private BigDecimal quantity;

        private PrtfAsset(Long assetId, BigDecimal quantity) {
            this.assetId = assetId;
            this.quantity = quantity;
        }

        private void setAsset(Asset asset) {
            this.asset = asset;
        }

        public Asset getAsset() {
            return asset;
        }

        public BigDecimal getQuantity() {
            return quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            this.quantity = quantity;
        }
    }
}